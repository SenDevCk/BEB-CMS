package bih.nic.bsphcl.beb_cms.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import bih.nic.bsphcl.beb_cms.R;
import bih.nic.bsphcl.beb_cms.entities.PgLogEntity;

public class PgLogAdapter extends RecyclerView.Adapter<PgLogAdapter.MyViewHolder> {

    private List<PgLogEntity> pgLogEntities;
    Activity activity;
    private int lastPosition = -1;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView pg_level, pg_date,pg_rem;
        //ImageView go_to_statement_view;
        public MyViewHolder(View view) {
            super(view);
            pg_level = (TextView) view.findViewById(R.id.pg_level);
            pg_date = (TextView) view.findViewById(R.id.pg_date);
            pg_rem = (TextView) view.findViewById(R.id.pg_rem);

        }
    }


    public PgLogAdapter(List<PgLogEntity> homeitemEntities, Activity activity) {
        this.pgLogEntities = homeitemEntities;
        Collections.sort(this.pgLogEntities);
        this.activity = activity;
    }

    @Override
    public PgLogAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pg_log_item, parent, false);

        return new PgLogAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PgLogAdapter.MyViewHolder holder, final int position) {
        final PgLogEntity pgLogEntity = pgLogEntities.get(position);
        holder.pg_level.setText("PG Level:- "+pgLogEntity.getPgLevel()+" ( "+pgLogEntity.getStatus()+" )");
        holder.pg_date.setText(pgLogEntity.getAllotmentTime());
        holder.pg_rem.setText(pgLogEntity.getRemarks());
        setAnimation(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        //return homeitemEntities.size();
        return pgLogEntities.size();
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