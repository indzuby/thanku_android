package com.yellowfuture.thanku.view;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yellowfuture.thanku.R;
import com.yellowfuture.thanku.utils.CodeDefinition;
import com.yellowfuture.thanku.utils.ContextUtils;
import com.yellowfuture.thanku.view.adapter.MainTabPagerAdapter;
import com.yellowfuture.thanku.view.common.BaseActivity;

public class MainActivity extends BaseActivity {

    TabLayout mTabLayout;
    ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    public void init(){


        mViewPager = (ViewPager) findViewById(R.id.main_view_pager);
        mTabLayout = (TabLayout) findViewById(R.id.tabs);

        MainTabPagerAdapter pagerAdapter = new MainTabPagerAdapter(getSupportFragmentManager(),this);
        mViewPager.setAdapter(pagerAdapter);

        mTabLayout.setupWithViewPager(mViewPager);
        for(int i = 0; i<mTabLayout.getTabCount();i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            tab.setCustomView(pagerAdapter.getTabView(i));
        }
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                selectedTab(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        selectedTab(0);
    }
    public void selectedTab(int position){
        for(int i = 0; i<mTabLayout.getTabCount();i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            ImageView img = (ImageView) tab.getCustomView().findViewById(R.id.image);
            TextView textView = (TextView) tab.getCustomView().findViewById(R.id.textView);
            textView.setSelected(false);
            img.setSelected(false);
        }
        TabLayout.Tab tab = mTabLayout.getTabAt(position);
        ImageView img = (ImageView)tab.getCustomView().findViewById(R.id.image);
        TextView textView = (TextView) tab.getCustomView().findViewById(R.id.textView);
        textView.setSelected(true);
        img.setSelected(true);
        tab.select();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        switch (requestCode) {
            case CodeDefinition.REQUEST_PERMISSIONS_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                    init();

                } else {
                    Toast.makeText(this,"위치 권한이 필요합니다.",Toast.LENGTH_SHORT).show();
                    finish();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
