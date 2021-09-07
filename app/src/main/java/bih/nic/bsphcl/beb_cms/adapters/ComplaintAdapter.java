package bih.nic.bsphcl.beb_cms.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import bih.nic.bsphcl.beb_cms.R;
import bih.nic.bsphcl.beb_cms.entities.ComplainEntity;
import bih.nic.bsphcl.beb_cms.ui.ComplainDetailsActivity;


/**
 * Created by nicsi on 11/23/2017.
 */
public class ComplaintAdapter extends BaseAdapter implements Filterable {
    Context activity;

    List<ComplainEntity> UserWiseGrivanceList;
    ArrayList<ComplainEntity> mStringFilterList;
    ValueFilter valueFilter;
    public ComplaintAdapter(Context context, ArrayList<ComplainEntity> rlist) {
        this.activity = context;
        this.UserWiseGrivanceList = rlist;
        this.mStringFilterList = rlist;
        //Collections.sort(this.UserWiseGrivanceList);
        //Collections.reverse(this.UserWiseGrivanceList);
        //Collections.sort( this.mStringFilterList);
        //Collections.reverse( this.mStringFilterList);
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
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        ViewHolder holder;
            LayoutInflater inflater = ((Activity) activity).getLayoutInflater();
            row = inflater.inflate(R.layout.complain_item, parent, false);
            holder = new ViewHolder();
            holder.UserName=(TextView)row.findViewById(R.id.tv_user_name);
            holder.tv_add_con=(TextView)row.findViewById(R.id.tv_add_con);
            holder.GrivamnceId=(TextView)row.findViewById(R.id.tv_Grivance_Id);
            holder.tv_Grivance_type=(TextView)row.findViewById(R.id.tv_Grivance_type);
            holder.tv_Grivance_sts=(TextView)row.findViewById(R.id.tv_Grivance_sts);
            holder.Date=(TextView)row.findViewById(R.id.tv_entrydate);
            //holder.Image=(ImageView)row.findViewById(R.id.img_GrivanceImg);
            holder.UserName.setText(""+UserWiseGrivanceList.get(position).getConatactNo());
            holder.tv_add_con.setText(""+UserWiseGrivanceList.get(position).getName());
            holder.GrivamnceId.setText(UserWiseGrivanceList.get(position).getComplaintNo());
            long minutes=Integer.parseInt(UserWiseGrivanceList.get(position).getComplaintTime());
            if (minutes<60){
                holder.Date.setText(""+UserWiseGrivanceList.get(position).getComplaintTime()+" minutes");
            }else{
                if ((minutes/60)<24){
                    //if ((minutes % 60)>=24)
                    holder.Date.setText(""+(minutes/60)+" hours "+(minutes % 60)+" minutes");
                }else{
                    if ((minutes % 1440)>=60)
                    holder.Date.setText(""+(minutes/1440)+" days "+(minutes % 1440)/60+" hours");
                    else holder.Date.setText(""+(minutes/1440)+" days ");
                }
            }

            holder.tv_Grivance_type.setText(UserWiseGrivanceList.get(position).getComplaintType());
            holder.tv_Grivance_sts.setText(""+UserWiseGrivanceList.get(position).getEscalationLevel());

            //Picasso.get().load("https://grievance.sspmis.in"+UserWiseGrivanceList.get(position).getPhotoByte1()).into(holder.Image);

            row.setTag(holder);

      /*  row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ComplainDetailsActivity.class);
                activity.startActivity(intent);
            }
        });*/
        return row;
    }

    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

    private class ViewHolder {
        TextView UserName, GrivamnceId, Date, tv_Grivance_type, tv_Grivance_sts,tv_add_con;
        ImageView Image;
    }
    private class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                List<ComplainEntity> filterList = new ArrayList<>();
                for (int i = 0; i < mStringFilterList.size(); i++) {
                    if ((mStringFilterList.get(i).getComplaintNo().toUpperCase()).contains(constraint.toString().toUpperCase())) {
                        filterList.add(mStringFilterList.get(i));
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = mStringFilterList.size();
                results.values = mStringFilterList;
            }
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            UserWiseGrivanceList = (List<ComplainEntity>) results.values;
            notifyDataSetChanged();
        }

    }

}


