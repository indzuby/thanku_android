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
import com.yellowfuture.thanku.utils.SessionUtils;
import com.yellowfuture.thanku.utils.Utils;
import com.yellowfuture.thanku.view.common.BaseActivity;
import com.yellowfuture.thanku.view.search.AddressSearchActivity;

/**
 * Created by zuby on 2016. 7. 13..
 */
public class BuyActivity extends BaseActivity {


    EditText mOrderPhoneEditText;
    EditText mReceivePhoneEditText;
    TextView mReceiveAddressTextView;
    EditText mMemoEditText;
    ImageView mPhotoImageView;
    String mPhone;
    String mAddress;

    View mPhotoButton;


    @Override
    public void initView() {
        super.initView();
        mOrderPhoneEditText = (EditText) findViewById(R.id.orderPhoneEditText);
        mReceivePhoneEditText = (EditText) findViewById(R.id.receivePhoneEditText);
        mReceiveAddressTextView = (TextView) findViewById(R.id.addressTextView);
        mMemoEditText = (EditText) findViewById(R.id.memoEditText);
        mPhotoImageView = (ImageView) findViewById(R.id.photoImageView);
        mPhotoButton = findViewById(R.id.photoButton);

        mOrderPhoneEditText.setText(mPhone);
        mReceivePhoneEditText.setText(mPhone);
        mReceiveAddressTextView.setText(mAddress);
    }

    public void initActionBar() {
        Utils.getActionBar(this, getSupportActionBar(), R.layout.actionbar_default);
        getSupportActionBar().setElevation(0);
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.serviceBuy));
        findViewById(R.id.back).setOnClickListener(this);
    }

    @Override
    public void init() {
        super.init();
        mPhone = SessionUtils.getString(this, CodeDefinition.USER_PHONE,"");
        mAddress = SessionUtils.getString(this, CodeDefinition.USER_ADDRESS,"");

        initView();
        initActionBar();

        findViewById(R.id.addressLayout).setOnClickListener(this);
        mPhotoButton.setOnClickListener(this);
        findViewById(R.id.addCartButton).setOnClickListener(this);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_buy);
        init();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent = null;
        if(v.getId() == R.id.addressLayout) {
            intent = new Intent(BuyActivity.this, AddressSearchActivity.class);
            startActivityForResult(intent, CodeDefinition.REQUEST_SEARCH_START);
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
        if(resultCode != RESULT_OK)
            return;
        if(requestCode == CodeDefinition.REQUEST_SEARCH_START) {
            String address = data.getStringExtra(CodeDefinition.RESPONSE_SEARCH_RESULT);
            mReceiveAddressTextView.setText(address);
        }

    }
}
