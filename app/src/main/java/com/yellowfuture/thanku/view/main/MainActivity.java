package com.yellowfuture.thanku.view.main;

import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.TextView;

import com.skp.Tmap.TMapData;
import com.skp.Tmap.TMapPOIItem;
import com.yellowfuture.thanku.R;
import com.yellowfuture.thanku.control.GpsControl;
import com.yellowfuture.thanku.utils.CodeDefinition;
import com.yellowfuture.thanku.utils.SessionUtils;
import com.yellowfuture.thanku.utils.Utils;
import com.yellowfuture.thanku.view.profile.ProfileActivity;
import com.yellowfuture.thanku.view.service.BuyActivity;
import com.yellowfuture.thanku.view.common.BaseActivity;
import com.yellowfuture.thanku.view.service.ErrandActivity;
import com.yellowfuture.thanku.view.service.QuickActivity;
import com.yellowfuture.thanku.view.restaurant.RestaurantActivity;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    DrawerLayout mDrawerLayout;
    boolean active = true;
    TMapData mMapData;
    LocationManager mLocationManager;
    @Override
    public void initView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

    }

    public void initActionBar() {
        findViewById(R.id.menu).setOnClickListener(this);

    }

    public void initNavigationLayout() {
        findViewById(R.id.profileLayout).setOnClickListener(this);
        findViewById(R.id.notificationLayout).setOnClickListener(this);
        findViewById(R.id.cartLayout).setOnClickListener(this);
        findViewById(R.id.orderlayout).setOnClickListener(this);
        findViewById(R.id.announcementLayout).setOnClickListener(this);
        findViewById(R.id.qnaLayout).setOnClickListener(this);
        findViewById(R.id.advertisementLayout).setOnClickListener(this);
    }

    public void initMyLocationView() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(active) {
                    try {
                        Location location = GpsControl.getInstance(MainActivity.this).getLocation();
                        String address = mMapData.convertGpsToAddress(location.getLatitude(), location.getLongitude());
                        ArrayList<TMapPOIItem> list = mMapData.findAllPOI(address, 1);
                        if (list.size() > 0 && active) {
                            final TMapPOIItem item = list.get(0);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    TextView nowAddressTextView = (TextView) findViewById(R.id.nowAddressTextView);
                                    nowAddressTextView.setText(Utils.parsePOIAddressOld(item));
                                }
                            });
                        }
                        Thread.sleep(10000);
                    } catch (Exception e) {

                    }
                }

            }
        }).start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    public void init() {
        super.init();
        initActionBar();
        initView();
        initNavigationLayout();
        mMapData = new TMapData();
        initMyLocationView();
        findViewById(R.id.serviceRestaurantLayout).setOnClickListener(this);
        findViewById(R.id.serviceBuyLayout).setOnClickListener(this);
        findViewById(R.id.serviceErrandLayout).setOnClickListener(this);
        findViewById(R.id.serviceQuickLayout).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent;
        if (v.getId() == R.id.menu) {
            mDrawerLayout.openDrawer(GravityCompat.START);
        } else if (v.getId() == R.id.serviceRestaurantLayout) {
            intent = new Intent(MainActivity.this, RestaurantActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.serviceBuyLayout) {
            intent = new Intent(MainActivity.this, BuyActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.serviceErrandLayout) {
            intent = new Intent(MainActivity.this, ErrandActivity.class);
            startActivity(intent);

        } else if (v.getId() == R.id.serviceQuickLayout) {
            intent = new Intent(MainActivity.this, QuickActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.profileLayout) {
            intent = new Intent(MainActivity.this, ProfileActivity.class);
            intent.putExtra(CodeDefinition.PROFILE_START_PARAM, 0);
            startActivity(intent);
            mDrawerLayout.closeDrawers();
        } else if (v.getId() == R.id.orderlayout) {
            intent = new Intent(MainActivity.this, ProfileActivity.class);
            intent.putExtra(CodeDefinition.PROFILE_START_PARAM, 1);
            startActivity(intent);
            mDrawerLayout.closeDrawers();
        } else if (v.getId() == R.id.cartLayout) {
            intent = new Intent(MainActivity.this, ProfileActivity.class);
            intent.putExtra(CodeDefinition.PROFILE_START_PARAM, 2);
            startActivity(intent);
            mDrawerLayout.closeDrawers();
        } else if (v.getId() == R.id.advertisementLayout || v.getId() == R.id.qnaLayout) {
            intent = new Intent(MainActivity.this, QnaActivity.class);
            startActivity(intent);
            mDrawerLayout.closeDrawers();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        active = false;
    }

}
