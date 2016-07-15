package com.yellowfuture.thanku.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;

import com.yellowfuture.thanku.R;
import com.yellowfuture.thanku.view.profile.ProfileActivity;
import com.yellowfuture.thanku.view.service.BuyActivity;
import com.yellowfuture.thanku.view.basic.BaseActivity;
import com.yellowfuture.thanku.view.service.ErrandActivity;
import com.yellowfuture.thanku.view.service.QuickActivity;

public class MainActivity extends BaseActivity {

    DrawerLayout mDrawerLayout;

    @Override
    public void initView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

    }
    public void initActionBar(){
        findViewById(R.id.menu).setOnClickListener(this);

    }
    public void initNavigationLayout(){
        findViewById(R.id.profileLayout).setOnClickListener(this);
        findViewById(R.id.notificationLayout).setOnClickListener(this);
        findViewById(R.id.cartLayout).setOnClickListener(this);
        findViewById(R.id.orderlayout).setOnClickListener(this);
        findViewById(R.id.announcementLayout).setOnClickListener(this);
        findViewById(R.id.qnaLayout).setOnClickListener(this);
        findViewById(R.id.advertisementLayout).setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    public void init(){
        initActionBar();
        initView();
        initNavigationLayout();
        findViewById(R.id.serviceRestaurantLayout).setOnClickListener(this);
        findViewById(R.id.serviceBuyLayout).setOnClickListener(this);
        findViewById(R.id.serviceErrandLayout).setOnClickListener(this);
        findViewById(R.id.serviceQuickLayout).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent;
        if(v.getId() == R.id.menu) {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }else if(v.getId() == R.id.serviceRestaurantLayout) {

        }else if(v.getId() == R.id.serviceBuyLayout) {
            intent = new Intent(MainActivity.this, BuyActivity.class);
            startActivity(intent);
        }else if(v.getId() == R.id.serviceErrandLayout) {
            intent = new Intent(MainActivity.this, ErrandActivity.class);
            startActivity(intent);

        }else if(v.getId() == R.id.serviceQuickLayout) {
            intent = new Intent(MainActivity.this, QuickActivity.class);
            startActivity(intent);
        }else if(v.getId() == R.id.profileLayout) {
            intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
            mDrawerLayout.closeDrawers();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
