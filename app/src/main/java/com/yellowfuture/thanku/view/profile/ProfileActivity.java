package com.yellowfuture.thanku.view.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.yellowfuture.thanku.R;
import com.yellowfuture.thanku.utils.CodeDefinition;
import com.yellowfuture.thanku.view.adapter.ProfilePagerAdapter;
import com.yellowfuture.thanku.view.common.BaseActivity;

/**
 * Created by zuby on 2016. 7. 13..
 */
public class ProfileActivity extends BaseActivity{

    ViewPager mProfileViewPager;
    ProfilePagerAdapter mAdapter;
    TabLayout mTabs;
    int startState;
    @Override
    public void initView() {
        mProfileViewPager = (ViewPager) findViewById(R.id.profileViewPager);
        mTabs = (TabLayout) findViewById(R.id.tabs);

    }
    public void initActionBar(){
        findViewById(R.id.back).setOnClickListener(this);
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.profile));
    }

    @Override
    public void init() {
        super.init();
        initActionBar();
        initView();

        startState = getIntent().getIntExtra(CodeDefinition.PROFILE_START_PARAM,0);

        mAdapter = new ProfilePagerAdapter(getSupportFragmentManager(),this);

        mProfileViewPager.setAdapter(mAdapter);
        mTabs.setupWithViewPager(mProfileViewPager);
        for(int i = 0; i<mTabs.getTabCount();i++) {
            TabLayout.Tab tab = mTabs.getTabAt(i);
            tab.setText(mAdapter.getTitle(i));
        }
        mProfileViewPager.setCurrentItem(startState);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        init();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
