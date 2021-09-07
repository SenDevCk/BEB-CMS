package bih.nic.bsphcl.beb_cms.servises;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import androidx.appcompat.app.AlertDialog;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import bih.nic.bsphcl.beb_cms.entities.ComplainCountTypeWiseEntity;
import bih.nic.bsphcl.beb_cms.interfaces.ComplaintCountTypeWiseBinder;
import bih.nic.bsphcl.beb_cms.utilities.Urls_this_pro;
import bih.nic.bsphcl.beb_cms.utilities.WebHandler;

public class CompalinCountTypewiseLoader extends AsyncTask<String, Void,  ArrayList<ComplainCountTypeWiseEntity>> {
    Activity activity;
    private ProgressDialog dialog1;
    private AlertDialog alertDialog;
    static ComplaintCountTypeWiseBinder complaintCountBinder;
    public CompalinCountTypewiseLoader(Activity activity) {
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
    protected  ArrayList<ComplainCountTypeWiseEntity> doInBackground(String... strings) {
        String res= null;
        try {
            res = WebHandler.callByPostwithoutparameter(Urls_this_pro.DOW_COM_COUNT+ URLEncoder.encode(String.valueOf(strings[0]), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ArrayList<ComplainCountTypeWiseEntity> complainEntityArrayList=WebServiceHelper.complainCountParser(res);
        return complainEntityArrayList;
    }

    @Override
    protected void onPostExecute( ArrayList<ComplainCountTypeWiseEntity> res) {
        super.onPostExecute(res);
        if (this.dialog1.isShowing()){
            this.dialog1.dismiss();
        }
       if (res!=null){
           if (res.size()>0){
               complaintCountBinder.countFound(res);
           }else{
               complaintCountBinder.countNotFound("Data not Found");
           }
       }else{
           complaintCountBinder.countNotFound("Server Problem or Something went wrong !");
       }
    }

    public static void complainCountLoader(ComplaintCountTypeWiseBinder complaintCountBinder2){
        complaintCountBinder=complaintCountBinder2;
    }
}
