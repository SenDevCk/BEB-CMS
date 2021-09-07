package bih.nic.bsphcl.beb_cms.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

import bih.nic.bsphcl.beb_cms.R;
import bih.nic.bsphcl.beb_cms.adapters.ComplaintAdapter;
import bih.nic.bsphcl.beb_cms.entities.ComplainEntity;
import bih.nic.bsphcl.beb_cms.entities.UserInformationEntity;
import bih.nic.bsphcl.beb_cms.interfaces.GrivanceListBinder;
import bih.nic.bsphcl.beb_cms.servises.ComplainListLoader;
import bih.nic.bsphcl.beb_cms.utilities.CommonPref;
import jp.wasabeef.blurry.Blurry;

public class ComplainListActivity extends AppCompatActivity {

    SwipeRefreshLayout swip_list_com;
    ListView list_complaint;
    ComplaintAdapter complaintAdapter;
    String com_type,com_type_id;
    SearchView search;
    Toolbar toolbar_cl;
    private int LAUNCH_SECOND_ACTIVITY=10;
    static ArrayList<ComplainEntity> grivanceEntityArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain_list);
        
        toolbar_cl = (Toolbar) findViewById(R.id.toolbar_cl);
        toolbar_cl.setTitle("Complaint List");
        toolbar_cl.setSubtitle("CRMS");
        toolbar_cl.setSubtitleTextColor(getResources().getColor(R.color.white));
        toolbar_cl.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar_cl);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar_cl.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        com_type=getIntent().getStringExtra("com_type");
        com_type_id=getIntent().getStringExtra("com_type_id");
        list_complaint = (ListView) findViewById(R.id.list_complaint);
        search = (SearchView) findViewById(R.id.search);
        swip_list_com = (SwipeRefreshLayout) findViewById(R.id.swip_list_com);
        swip_list_com.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //onres = false;
                loadpageData();
                swip_list_com.setRefreshing(false);
            }
        });
       search.setActivated(false);
       search.setQueryHint("Type Complain Number");
       search.onActionViewExpanded();
       search.setIconified(false);
       search.clearFocus();

       search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search.clearFocus();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                complaintAdapter.getFilter().filter(newText);

                return false;
            }

        });
        loadpageData();
    }

    private void loadpageData() {
        ComplainListLoader.complaintListListener(new GrivanceListBinder() {
            @Override
            public void grivanceFound(ArrayList<ComplainEntity> grivanceEntityArrayList) {
                ComplainListActivity.grivanceEntityArrayList=grivanceEntityArrayList;
                complaintAdapter = new ComplaintAdapter(ComplainListActivity.this, ComplainListActivity.grivanceEntityArrayList);
                list_complaint.invalidate();
                list_complaint.setAdapter(complaintAdapter);
                list_complaint.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent=new Intent(ComplainListActivity.this,ComplainDetailsActivity.class);
                        ComplainEntity complainEntity= (ComplainEntity) parent.getItemAtPosition(position);
                        intent.putExtra("object",  complainEntity);
                        startActivityForResult(intent,LAUNCH_SECOND_ACTIVITY);
                    }
                });
            }

            @Override
            public void grivanceNotFound(String response) {
               list_complaint.invalidate();
                Toast.makeText(ComplainListActivity.this, ""+response, Toast.LENGTH_SHORT).show();
            }
        });
        UserInformationEntity userInformationEntity= CommonPref.getUserDetails(ComplainListActivity.this);//ZDIV@NEW@NA
        new ComplainListLoader(ComplainListActivity.this).execute(userInformationEntity.getUserID().trim()+"@"+userInformationEntity.getPassword().trim()+"@"+userInformationEntity.getImeiNo().trim()+"@NA@NA@NA@"+com_type_id+"@"+com_type+"@NA");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_SECOND_ACTIVITY) {
            if(resultCode == Activity.RESULT_OK){
                //String result=data.getSerializableExtra("object");
                Log.e("result","*************** RESULT_OK ********************");

                for (ComplainEntity complainEntity:ComplainListActivity.grivanceEntityArrayList){

                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
                Log.e("result","*************** RESULT_CANCELED ********************");
            }
        }
    }
}