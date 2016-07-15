package com.yellowfuture.thanku.view.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yellowfuture.thanku.R;
import com.yellowfuture.thanku.utils.Utils;
import com.yellowfuture.thanku.view.basic.BaseActivity;

/**
 * Created by zuby on 2016. 7. 14..
 */
public class SearchActivity extends BaseActivity {
    @Override
    public void initView() {

    }

    public void initActionBar() {
        Utils.getActionBar(this, getSupportActionBar(), R.layout.actionbar_default);
        getSupportActionBar().setElevation(0);
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.findAddress));
        findViewById(R.id.back).setOnClickListener(this);
    }

    @Override
    public void init() {
        initActionBar();
        initView();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_search);
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
