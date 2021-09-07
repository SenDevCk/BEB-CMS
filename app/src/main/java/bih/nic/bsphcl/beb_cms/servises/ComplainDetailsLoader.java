package bih.nic.bsphcl.beb_cms.servises;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import androidx.appcompat.app.AlertDialog;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import bih.nic.bsphcl.beb_cms.entities.ComplainDetailsEntity;
import bih.nic.bsphcl.beb_cms.interfaces.ComplainDetailsListener;
import bih.nic.bsphcl.beb_cms.utilities.Urls_this_pro;
import bih.nic.bsphcl.beb_cms.utilities.WebHandler;

public class ComplainDetailsLoader extends AsyncTask<String, Void, ComplainDetailsEntity> {
    Activity activity;
    private ProgressDialog dialog1;
    private AlertDialog alertDialog;
    private static ComplainDetailsListener complainDetailsListener;
    public ComplainDetailsLoader(Activity activity) {
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
    protected ComplainDetailsEntity doInBackground(String... strings) {
        String res= null;
       // try {
            res = WebHandler.callByPostwithoutparameter(Urls_this_pro.DOW_COMPLAIN_DET+strings[0]);
      /*  } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/
        ComplainDetailsEntity complainEntityArrayList=WebServiceHelper.complainParser(res);
        return complainEntityArrayList;
    }

    @Override
    protected void onPostExecute(ComplainDetailsEntity result) {
        super.onPostExecute(result);
        if (dialog1.isShowing())dialog1.dismiss();
        if (result==null){
            complainDetailsListener.complainDetailsNotFound("Server Problem ! Data not found !");
        }else{
            complainDetailsListener.complainDetailsFound(result);
        }
    }

    public static void complainDetailsBinder(ComplainDetailsListener complainDetailsListener1){
        complainDetailsListener=complainDetailsListener1;
    }
}
