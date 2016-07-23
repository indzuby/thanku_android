package com.yellowfuture.thanku.view.restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.yellowfuture.thanku.R;
import com.yellowfuture.thanku.domain.Category;
import com.yellowfuture.thanku.network.controller.RestaurantController;
import com.yellowfuture.thanku.utils.CodeDefinition;
import com.yellowfuture.thanku.utils.SessionUtils;
import com.yellowfuture.thanku.view.adapter.RestaurantPagerAdapter;
import com.yellowfuture.thanku.view.common.BaseActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zuby on 2016-07-17.
 */
public class RestaurantActivity extends BaseActivity{

    ViewPager mRestaurantViewPager;
    RestaurantPagerAdapter mAdapter;
    TabLayout mTabs;
    List<Category> mCategories;
    @Override
    public void initView() {
        mRestaurantViewPager = (ViewPager) findViewById(R.id.restaurantViewPager);
        mTabs = (TabLayout) findViewById(R.id.tabs);
        mAdapter = new RestaurantPagerAdapter(getSupportFragmentManager(), mCategories);
        mRestaurantViewPager.setAdapter(mAdapter);
        mTabs.setupWithViewPager(mRestaurantViewPager);
        for(int i = 0; i<mTabs.getTabCount();i++) {
            TabLayout.Tab tab = mTabs.getTabAt(i);
            tab.setText(mAdapter.getTitle(i));
        }

    }
    public void initActionBar(){
        findViewById(R.id.back).setOnClickListener(this);
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.serviceRestaurant));
    }
    public void initData(){
        mAccessToken = SessionUtils.getString(this, CodeDefinition.ACCESS_TOKEN,"");
        RestaurantController.getInstance(this).findCategoryAll(mAccessToken,new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if(response.code()==200) {
                    mCategories = response.body();
                    initView();
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {

            }
        });
    }
    @Override
    public void init() {
        super.init();
        initData();
        initActionBar();


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
