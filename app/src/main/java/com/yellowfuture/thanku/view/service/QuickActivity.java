package com.yellowfuture.thanku.view.service;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yellowfuture.thanku.R;
import com.yellowfuture.thanku.model.OrderObject;
import com.yellowfuture.thanku.network.controller.OrderController;
import com.yellowfuture.thanku.network.form.OrderObjectForm;
import com.yellowfuture.thanku.utils.CodeDefinition;
import com.yellowfuture.thanku.utils.SessionUtils;
import com.yellowfuture.thanku.utils.Utils;
import com.yellowfuture.thanku.view.common.BaseActivity;
import com.yellowfuture.thanku.view.profile.ProfileActivity;
import com.yellowfuture.thanku.view.search.AddressSearchActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zuby on 2016. 7. 13..
 */
public class QuickActivity extends BaseActivity {


    EditText mOrderPhoneEditText;
    EditText mReceivePhoneEditText;
    TextView mStartAddressTextView, mDestinationAddressTextView;
    TextView mPriceTextView;
    EditText mCommentEditText;
    ImageView mPhotoImageView;
    String mPhone;

    View mPhotoButton;
    double startLat, startLon, destinationLat, destinationLon;

    @Override
    public void initView() {
        super.initView();
        mOrderPhoneEditText = (EditText) findViewById(R.id.orderPhoneEditText);
        mReceivePhoneEditText = (EditText) findViewById(R.id.receivePhoneEditText);
        mStartAddressTextView = (TextView) findViewById(R.id.startAddressTextView);
        mDestinationAddressTextView = (TextView) findViewById(R.id.destinationAddressTextView);
        mPriceTextView = (TextView) findViewById(R.id.priceTextView);
        mCommentEditText = (EditText) findViewById(R.id.commentEditText);
        mPhotoImageView = (ImageView) findViewById(R.id.photoImageView);
        mPhotoButton = findViewById(R.id.photoButton);
        mOrderPhoneEditText.setText(mPhone);

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
        super.init();
        mPhone = SessionUtils.getString(this, CodeDefinition.USER_PHONE, "");
        initView();
        initActionBar();

        findViewById(R.id.startAddressLayout).setOnClickListener(this);
        findViewById(R.id.destinationAddressLayout).setOnClickListener(this);
        mPhotoButton.setOnClickListener(this);
        findViewById(R.id.addCartButton).setOnClickListener(this);

    }

    public void addCart() {
        String orderPhone = mOrderPhoneEditText.getText().toString();
        String receivePhone = mReceivePhoneEditText.getText().toString();
        String startAddress = mStartAddressTextView.getText().toString();
        String destinationAddress = mDestinationAddressTextView.getText().toString();
        String comment = mCommentEditText.getText().toString();
        if (orderPhone.length() <= 0 || receivePhone.length() <= 0 || startAddress.length() <= 0 || destinationAddress.length() <= 0) {
            Toast.makeText(this, "연락처와 주소를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        OrderObjectForm form = new OrderObjectForm();
        form.setType(OrderObject.OrderType.QUICK);
        form.setOrderTel(orderPhone);
        form.setReceiverTel(receivePhone);
        form.setStartAddr(startAddress);
        form.setEndAddr(destinationAddress);
        form.setComment(comment);
        form.setStartLat(startLat);
        form.setStartLon(startLon);
        form.setEndLat(destinationLat);
        form.setEndLon(destinationLon);
        form.setAddPrice(Utils.getDistancePriceFromAToB(startLat, startLon, destinationLat, destinationLon));

        OrderController.getInstance(this).addOrder(mAccessToken, form, new Callback<OrderObject>() {
            @Override
            public void onResponse(Call<OrderObject> call, Response<OrderObject> response) {
                if (response.code() == 200) {
                    Toast.makeText(getBaseContext(), "장바구니에 추가되었습니다.", Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder alertDlg = new AlertDialog.Builder(QuickActivity.this);

                    alertDlg.setTitle("장바구니를 확인하시겠습니까?");
                    alertDlg.setPositiveButton("예", new DialogInterface.OnClickListener() { // 확인 버튼
                        @Override
                        public void onClick(DialogInterface dialog, int whichButton) {
                            Intent intent = new Intent(QuickActivity.this, ProfileActivity.class);
                            intent.putExtra(CodeDefinition.PROFILE_START_PARAM, CodeDefinition.PROFILE_CART_CODE);
                            startActivityForResult(intent, CodeDefinition.REQUEST_PROFILE_CODE);
                            finish();
                        }
                    });
                    alertDlg.setNegativeButton("아니오", new DialogInterface.OnClickListener() { // 취소 버튼
                        @Override
                        public void onClick(DialogInterface dialog, int whichButton) {

                            dialog.cancel();
                            finish();
                        }
                    });
                    AlertDialog alert = alertDlg.create();
                    alert.show();
                }
            }

            @Override
            public void onFailure(Call<OrderObject> call, Throwable t) {

            }
        });
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
        if (v.getId() == R.id.startAddressLayout || v.getId() == R.id.destinationAddressLayout) {
            intent = new Intent(QuickActivity.this, AddressSearchActivity.class);
            if (v.getId() == R.id.startAddressLayout)
                startActivityForResult(intent, CodeDefinition.REQUEST_SEARCH_START);
            else
                startActivityForResult(intent, CodeDefinition.REQUEST_SEARCH_DESTINATION);
        } else if (v.getId() == R.id.photoButton) {
            Toast.makeText(this, "사진 첨부", Toast.LENGTH_SHORT).show();

        } else if (v.getId() == R.id.addCartButton) {
            addCart();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;
        if (requestCode == CodeDefinition.REQUEST_SEARCH_START) {
            String address = data.getStringExtra(CodeDefinition.RESPONSE_SEARCH_RESULT);
            startLat = data.getDoubleExtra(CodeDefinition.RESPONSE_SEARCH_LAT, 0);
            startLon = data.getDoubleExtra(CodeDefinition.RESPONSE_SEARCH_LON, 0);

            mStartAddressTextView.setText(address);
            if (startLat > 0 && destinationLon > 0)
                mPriceTextView.setText(Utils.getDistancePriceToString(Utils.getDistancePriceFromAToB(startLat,startLon,destinationLat,destinationLon)));
        } else if (requestCode == CodeDefinition.REQUEST_SEARCH_DESTINATION) {
            String address = data.getStringExtra(CodeDefinition.RESPONSE_SEARCH_RESULT);
            destinationLat = data.getDoubleExtra(CodeDefinition.RESPONSE_SEARCH_LAT, 0);
            destinationLon = data.getDoubleExtra(CodeDefinition.RESPONSE_SEARCH_LON, 0);
            mDestinationAddressTextView.setText(address);
            if (startLat > 0 && destinationLon > 0)
                mPriceTextView.setText(Utils.getDistancePriceToString(Utils.getDistancePriceFromAToB(startLat,startLon,destinationLat,destinationLon)));

        }

    }
}
