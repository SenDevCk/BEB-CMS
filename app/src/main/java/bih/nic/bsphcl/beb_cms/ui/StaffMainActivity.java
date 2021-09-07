package bih.nic.bsphcl.beb_cms.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import bih.nic.bsphcl.beb_cms.R;
import bih.nic.bsphcl.beb_cms.adapters.ComplaintCountAdapter;
import bih.nic.bsphcl.beb_cms.entities.ComplainCountTypeWiseEntity;
import bih.nic.bsphcl.beb_cms.entities.ComplainEntity;
import bih.nic.bsphcl.beb_cms.entities.UserInformationEntity;
import bih.nic.bsphcl.beb_cms.interfaces.ComplaintCountTypeWiseBinder;
import bih.nic.bsphcl.beb_cms.servises.CompalinCountTypewiseLoader;
import bih.nic.bsphcl.beb_cms.utilities.CommonPref;

public class StaffMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    ImageView imageViewheader;
    TextView text_header_name, text_header_mobile;
    SwipeRefreshLayout swipeRefreshLayout;
    ComplaintCountAdapter complaintCountAdapter;
    ListView list_com_item;
    ArrayList<ComplainEntity> complainEntities;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(this);

        try {
            String version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            String code_v = String.valueOf(getPackageManager().getPackageInfo(getPackageName(), 0).versionCode);
            TextView app_name_tip = (TextView) navigationView.findViewById(R.id.app_name_tip);
            app_name_tip.setText("CRMS ( " + code_v + "." + version + " ) V");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        //navigation header
        View header = navigationView.getHeaderView(0);
        text_header_name = (TextView) header.findViewById(R.id.text_header_name);
        text_header_mobile = (TextView) header.findViewById(R.id.text_header_mobile);
        imageViewheader = (ImageView) header.findViewById(R.id.imageViewheader);

        UserInformationEntity userinfo = CommonPref.getUserDetails(StaffMainActivity.this);
        if (userinfo.getDivision().startsWith("2")) {
            //header_image.setImageDrawable(getResources().getDrawable(R.drawable.sblogo1));
            imageViewheader.setImageDrawable(getResources().getDrawable(R.drawable.sblogo1));
        } else if (userinfo.getDivision().startsWith("1")) {
            //header_image.setImageDrawable(getResources().getDrawable(R.drawable.nblogo));
            imageViewheader.setImageDrawable(getResources().getDrawable(R.drawable.nblogo));
        } else {
            imageViewheader.setImageDrawable(getResources().getDrawable(R.drawable.bsphcl_logo));
        }
        toolbar.setSubtitle("" + userinfo.getUserName());
        text_header_name.setText("" + userinfo.getUserName());
        text_header_mobile.setText("" + userinfo.getContactNo());

        list_com_item = (ListView) findViewById(R.id.list_Grivance_Itm);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.pullToRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //onres = false;
                loadpageData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        loadpageData();
    }

    private void loadpageData() {
        UserInformationEntity userInformationEntity=CommonPref.getUserDetails(StaffMainActivity.this);
        CompalinCountTypewiseLoader.complainCountLoader(new ComplaintCountTypeWiseBinder() {
            @Override
            public void countFound(ArrayList<ComplainCountTypeWiseEntity> grivanceEntityArrayList) {
                complaintCountAdapter = new ComplaintCountAdapter(StaffMainActivity.this, grivanceEntityArrayList);
                list_com_item.setAdapter(complaintCountAdapter);
            }

            @Override
            public void countNotFound(String response) {
                list_com_item.invalidate();
                toolbar.setSubtitle(""+response);
                Toast.makeText(StaffMainActivity.this, ""+response, Toast.LENGTH_SHORT).show();
            }
        });
        new CompalinCountTypewiseLoader(StaffMainActivity.this).execute(userInformationEntity.getUserID().trim()+"|"+userInformationEntity.getPassword().trim()+"|"+userInformationEntity.getImeiNo().trim());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.staff_main, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

       /* if (id == R.id.drawer_tkc) {
            dialogNeft();

        } else if (id == R.id.drawer_topup) {
            dialogReport(1);
        } else if (id == R.id.drawer_server_report) {
            dialogReport(2);
        }
        else if (id == R.id.drawer_gri_sin) {
            startActivity(new Intent(MainActivity.this,SingleRechargeStatus.class));
        }else if (id==R.id.drawer_gri_sts){
            Toast.makeText(this, "Under Construction ..", Toast.LENGTH_SHORT).show();
        }*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        StaffMainActivity.super.onBackPressed();
                        //finish();
                    }

                }).create().show();

    }
}
