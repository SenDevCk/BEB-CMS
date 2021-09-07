package bih.nic.bsphcl.beb_cms.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import bih.nic.bsphcl.beb_cms.R;

import bih.nic.bsphcl.beb_cms.servises.LoginLoader;
import bih.nic.bsphcl.beb_cms.utilities.Utiilties;

public class LoginActivity extends AppCompatActivity {

    TextView login_text;
    EditText edit_uid,edit_pass;
    private static String imei = "";
    TelephonyManager tm;
    TextView text_ver;
    String version;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edit_uid = (EditText) findViewById(R.id.edit_uid);
        edit_pass = (EditText) findViewById(R.id.edit_pass);
        text_ver = (TextView) findViewById(R.id.text_ver);
        login_text = (TextView) findViewById(R.id.login_text);
        //text_ver = (TextView) findViewById(R.id.text_imei);
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/header_font.ttf");
        login_text.setTypeface(face);
        readPhoneState();
    }

    public void loginClick(View view) {
        if (edit_uid.getText().toString().trim().equals("")) {
            Toast.makeText(this, "Enter User Name", Toast.LENGTH_SHORT).show();
        } else if (edit_pass.getText().toString().trim().equals("")) {
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
        }else if (!Utiilties.isOnline(LoginActivity.this)) {
            Toast.makeText(LoginActivity.this, "Please go online !", Toast.LENGTH_SHORT).show();
        } else {
            new LoginLoader(LoginActivity.this).execute(edit_uid.getText().toString().trim() + "|" + edit_pass.getText().toString().trim() + "|" + imei.trim());
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        //readPhoneState();
    }

    public void readPhoneState() {

        try {
            tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            if (tm != null) ;
            if (ActivityCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                imei = tm.getDeviceId();
            } else {
                imei = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID).toUpperCase();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            version = LoginActivity.this.getPackageManager().getPackageInfo(LoginActivity.this.getPackageName(), 0).versionName;
            Log.e("App Version : ", "" + version + " ( " + imei + " )");
            text_ver.setText("App Version " + version + " ( " + imei + " )");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
