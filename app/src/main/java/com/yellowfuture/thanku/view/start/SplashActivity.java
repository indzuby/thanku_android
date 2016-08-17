package com.yellowfuture.thanku.view.start;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.yellowfuture.thanku.R;
import com.yellowfuture.thanku.control.GpsControl;
import com.yellowfuture.thanku.service.RegistrationIntentService;
import com.yellowfuture.thanku.utils.CodeDefinition;
import com.yellowfuture.thanku.utils.SessionUtils;
import com.yellowfuture.thanku.utils.Utils;
import com.yellowfuture.thanku.view.account.LoginActivity;
import com.yellowfuture.thanku.view.common.BaseActivity;
import com.yellowfuture.thanku.view.main.MainActivity;

/**
 * Created by zuby on 2016. 6. 16..
 */
public class SplashActivity extends BaseActivity {
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    boolean isReg = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Utils.checkPermission(this);
        GpsControl.getInstance(this).setMLocationManager((LocationManager) getSystemService(Context.LOCATION_SERVICE));
        GpsControl.getInstance(this).startGpsTrace();

    }

    @Override
    public void initView() {
        super.initView();

    }
    public void initGcm(){
        getInstanceIdToken();
        registBroadcastReceiver();
    }

    public void getInstanceIdToken() {
        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }
    }


    public void registBroadcastReceiver(){
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if(action.equals(CodeDefinition.REGISTRATION_COMPLETE)){
                    // 액션이 COMPLETE일 경우
                    String token = intent.getStringExtra("token");
                    SessionUtils.putString(getBaseContext(),CodeDefinition.REG_ID,token);
                    isReg = true;
                }

            }
        };
    }

    @Override
    public void init() {
        super.init();
        initView();
        initGcm();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(!isReg);

                    Thread.sleep(1000);
                    Intent intent = new Intent(SplashActivity.this, StartActivity.class);
                    if (mAccessToken != null & mAccessToken.length() > 0)
                        intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {

                }
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case CodeDefinition.REQUEST_PERMISSIONS_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    init();
                } else {
                    Toast.makeText(this, "위치 권한이 필요합니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }
                return;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(CodeDefinition.REGISTRATION_COMPLETE));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if(resultCode != ConnectionResult.SUCCESS) {
            if(apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this,resultCode,9000).show();
            }else
                finish();
            return false;
        }
        return true;
    }
}
