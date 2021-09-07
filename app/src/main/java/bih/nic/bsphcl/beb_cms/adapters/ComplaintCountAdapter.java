package bih.nic.bsphcl.beb_cms.adapters;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

import bih.nic.bsphcl.beb_cms.R;
import bih.nic.bsphcl.beb_cms.entities.ComplainCountIDWiseEntity;
import bih.nic.bsphcl.beb_cms.entities.ComplainCountTypeWiseEntity;

import bih.nic.bsphcl.beb_cms.entities.UserInformationEntity;
import bih.nic.bsphcl.beb_cms.interfaces.ComplaintCountIDWiseBinder;
import bih.nic.bsphcl.beb_cms.servises.CompalinCountIDwiseLoader;
import bih.nic.bsphcl.beb_cms.ui.ComplainListActivity;
import bih.nic.bsphcl.beb_cms.utilities.CommonPref;


/**
 * Created by nicsi on 06/23/2020.
 */

public class ComplaintCountAdapter extends BaseAdapter {
    Activity activity;

    ArrayList<ComplainCountTypeWiseEntity> UserWiseGrivanceList;

    public ComplaintCountAdapter(Activity context, ArrayList<ComplainCountTypeWiseEntity> rlist) {
        this.activity = context;
        this.UserWiseGrivanceList = rlist;

    }


    @Override
    public int getCount() {
        return UserWiseGrivanceList.size();
    }

    @Override
    public Object getItem(int position) {
        return UserWiseGrivanceList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View row = convertView;
        final ViewHolder holder = new ViewHolder();
            LayoutInflater inflater = ((Activity) activity).getLayoutInflater();
            row = inflater.inflate(R.layout.com_count_item, parent, false);

            holder.text_designation = (TextView) row.findViewById(R.id.text_designation);
            holder.text_count = (TextView) row.findViewById(R.id.text_count);
            holder.text_new = (TextView) row.findViewById(R.id.text_new);
            holder.text_reopen = (TextView) row.findViewById(R.id.text_reopen);
            holder.text_close = (TextView) row.findViewById(R.id.text_close);
            holder.text_designation.setText(""+UserWiseGrivanceList.get(position).getComplaint_type());
            holder.text_count.setText(""+UserWiseGrivanceList.get(position).getTotal());
            holder.text_new.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, ComplainListActivity.class);
                    intent.putExtra("com_id",UserWiseGrivanceList.get(position).getCompl_id().trim());
                    intent.putExtra("type",holder.text_new.getText());
                    activity.startActivity(intent);
                }
            });
            holder.text_reopen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, ComplainListActivity.class);
                    intent.putExtra("com_id",UserWiseGrivanceList.get(position).getCompl_id().trim());
                    intent.putExtra("type",holder.text_reopen.getText());
                    activity.startActivity(intent);
                }
            });
            holder.text_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, ComplainListActivity.class);
                    intent.putExtra("com_id",UserWiseGrivanceList.get(position).getCompl_id().trim());
                    intent.putExtra("type",holder.text_close.getText());
                    activity.startActivity(intent);
                }
            });
          row.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  CompalinCountIDwiseLoader.complainCountLoader(new ComplaintCountIDWiseBinder() {
                      @Override
                      public void countFound(ArrayList<ComplainCountIDWiseEntity> grivanceEntityArrayList) {
                          listAlertDialog(grivanceEntityArrayList);
                      }

                      @Override
                      public void countNotFound(String response) {

                      }
                  });
                  UserInformationEntity userInformationEntity= CommonPref.getUserDetails(activity);
                  new CompalinCountIDwiseLoader(activity).execute(userInformationEntity.getUserID()+"@"+userInformationEntity.getPassword()+"@"+userInformationEntity.getImeiNo()+"@"+UserWiseGrivanceList.get(position).getCompl_id());
              }
          });
        return row;
    }

    private void listAlertDialog(ArrayList<ComplainCountIDWiseEntity> grivanceEntityArrayList) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(activity);
        builderSingle.setIcon(R.drawable.bsphcl_logo);
        builderSingle.setTitle("Please Select");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(activity, android.R.layout.select_dialog_singlechoice);
       for (ComplainCountIDWiseEntity entity:grivanceEntityArrayList){
           arrayAdapter.add(entity.getComplaint_type()+": ( "+entity.getTotal()+" )");
       }

        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
                AlertDialog.Builder builderInner = new AlertDialog.Builder(activity);
                builderInner.setMessage(strName);
                builderInner.setTitle("Your Selected Item is");
                builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int which) {
                        dialog.dismiss();
                    }
                });
                builderInner.show();
            }
        });
        builderSingle.show();
    }

    private class ViewHolder {
        TextView text_designation, text_count;
        TextView text_new,text_reopen,text_close;
    }
}


