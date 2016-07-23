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
public class ErrandActivity extends BaseActivity {


    EditText mOrderPhoneEditText;
    TextView mReceiveAddressTextView;
    EditText mCommentEditText;
    ImageView mPhotoImageView;
    String mPhone;


    double lat,lon;
    View mPhotoButton;


    @Override
    public void initView() {
        super.initView();
        mOrderPhoneEditText = (EditText) findViewById(R.id.orderPhoneEditText);
        mReceiveAddressTextView = (TextView) findViewById(R.id.addressTextView);
        mCommentEditText = (EditText) findViewById(R.id.commentEditText);
        mPhotoImageView = (ImageView) findViewById(R.id.photoImageView);
        mPhotoButton = findViewById(R.id.photoButton);

        mOrderPhoneEditText.setText(mPhone);
    }

    public void initActionBar() {
        Utils.getActionBar(this, getSupportActionBar(), R.layout.actionbar_default);
        getSupportActionBar().setElevation(0);
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.serviceErrand));
        findViewById(R.id.back).setOnClickListener(this);
    }

    @Override
    public void init() {
        super.init();
        mPhone = SessionUtils.getString(this, CodeDefinition.USER_PHONE,"");
        initView();
        initActionBar();

        findViewById(R.id.addressLayout).setOnClickListener(this);
        mPhotoButton.setOnClickListener(this);
        findViewById(R.id.addCartButton).setOnClickListener(this);

    }

    public void addCart(){
        String orderPhone = mOrderPhoneEditText.getText().toString();
        String address = mReceiveAddressTextView.getText().toString();
        String comment = mCommentEditText.getText().toString();
        if(orderPhone.length()<=0 || address.length()<=0) {
            Toast.makeText(this,"연락처와 주소를 입력해주세요.",Toast.LENGTH_SHORT).show();
            return ;
        }
        OrderObjectForm form = new OrderObjectForm();
        form.setType(OrderObject.OrderType.ERRAND);
        form.setOrderTel(orderPhone);
        form.setAddress(address);
        form.setComment(comment);
        form.setLat(lat);
        form.setLon(lon);
        OrderController.getInstance(this).addOrder(mAccessToken, form, new Callback<OrderObject>() {
            @Override
            public void onResponse(Call<OrderObject> call, Response<OrderObject> response) {
                if(response.code()==200){
                    Toast.makeText(getBaseContext(),"장바구니에 추가되었습니다.",Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder alertDlg = new AlertDialog.Builder(ErrandActivity.this);

                    alertDlg.setTitle("장바구니를 확인하시겠습니까?");
                    alertDlg.setPositiveButton("예", new DialogInterface.OnClickListener() { // 확인 버튼
                        @Override
                        public void onClick(DialogInterface dialog, int whichButton) {
                            Intent intent = new Intent(ErrandActivity.this, ProfileActivity.class);
                            intent.putExtra(CodeDefinition.PROFILE_START_PARAM, CodeDefinition.PROFILE_CART_CODE);
                            startActivityForResult(intent,CodeDefinition.REQUEST_PROFILE_CODE);
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
        setContentView(R.layout.activity_service_errand);
        init();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent = null;
        if(v.getId() == R.id.addressLayout) {
            intent = new Intent(ErrandActivity.this, AddressSearchActivity.class);
            startActivityForResult(intent, CodeDefinition.REQUEST_SEARCH_START);

        }else if(v.getId() == R.id.photoButton) {
            Toast.makeText(this,"사진 첨부",Toast.LENGTH_SHORT).show();

        }else if(v.getId() == R.id.addCartButton) {
            addCart();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_OK)
            return;
        if(requestCode == CodeDefinition.REQUEST_SEARCH_START) {
            String address = data.getStringExtra(CodeDefinition.RESPONSE_SEARCH_RESULT);
            lat = data.getDoubleExtra(CodeDefinition.RESPONSE_SEARCH_LAT,0);
            lon = data.getDoubleExtra(CodeDefinition.RESPONSE_SEARCH_LON,0);
            mReceiveAddressTextView.setText(address);
        }

    }
}
