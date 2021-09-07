package bih.nic.bsphcl.beb_cms.servises;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import androidx.appcompat.app.AlertDialog;

import org.json.JSONObject;

import bih.nic.bsphcl.beb_cms.interfaces.CloseComplainBinder;
import bih.nic.bsphcl.beb_cms.utilities.Urls_this_pro;
import bih.nic.bsphcl.beb_cms.utilities.WebHandler;

public class CloseComplainService extends AsyncTask<String ,Void,String> {

    Activity activity;
    private ProgressDialog dialog1;
    private AlertDialog alertDialog;
    private static CloseComplainBinder closeComplainBinder;
    public CloseComplainService(Activity activity) {
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
    protected String doInBackground(String... strings) {
        return WebHandler.callByPostwithoutparameter(Urls_this_pro.LOAD_CLO_COM);
    }

    @Override
    protected void onPostExecute(String res) {
        super.onPostExecute(res);
        if (dialog1.isShowing()){
            dialog1.dismiss();
        }
        if (res!=null){
         try{
             JSONObject jsonObject=new JSONObject(res);
             if (jsonObject.getString("").equals("SUCCESS")){
                 closeComplainBinder.closeComplainSuccess("SUCCESS");
             }else{
                 closeComplainBinder.closeComplainSuccess("Un-SUCCESS");
             }
         }catch (Exception e){
             e.printStackTrace();
         }
        }else{
            closeComplainBinder.closeComplainUnSucess("Somthing went wrong !");
        }

    }

    public static void closeComplainListener(CloseComplainBinder closeComplainBinder1){
        closeComplainBinder=closeComplainBinder1;
    }
}
