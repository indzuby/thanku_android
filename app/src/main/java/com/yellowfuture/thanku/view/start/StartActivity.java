package com.yellowfuture.thanku.view.start;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.yellowfuture.thanku.R;
import com.yellowfuture.thanku.utils.Utils;
import com.yellowfuture.thanku.view.adapter.StartPagerAdapter;
import com.yellowfuture.thanku.view.basic.BaseActivity;
import com.yellowfuture.thanku.view.main.LoginActivity;
import com.yellowfuture.thanku.view.main.SignUpActivity;

/**
 * Created by zuby on 2016. 7. 12..
 */
public class StartActivity extends BaseActivity {

    ViewPager mViewPager;
    LinearLayout mOvalLayout;

    @Override
    public void initView() {
        mViewPager = (ViewPager) findViewById(R.id.startViewPager);
        mOvalLayout = (LinearLayout) findViewById(R.id.ovalLayout);

    }

    @Override
    public void init() {
        initView();
        mViewPager.setAdapter(new StartPagerAdapter(this));
        Utils.setOvalContainer(this, mOvalLayout, 4);
        Utils.selectOval(mOvalLayout, 0);

        findViewById(R.id.closeButton).setOnClickListener(this);
        findViewById(R.id.signUpButton).setOnClickListener(this);
        findViewById(R.id.loginButton).setOnClickListener(this);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Utils.selectOval(mOvalLayout, position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        init();
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;

        if (v.getId() == R.id.closeButton) {
            finish();
            return ;
        } else if (v.getId() == R.id.signUpButton) {
            intent = new Intent(StartActivity.this, SignUpActivity.class);
        } else if (v.getId() == R.id.loginButton) {
            intent = new Intent(StartActivity.this, LoginActivity.class);
        }
        startActivityForResult(intent, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            finish();
        }
    }
}
