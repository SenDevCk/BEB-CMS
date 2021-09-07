package bih.nic.bsphcl.beb_cms.servises;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.net.URLEncoder;

import bih.nic.bsphcl.beb_cms.entities.UserInformationEntity;
import bih.nic.bsphcl.beb_cms.ui.MainActivity;
import bih.nic.bsphcl.beb_cms.ui.StaffMainActivity;
import bih.nic.bsphcl.beb_cms.utilities.CommonPref;
import bih.nic.bsphcl.beb_cms.utilities.GlobalVariables;
import bih.nic.bsphcl.beb_cms.utilities.Urls_this_pro;
import bih.nic.bsphcl.beb_cms.utilities.Utiilties;
import bih.nic.bsphcl.beb_cms.utilities.WebHandler;

@RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
public
class LoginLoader extends AsyncTask<String, Void, UserInformationEntity> {
    private ProgressDialog dialog1;
    private AlertDialog alertDialog;
    AppCompatActivity activity;
    String entered_string;
    public LoginLoader(AppCompatActivity activity) {
        this.activity = activity;
        dialog1 = new ProgressDialog(this.activity);
        alertDialog = new AlertDialog.Builder(this.activity).create();
    }


    @Override
    protected void onPreExecute() {
        this.dialog1.setCanceledOnTouchOutside(false);
        this.dialog1.setMessage("Processing...");
        this.dialog1.show();
    }

    @Override
    protected UserInformationEntity doInBackground(String... strings) {
        entered_string=strings[0];
        UserInformationEntity userInfo2 = null;
        if (Utiilties.isOnline(activity)) {
            String result = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                try {
                    result = WebHandler.callByPostwithoutparameter(Urls_this_pro.LOG_IN_URL + Utiilties.reqString(strings[0],activity));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                alertDialog.setMessage("Android version must kitkat(19) or above !");
                alertDialog.show();
            }
            if (result != null) {
                userInfo2 = WebServiceHelper.loginParser(result);
            }
            return userInfo2;
        } else {
            userInfo2 = CommonPref.getUserDetails(activity);
            if (userInfo2.getUserID().length() > 4) {
                userInfo2.setAuthenticated(true);
                return userInfo2;
            } else {
                return null;
            }
        }
    }

    @Override
    protected void onPostExecute(UserInformationEntity result) {
        super.onPostExecute(result);
        if (this.dialog1.isShowing()) {
            this.dialog1.cancel();
        }
        if (result == null) {
            alertDialog.setTitle("Failed");
            alertDialog.setMessage("Something Went Wrong ! May be Server Problem !");
            alertDialog.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //edit_user_name.setFocusable(true);
                }
            });
            alertDialog.show();
        } else if (!result.getAuthenticated()) {
            alertDialog.setTitle("Failed");
            if (result.getMessageString() != null) {
                alertDialog.setMessage("User authentication failed ! \n" + result.getMessageString());
            } else {
                alertDialog.setMessage("User authentication failed !");
            }
            alertDialog.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //edit_user_name.setFocusable(true);
                }
            });
            alertDialog.show();
        } else {
            Intent cPannel = new Intent(activity, MainActivity.class);
            if (Utiilties.isOnline(activity)) {
                if (result != null) {
                  /*  if (entered_string.split("\\|")[2].equalsIgnoreCase(result.getImeiNo())) {
                        try {*/
                            result.setPassword(entered_string.split("\\|")[1]);
                            result.setImeiNo(entered_string.split("\\|")[2]);
                            GlobalVariables.LoggedUser = result;
                            CommonPref.setUserDetails(activity, result);
                            activity.startActivity(cPannel);
                            activity.finish();

                        /* } catch (Exception ex) {
                            Toast.makeText(activity, "Login failed due to Some Error !", Toast.LENGTH_SHORT).show();
                        }
                   } else {
                        alertDialog.setTitle("Device Not Registered");
                        alertDialog.setMessage("Sorry, your device is not registered!.\r\nPlease contact your Admin.");
                        alertDialog.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //edit_user_name.setFocusable(true);
                            }
                        });
                        alertDialog.show();
                    }*/
                }
            } else {
                if (CommonPref.getUserDetails(activity) != null) {
                    GlobalVariables.LoggedUser = result;
                    if (GlobalVariables.LoggedUser.getUserID().equalsIgnoreCase(entered_string.split("\\|")[0]) && GlobalVariables.LoggedUser.getPassword().equalsIgnoreCase(entered_string.split("\\|")[1])) {
                        activity.startActivity(cPannel);
                        activity.finish();
                    } else {
                        Toast.makeText(activity, "User name and password not matched !", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(activity, "Please enable internet connection for first time login.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}