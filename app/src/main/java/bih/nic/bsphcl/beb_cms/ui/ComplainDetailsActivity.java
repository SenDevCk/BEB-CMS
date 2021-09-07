package bih.nic.bsphcl.beb_cms.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

import bih.nic.bsphcl.beb_cms.R;
import bih.nic.bsphcl.beb_cms.adapters.PgLogAdapter;
import bih.nic.bsphcl.beb_cms.adapters.SpinnerDataAdapter;
import bih.nic.bsphcl.beb_cms.database.DataBaseHelper;
import bih.nic.bsphcl.beb_cms.entities.ComplainDetailsEntity;
import bih.nic.bsphcl.beb_cms.entities.ComplainEntity;
import bih.nic.bsphcl.beb_cms.entities.SpinnerData;
import bih.nic.bsphcl.beb_cms.entities.UserInformationEntity;
import bih.nic.bsphcl.beb_cms.interfaces.CloseComplainBinder;
import bih.nic.bsphcl.beb_cms.interfaces.ComplainDetailsListener;
import bih.nic.bsphcl.beb_cms.servises.CloseComplainService;
import bih.nic.bsphcl.beb_cms.servises.ComplainDetailsLoader;
import bih.nic.bsphcl.beb_cms.utilities.CommonPref;
import bih.nic.bsphcl.beb_cms.utilities.SpinerDataFounder;
import bih.nic.bsphcl.beb_cms.utilities.Utiilties;

