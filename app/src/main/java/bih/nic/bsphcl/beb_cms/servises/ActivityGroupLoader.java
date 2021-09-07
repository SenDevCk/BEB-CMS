package bih.nic.bsphcl.beb_cms.servises;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

import bih.nic.bsphcl.beb_cms.database.DataBaseHelper;
import bih.nic.bsphcl.beb_cms.entities.ActivityCode;
import bih.nic.bsphcl.beb_cms.entities.ActivityGroup;
import bih.nic.bsphcl.beb_cms.utilities.Urls_this_pro;
import bih.nic.bsphcl.beb_cms.utilities.WebHandler;

public class ActivityGroupLoader extends AsyncTask<String, Void, ArrayList<ActivityGroup>> {

    Activity activity;
    private ProgressDialog dialog1;
    private AlertDialog alertDialog;

    public ActivityGroupLoader(Activity activity) {
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
    protected ArrayList<ActivityGroup> doInBackground(String... strings) {
        String json_res=WebHandler.callByPostwithoutparameter(Urls_this_pro.LOAD_ACTIVITY_GROUP);

        return WebServiceHelper.activityGroupParser(json_res);
    }

    @Override
    protected void onPostExecute(ArrayList<ActivityGroup> res) {
        super.onPostExecute(res);
          if (res!=null){
              if (res.size()>0){
                  long c=new DataBaseHelper(activity).saveActivityGroup(res);
                  Log.d("ActivityGroup","Total= "+c);
                  if (dialog1.isShowing())dialog1.dismiss();
                  if (c>0){
                      new ActivityCodeLoader(activity).execute();
                  }else {
                      Toast.makeText(activity, "Activity Group not saved !", Toast.LENGTH_SHORT).show();
                  }
              }
          }
    }
}
