package com.yellowfuture.thanku.view.service;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yellowfuture.thanku.R;
import com.yellowfuture.thanku.utils.ContextUtils;
import com.yellowfuture.thanku.view.basic.BaseActivity;

/**
 * Created by zuby on 2016. 7. 13..
 */
public class ErrandActivity extends BaseActivity {


    EditText mOrderTelEditText;
    TextView mReceiveAddressTextView;
    EditText mMemoEditText;
    ImageView mPhotoImageView;


    View mPhotoButton;


    @Override
    public void initView() {
        mOrderTelEditText = (EditText) findViewById(R.id.orderTelEditText);
        mReceiveAddressTextView = (TextView) findViewById(R.id.addressTextView);
        mMemoEditText = (EditText) findViewById(R.id.memoEditText);
        mPhotoImageView = (ImageView) findViewById(R.id.photoImageView);
        mPhotoButton = findViewById(R.id.photoButton);
    }

    public void initActionBar() {
        ContextUtils.getActionBar(this, getSupportActionBar(), R.layout.actionbar_default);
        getSupportActionBar().setElevation(0);
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.serviceErrand));
        findViewById(R.id.back).setOnClickListener(this);
    }

    @Override
    public void init() {
        initView();
        initActionBar();

        mReceiveAddressTextView.setOnClickListener(this);
        mPhotoButton.setOnClickListener(this);
        findViewById(R.id.addCartButton).setOnClickListener(this);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_errand);
        init();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent = null;
        if(v.getId() == R.id.addressTextView) {

        }else if(v.getId() == R.id.photoButton) {
            Toast.makeText(this,"사진 첨부",Toast.LENGTH_SHORT).show();

        }else if(v.getId() == R.id.addCartButton) {
            Toast.makeText(this,"장바구니 담기",Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK)
            finish();

    }
}