public class ComplainDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar_gd;
    //String[] activity_group={" -- SELECT -- ","ACTIVITIES PERFORMED","ABNORMALITIES OBSERVED","BILLING/FINANCIAL ACTIVITIES","REASON FOR INCOMPLETION DUE TO BSPH","REASON FOR INCOMPLETION DUE TO CUSTOMER","CANCELLATION REASONS"};
    Spinner spn_ac_gp, spn_ac_cd;
    private int mYear, mMonth, mDay;
    DatePickerDialog datedialog;
    TextView tvdob, text_id, text_mb, text_type, text_status;
    TextView text_div, text_sub_div, text_section, text_com_subtype, text_asc;
    ImageView btncaladob;
    private int LAUNCH_SECOND_ACTIVITY = 10;
    ComplainEntity complainEntity;
    ComplainDetailsEntity complainDetailsEntity1;
    RecyclerView recycler_list_pglog;
    String activitygp_id = "", activitycd_id = "";
    private EditText et_rem;
    LinearLayout ll_btn, ll_entry;
    UserInformationEntity userInformationEntity;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain_details);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar_gd = (Toolbar) findViewById(R.id.toolbar_gd);
            //toolbar_gd.setNavigationIcon(getResources().getDrawable(R.drawable.leftarrow));
            toolbar_gd.setTitle("Complaint Details");
            toolbar_gd.setSubtitle("CRMS");
            toolbar_gd.setSubtitleTextColor(getResources().getColor(R.color.white));
            toolbar_gd.setTitleTextColor(getResources().getColor(R.color.white));
            setSupportActionBar(toolbar_gd);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar_gd.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
        complainEntity = (ComplainEntity) getIntent().getSerializableExtra("object");
        inIt();
    }

    private void inIt() {
        userInformationEntity = CommonPref.getUserDetails(ComplainDetailsActivity.this);
        ll_btn = findViewById(R.id.ll_btn);
        ll_entry = findViewById(R.id.ll_entry);
        recycler_list_pglog = findViewById(R.id.recycler_list_pglog);
        text_id = findViewById(R.id.text_id);
        text_mb = findViewById(R.id.text_mb);
        text_type = findViewById(R.id.text_type);
        text_status = findViewById(R.id.text_status);
        text_div = findViewById(R.id.text_div);
        text_sub_div = findViewById(R.id.text_sub_div);
        text_section = findViewById(R.id.text_section);
        text_com_subtype = findViewById(R.id.text_com_subtype);
        text_asc = findViewById(R.id.text_asc);
        et_rem = (EditText) findViewById(R.id.et_rem);
        tvdob = findViewById(R.id.tvdob);
        btncaladob = (ImageView) findViewById(R.id.btncaladob);
        btncaladob.setOnClickListener(this);
        loadActivityGroupSpiner();
        loadActivityGroupSpiner();
        loadComplainDetails();
    }

    private void loadActivityCodeSpiner(final String code) {
        spn_ac_cd = (Spinner) findViewById(R.id.spn_ac_cd);
        final ArrayList<SpinnerData> spinnerData = new SpinerDataFounder().getSpinerDataFromActivityCode(new DataBaseHelper(ComplainDetailsActivity.this).getActivityCode(code));
        spn_ac_cd.setAdapter(new SpinnerDataAdapter(ComplainDetailsActivity.this, spinnerData));
        spn_ac_cd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    activitycd_id = spinnerData.get(position).getId();
                } else {
                    activitycd_id = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void loadActivityGroupSpiner() {
        spn_ac_gp = (Spinner) findViewById(R.id.spn_ac_gp);
        final ArrayList<SpinnerData> spinnerData = new SpinerDataFounder().getSpinerDataFromActivityGroup(new DataBaseHelper(ComplainDetailsActivity.this).getActivityGroup());
        spn_ac_gp.setAdapter(new SpinnerDataAdapter(ComplainDetailsActivity.this, spinnerData));
        spn_ac_gp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    activitygp_id = spinnerData.get(position).getId();
                    loadActivityCodeSpiner(activitygp_id);
                } else {
                    activitygp_id = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void loadComplainDetails() {
        ComplainDetailsLoader.complainDetailsBinder(new ComplainDetailsListener() {
            @Override
            public void complainDetailsFound(ComplainDetailsEntity complainDetailsEntity) {

                complainDetailsEntity1 = complainDetailsEntity;
                if (complainDetailsEntity1.getResponsibleUser().trim().equals(userInformationEntity.getUserID().trim())) {
                    ll_btn.setVisibility(View.VISIBLE);
                    ll_entry.setVisibility(View.VISIBLE);
                } else {
                    ll_btn.setVisibility(View.GONE);
                    ll_entry.setVisibility(View.GONE);
                }
                text_id.setText("" + complainDetailsEntity1.getCompNo());
                text_mb.setText("" + complainDetailsEntity1.getComplTypeName());
                text_com_subtype.setText("" + complainDetailsEntity1.getComplSubTypeName());
                text_type.setText("" + complainDetailsEntity1.getEntryTime());
                text_status.setText("" + complainDetailsEntity1.getLastUpdateTime());
                text_asc.setText("" + complainDetailsEntity1.getAscalationLevel());

                text_div.setText("" + complainDetailsEntity1.getDivName());
                text_sub_div.setText("" + complainDetailsEntity1.getSubDivName());
                text_section.setText("" + complainDetailsEntity1.getSectionName());

                if (complainDetailsEntity1.getPgLogEntities() != null) {
                    if (complainDetailsEntity1.getPgLogEntities().size() > 0) {
                        PgLogAdapter comCountAdapter = new PgLogAdapter(complainDetailsEntity1.getPgLogEntities(), ComplainDetailsActivity.this);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ComplainDetailsActivity.this);
                        recycler_list_pglog.setLayoutManager(mLayoutManager);
                        recycler_list_pglog.setItemAnimator(new DefaultItemAnimator());
                        recycler_list_pglog.setAdapter(comCountAdapter);
                    } else {
                        Toast.makeText(ComplainDetailsActivity.this, "No log found !", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void complainDetailsNotFound(String msg) {
                Toast.makeText(ComplainDetailsActivity.this, "" + msg, Toast.LENGTH_SHORT).show();
            }
        });
        if (Utiilties.isOnline(ComplainDetailsActivity.this)) {
            new ComplainDetailsLoader(ComplainDetailsActivity.this).execute(complainEntity.getComplaintNo().trim() + "&desc=" + userInformationEntity.getUserID().trim() + "@" + userInformationEntity.getPassword().trim() + "@" + userInformationEntity.getImeiNo().trim());
        } else {
            Toast.makeText(this, "Go online !", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btncaladob) {
            ShowDialog();
        } else if (v.getId() == R.id.but_cl_com) {
            if (validate()) {
                CloseComplainService.closeComplainListener(new CloseComplainBinder() {
                    @Override
                    public void closeComplainSuccess(String sucess) {
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
                    }

                    @Override
                    public void closeComplainUnSucess(String error) {
                        Toast.makeText(ComplainDetailsActivity.this, "" + error, Toast.LENGTH_SHORT).show();
                    }
                });
                new CloseComplainService(ComplainDetailsActivity.this).execute();
            }
        }
    }

    private boolean validate() {
        boolean validate = false;
        if (activitygp_id.equals("")) {
            validate = false;
            Toast.makeText(this, "--Select Activity Group--", Toast.LENGTH_SHORT).show();
        } else if (activitycd_id.equals("")) {
            validate = false;
            Toast.makeText(this, "--Select Activity Code--", Toast.LENGTH_SHORT).show();
        } else if (et_rem.getText().toString().trim().equals("")) {
            validate = false;
            Toast.makeText(this, "Please Enter Remarks", Toast.LENGTH_SHORT).show();
        } else if (tvdob.getText().toString().trim().equals("")) {
            validate = false;
            Toast.makeText(this, "Please Enter Remarks", Toast.LENGTH_SHORT).show();
        } else {
            validate = true;
        }
        return validate;
    }

    private void ShowDialog() {


        Calendar c = Calendar.getInstance();

        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        datedialog = new DatePickerDialog(ComplainDetailsActivity.this,
                mDateSetListener, mYear, mMonth, mDay);
        //datedialog.getDatePicker().setMinDate(System.currentTimeMillis()+1);
        datedialog.show();

    }

    DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            mYear = selectedYear;
            mMonth = selectedMonth;
            mDay = selectedDay;
            try {
                if (mDay < 10 && (mMonth + 1) > 9) {
                    mDay = Integer.parseInt("0" + mDay);
                    tvdob.setText(new StringBuilder().append("0" + mDay).append("/").append(mMonth + 1).append("/").append(mYear));
                } else if ((mMonth + 1) < 10 && mDay > 9) {
                    mMonth = Integer.parseInt("0" + mMonth);
                    tvdob.setText(new StringBuilder().append(mDay).append("/").append("0" + (mMonth + 1)).append("/").append(mYear));
                } else if ((mMonth + 1) < 10 && mDay < 10) {
                    mDay = Integer.parseInt("0" + mDay);
                    mMonth = Integer.parseInt("0" + mMonth);
                    tvdob.setText(new StringBuilder().append("0" + mDay).append("/").append("0" + (mMonth + 1)).append("/").append(mYear));
                } else {
                    tvdob.setText(new StringBuilder().append(mDay).append("/").append(mMonth + 1).append("/").append(mYear));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
}
