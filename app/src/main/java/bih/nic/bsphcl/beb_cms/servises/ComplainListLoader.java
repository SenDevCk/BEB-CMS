package bih.nic.bsphcl.beb_cms.servises;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import androidx.appcompat.app.AlertDialog;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;

import bih.nic.bsphcl.beb_cms.entities.ComplainEntity;
import bih.nic.bsphcl.beb_cms.interfaces.GrivanceListBinder;
import bih.nic.bsphcl.beb_cms.utilities.Urls_this_pro;
import bih.nic.bsphcl.beb_cms.utilities.WebHandler;

public class ComplainListLoader extends AsyncTask<String, Void,  ArrayList<ComplainEntity>> {
    private static GrivanceListBinder grievance_list_binder;
    Activity activity;
    private ProgressDialog dialog1;
    private AlertDialog alertDialog;

    public ComplainListLoader(Activity activity) {
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
    protected  ArrayList<ComplainEntity> doInBackground(String... strings) {
        String res= null;
        try {
            res = WebHandler.callByPostwithoutparameter(Urls_this_pro.DOW_COM_LIST+ URLEncoder.encode(String.valueOf(strings[0]), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ArrayList<ComplainEntity> complainEntityArrayList=WebServiceHelper.complainListParser(res);
        return complainEntityArrayList;
    }

    @Override
    protected void onPostExecute( ArrayList<ComplainEntity> res) {
        super.onPostExecute(res);
        if (this.dialog1.isShowing())this.dialog1.dismiss();
         if (res==null){
             grievance_list_binder.grivanceNotFound("Server not Responding or Something wrong !");
         }else{
           if (res.size()>0) {
               grievance_list_binder.grivanceFound(res);
           }
           else {grievance_list_binder.grivanceNotFound("Complain Not Found");}
         }
    }

    public static void complaintListListener(GrivanceListBinder grivanceListBinder2){
        grievance_list_binder=grivanceListBinder2;
    }
}
