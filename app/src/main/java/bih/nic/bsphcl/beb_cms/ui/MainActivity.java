package bih.nic.bsphcl.beb_cms.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import bih.nic.bsphcl.beb_cms.R;
import bih.nic.bsphcl.beb_cms.adapters.ComCountAdapter;
import bih.nic.bsphcl.beb_cms.entities.ComplainCountTypeWiseEntity;
import bih.nic.bsphcl.beb_cms.entities.UserInformationEntity;
import bih.nic.bsphcl.beb_cms.interfaces.ComplaintCountTypeWiseBinder;
import bih.nic.bsphcl.beb_cms.servises.CompalinCountTypewiseLoader;
import bih.nic.bsphcl.beb_cms.utilities.CommonPref;
import bih.nic.bsphcl.beb_cms.utilities.Utiilties;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    CollapsingToolbarLayout toolBarLayout;
    RecyclerView recycler_list_consumer;
    TextView text_no_data_found,text_head;
    ComCountAdapter comCountAdapter;
    UserInformationEntity userInformationEntity;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userInformationEntity = CommonPref.getUserDetails(MainActivity.this);

        TextView text_head=(TextView)findViewById(R.id.text_head);
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/header_font.ttf");
        text_head.setTypeface(face);
        TextView text_contact=(TextView)findViewById(R.id.text_contact);
        text_contact.setText("Mb:- "+userInformationEntity.getContactNo());
        TextView text_name=(TextView)findViewById(R.id.text_name);
        text_name.setText(""+userInformationEntity.getUserName());
        TextView text_email=(TextView)findViewById(R.id.text_imei);
        text_email.setText("IMEI : "+userInformationEntity.getImeiNo());
        text_email.setVisibility(View.GONE);
        TextView text_sub=(TextView)findViewById(R.id.text_sub);
        text_sub.setText("( "+userInformationEntity.getRole()+" )");
        ImageView logo_img=(ImageView)findViewById(R.id.logo_img) ;

        if (userInformationEntity.getDivision().startsWith("2")) {
            logo_img.setImageDrawable(getResources().getDrawable(R.drawable.sblogo1));
            //toolbar.setLogo(getResources().getDrawable(R.drawable.sblogo1));
        } else if (userInformationEntity.getDivision().startsWith("1")) {
            logo_img.setImageDrawable(getResources().getDrawable(R.drawable.nblogo));
            //toolbar.setLogo(getResources().getDrawable(R.drawable.nblogo));
        } else {
            logo_img.setImageDrawable(getResources().getDrawable(R.drawable.bsphcl_logo));
            //toolbar.setLogo(getResources().getDrawable(R.drawable.bsphcl_logo));
        }
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle("");
        AppBarLayout mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    isShow = true;
                    toolBarLayout.setTitle("CRMS");
                } else if (isShow) {
                    isShow = false;
                    toolBarLayout.setTitle("");
                }
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.VISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadpageData();
            }
        });
        text_no_data_found = (TextView) findViewById(R.id.text_no_data_found);
        recycler_list_consumer = (RecyclerView) findViewById(R.id.recycler_list_consumer);


        loadpageData();

    }

    private void loadpageData() {
        CompalinCountTypewiseLoader.complainCountLoader(new ComplaintCountTypeWiseBinder() {
            @Override
            public void countFound(ArrayList<ComplainCountTypeWiseEntity> grivanceEntityArrayList) {
                if (grivanceEntityArrayList.size() > 0) {
                    text_no_data_found.setVisibility(View.GONE);
                    comCountAdapter = new ComCountAdapter(grivanceEntityArrayList, MainActivity.this);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this);
                    recycler_list_consumer.setLayoutManager(mLayoutManager);
                    recycler_list_consumer.setItemAnimator(new DefaultItemAnimator());
                    recycler_list_consumer.setAdapter(comCountAdapter);
                } else {
                    text_no_data_found.setVisibility(View.VISIBLE);
                    recycler_list_consumer.setVisibility(View.GONE);
                }
            }

            @Override
            public void countNotFound(String response) {
                //list_com_item.invalidate();
                toolbar.setSubtitle("" + response);
                Toast.makeText(MainActivity.this, "" + response, Toast.LENGTH_SHORT).show();
            }
        });
        if (Utiilties.isOnline(MainActivity.this)) {
            new CompalinCountTypewiseLoader(MainActivity.this).execute(userInformationEntity.getUserID().trim() + "@" + userInformationEntity.getPassword().trim() + "@" + userInformationEntity.getImeiNo().trim() );
        } else {
            Toast.makeText(this, "Please go Online and Refresh by scrolling down page !", Toast.LENGTH_SHORT).show();
            text_no_data_found.setText("Please go Online and Refresh by scrolling down page !");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cp_menu: {
                //AlertDialogForPrinter();
                break;
            }
           /* case android.R.id.home: {
                finish();
                break;
            }*/
        }
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
                        MainActivity.super.onBackPressed();
                        //finish();
                    }

                }).create().show();

    }
}