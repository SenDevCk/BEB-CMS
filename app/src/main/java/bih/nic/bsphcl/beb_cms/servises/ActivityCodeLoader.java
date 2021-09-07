package bih.nic.bsphcl.beb_cms.servises;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

import bih.nic.bsphcl.beb_cms.database.DataBaseHelper;
import bih.nic.bsphcl.beb_cms.entities.ActivityCode;
import bih.nic.bsphcl.beb_cms.ui.LoginActivity;
import bih.nic.bsphcl.beb_cms.utilities.CommonPref;
import bih.nic.bsphcl.beb_cms.utilities.Urls_this_pro;
import bih.nic.bsphcl.beb_cms.utilities.WebHandler;

public class ActivityCodeLoader extends AsyncTask<String, Void, ArrayList<ActivityCode>> {
    Activity activity;
    private ProgressDialog dialog1;
    private AlertDialog alertDialog;

    public ActivityCodeLoader(Activity activity) {
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
    protected ArrayList<ActivityCode> doInBackground(String... strings) {
        String json_res = WebHandler.callByPostwithoutparameter(Urls_this_pro.LOAD_ACTIVITY_CODE);
        return WebServiceHelper.activityCodeParser(json_res);
    }

    @Override
    protected void onPostExecute(ArrayList<ActivityCode> res) {
        super.onPostExecute(res);
        if (res != null) {
            if (res.size() > 0) {
                long c = new DataBaseHelper(activity).saveActivityCode(res);
                if (c > 0) {
                    if (this.dialog1.isShowing()) dialog1.dismiss();
                    CommonPref.setCheckUpdate(activity, true);
                    Intent intent = new Intent(activity, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    activity.startActivity(intent);
                    activity.finish();
                } else {
                    CommonPref.setCheckUpdate(activity, false);
                    if (this.dialog1.isShowing()) dialog1.dismiss();
                }
            } else {
                if (this.dialog1.isShowing())dialog1.dismiss();
                Toast.makeText(activity, "Activity Code Not Found !", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (this.dialog1.isShowing()) dialog1.dismiss();
            Toast.makeText(activity, "Something went wrong !", Toast.LENGTH_SHORT).show();
        }

    }
}
