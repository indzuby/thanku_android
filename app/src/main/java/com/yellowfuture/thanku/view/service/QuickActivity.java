package com.yellowfuture.thanku.view.service;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yellowfuture.thanku.R;
import com.yellowfuture.thanku.utils.CodeDefinition;
import com.yellowfuture.thanku.utils.Utils;
import com.yellowfuture.thanku.view.basic.BaseActivity;
import com.yellowfuture.thanku.view.search.SearchActivity;

/**
 * Created by zuby on 2016. 7. 13..
 */
public class QuickActivity extends BaseActivity {


    EditText mOrderTelEditText;
    EditText mReceiveTelEditText;
    TextView mStartAddressTextView,mDestinationAddressTextView;
    EditText mMemoEditText;
    ImageView mPhotoImageView;


    View mPhotoButton;


    @Override
    public void initView() {
        mOrderTelEditText = (EditText) findViewById(R.id.orderTelEditText);
        mReceiveTelEditText = (EditText) findViewById(R.id.receiveTelEditText);
        mStartAddressTextView = (TextView) findViewById(R.id.startAddressTextView);
        mDestinationAddressTextView = (TextView) findViewById(R.id.destinationAddressTextView);
        mMemoEditText = (EditText) findViewById(R.id.memoEditText);
        mPhotoImageView = (ImageView) findViewById(R.id.photoImageView);
        mPhotoButton = findViewById(R.id.photoButton);
    }

    public void initActionBar() {
        Utils.getActionBar(this, getSupportActionBar(), R.layout.actionbar_default);
        getSupportActionBar().setElevation(0);
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.serviceQuick));
        findViewById(R.id.back).setOnClickListener(this);
    }

    @Override
    public void init() {
        initView();
        initActionBar();

        findViewById(R.id.startAddressLayout).setOnClickListener(this);
        findViewById(R.id.destinationAddressLayout).setOnClickListener(this);
        mPhotoButton.setOnClickListener(this);
        findViewById(R.id.addCartButton).setOnClickListener(this);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_quick);
        init();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent = null;
        if(v.getId() == R.id.startAddressLayout || v.getId() == R.id.destinationAddressLayout) {
            intent = new Intent(QuickActivity.this, SearchActivity.class);
            if(v.getId() == R.id.startAddressLayout)
                startActivityForResult(intent, CodeDefinition.REQUEST_SEARCH_START);
            else
                startActivityForResult(intent, CodeDefinition.REQUEST_SEARCH_DESTINATION);
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