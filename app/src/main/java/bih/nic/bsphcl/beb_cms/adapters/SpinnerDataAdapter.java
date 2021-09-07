package bih.nic.bsphcl.beb_cms.adapters;

import android.app.Activity;
import android.opengl.EGLExt;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import bih.nic.bsphcl.beb_cms.R;
import bih.nic.bsphcl.beb_cms.entities.SpinnerData;

public class SpinnerDataAdapter extends BaseAdapter {
    LayoutInflater layoutInflater;
    Activity activity;
    ArrayList<SpinnerData> spinnerData;

    public SpinnerDataAdapter(Activity activity, ArrayList<SpinnerData> spinnerData){
        this.activity=activity;
        this.spinnerData=spinnerData;
        layoutInflater=this.activity.getLayoutInflater();
    }

    @Override
    public int getCount() {
        return spinnerData.size();
    }

    @Override
    public Object getItem(int position) {
        return spinnerData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=layoutInflater.inflate(android.R.layout.simple_list_item_1,null,false);
        TextView text=(TextView) convertView.findViewById(android.R.id.text1);
        text.setText(spinnerData.get(position).getValue());
        return convertView;
    }
}
