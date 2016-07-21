package com.yellowfuture.thanku.view.start;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.yellowfuture.thanku.R;
import com.yellowfuture.thanku.control.GpsControl;
import com.yellowfuture.thanku.utils.CodeDefinition;
import com.yellowfuture.thanku.utils.Utils;
import com.yellowfuture.thanku.view.common.BaseActivity;

/**
 * Created by zuby on 2016. 6. 16..
 */
public class SplashActivity extends BaseActivity {
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

    }

    @Override
    public void init() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    Intent intent = new Intent(SplashActivity.this, StartActivity.class);
                    startActivity(intent);
                    finish();
                }catch (Exception e){

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
                    Toast.makeText(this,"위치 권한이 필요합니다.",Toast.LENGTH_SHORT).show();
                    finish();
                }
                return;
            }
        }
    }
}
