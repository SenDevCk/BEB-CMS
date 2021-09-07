package bih.nic.bsphcl.beb_cms.adapters;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import bih.nic.bsphcl.beb_cms.R;
import bih.nic.bsphcl.beb_cms.entities.ComplainCountIDWiseEntity;
import bih.nic.bsphcl.beb_cms.entities.ComplainCountTypeWiseEntity;
import bih.nic.bsphcl.beb_cms.entities.UserInformationEntity;
import bih.nic.bsphcl.beb_cms.interfaces.ComplaintCountIDWiseBinder;
import bih.nic.bsphcl.beb_cms.servises.CompalinCountIDwiseLoader;
import bih.nic.bsphcl.beb_cms.ui.ComplainListActivity;
import bih.nic.bsphcl.beb_cms.utilities.CommonPref;


/**
 * Created by chandan on 5/13/18.
 */

public class ComCountAdapter extends RecyclerView.Adapter<ComCountAdapter.MyViewHolder> {

    private List<ComplainCountTypeWiseEntity> complainCountEntities;
    Activity activity;
    private int lastPosition = -1;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView text_designation, text_count;
        //ImageView go_to_statement_view;
        public MyViewHolder(View view) {
            super(view);
            text_designation = (TextView) view.findViewById(R.id.text_designation);
            text_count = (TextView) view.findViewById(R.id.text_count);

        }
    }


    public ComCountAdapter(List<ComplainCountTypeWiseEntity> homeitemEntities, Activity activity) {
        this.complainCountEntities = homeitemEntities;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.com_count_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final ComplainCountTypeWiseEntity mruEntity = complainCountEntities.get(position);
        holder.text_designation.setText(mruEntity.getComplaint_type());
        holder.text_count.setText(mruEntity.getTotal());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
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
                new CompalinCountIDwiseLoader(activity).execute(userInformationEntity.getUserID()+"@"+userInformationEntity.getPassword()+"@"+userInformationEntity.getImeiNo()+"@"+complainCountEntities.get(position).getCompl_id());
            }
        });
        setAnimation(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        //return homeitemEntities.size();
        return complainCountEntities.size();
    }
    private void listAlertDialog(final ArrayList<ComplainCountIDWiseEntity> grivanceEntityArrayList) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(activity);
        builderSingle.setIcon(R.drawable.bsphcl_logo);
        builderSingle.setTitle("Please Select Type");

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
               Intent intent=new Intent(activity,ComplainListActivity.class);
               intent.putExtra("com_type",strName.trim().split(":")[0]);
               intent.putExtra("com_type_id",grivanceEntityArrayList.get(which).getCompl_id());
               activity.startActivity(intent);
            }
        });
        builderSingle.show();
    }

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(activity, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
}