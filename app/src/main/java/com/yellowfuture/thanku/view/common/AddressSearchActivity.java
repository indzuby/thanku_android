package com.yellowfuture.thanku.view.common;

import android.os.Bundle;
import android.widget.TextView;

import com.yellowfuture.thanku.R;
import com.yellowfuture.thanku.utils.ContextUtils;
import com.yellowfuture.thanku.view.basic.BaseActivity;

/**
 * Created by zuby on 2016. 6. 17..
 */
public class AddressSearchActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_search);
        init();
    }

    public void initActionbar() {
        ContextUtils.getActionBar(this, getSupportActionBar(), R.layout.actionbar_default);
        findViewById(R.id.back).setOnClickListener(this);
        ((TextView)findViewById(R.id.title)).setText("검색");
    }

    @Override
    public void initView() {
    }

    @Override
    public void init() {
        initView();
        initActionbar();

    }
}
