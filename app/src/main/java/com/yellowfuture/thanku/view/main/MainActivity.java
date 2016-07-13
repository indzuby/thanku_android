package com.yellowfuture.thanku.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;

import com.yellowfuture.thanku.R;
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

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
