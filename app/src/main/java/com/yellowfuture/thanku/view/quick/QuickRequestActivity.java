package com.yellowfuture.thanku.view.quick;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.skp.Tmap.TMapPoint;
import com.yellowfuture.thanku.R;
import com.yellowfuture.thanku.utils.CodeDefinition;
import com.yellowfuture.thanku.utils.ContextUtils;
import com.yellowfuture.thanku.view.common.BaseActivity;


/**
 * Created by zuby on 2016. 6. 19..
 */
public class QuickRequestActivity extends BaseActivity {

    TMapPoint mStartPoint,mEndPoint;
    EditText mRequestEditText;

    @Override
    public void init() {
        initActionbar();
        double lat,lon;
        String startName,endName;

        lat = getIntent().getDoubleExtra(CodeDefinition.PARAM_START_LATITUDE,0);
        lon = getIntent().getDoubleExtra(CodeDefinition.PARAM_START_LONGITUDE,0);
        startName = getIntent().getStringExtra(CodeDefinition.PARAM_START_NAME);
        mStartPoint = new TMapPoint(lat,lon);

        Location startLocation = new Location("start point");
        startLocation.setLatitude(lat);
        startLocation.setLongitude(lon);

        lat = getIntent().getDoubleExtra(CodeDefinition.PARAM_END_LATITUDE,0);
        lon = getIntent().getDoubleExtra(CodeDefinition.PARAM_END_LONGITUDE,0);
        endName = getIntent().getStringExtra(CodeDefinition.PARAM_END_NAME);
        mEndPoint = new TMapPoint(lat,lon);
        Location endLocation = new Location("start point");
        endLocation.setLatitude(lat);
        endLocation.setLongitude(lon);

        TextView startView = (TextView) findViewById(R.id.start_text_view);
        TextView endView = (TextView) findViewById(R.id.end_text_view);
        TextView distanceView = (TextView) findViewById(R.id.distance_text_view);
        TextView defaultCost = (TextView) findViewById(R.id.default_cost);
        TextView farDiscountCost = (TextView) findViewById(R.id.far_discount_cost);
        TextView totalCost = (TextView) findViewById(R.id.total_cost);
        mRequestEditText = (EditText) findViewById(R.id.request_text);

        findViewById(R.id.request_button).setOnClickListener(this);

        startView.setText(startName);
        endView.setText(endName);

//        float distance = startLocation.distanceTo(endLocation);
        double distance = getIntent().getDoubleExtra(CodeDefinition.PARAM_DISTANCE,0f);
        distanceView.setText(Math.round(distance/10)/100f+"km");

    }

    public void initActionbar() {
        ContextUtils.getActionBar(this, getSupportActionBar(), R.layout.actionbar_default);
        findViewById(R.id.back).setOnClickListener(this);
        ((TextView)findViewById(R.id.title)).setText("배송 요청");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_request);
        init();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(v.getId() == R.id.request_button) {

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
