package com.yellowfuture.thanku.view.restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.yellowfuture.thanku.R;
import com.yellowfuture.thanku.view.adapter.RestaurantPagerAdapter;
import com.yellowfuture.thanku.view.common.BaseActivity;

/**
 * Created by zuby on 2016-07-17.
 */
public class RestaurantActivity extends BaseActivity{

    ViewPager mRestaurantViewPager;
    RestaurantPagerAdapter mAdapter;
    TabLayout mTabs;

    @Override
    public void initView() {
        mRestaurantViewPager = (ViewPager) findViewById(R.id.restaurantViewPager);
        mTabs = (TabLayout) findViewById(R.id.tabs);

    }
    public void initActionBar(){
        findViewById(R.id.back).setOnClickListener(this);
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.serviceRestaurant));
    }

    @Override
    public void init() {
        initView();
        initActionBar();

        mAdapter = new RestaurantPagerAdapter(getSupportFragmentManager(),this);
        mRestaurantViewPager.setAdapter(mAdapter);
        mTabs.setupWithViewPager(mRestaurantViewPager);
        for(int i = 0; i<mTabs.getTabCount();i++) {
            TabLayout.Tab tab = mTabs.getTabAt(i);
            tab.setText(mAdapter.getTitle(i));
        }

        findViewById(R.id.fabButton).setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        init();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(v.getId() == R.id.fabButton) {
            RestaurantFabPopup fabPopup = new RestaurantFabPopup(this);
            fabPopup.show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
