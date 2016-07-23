package com.yellowfuture.thanku.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yellowfuture.thanku.R;
import com.yellowfuture.thanku.network.controller.InquireController;
import com.yellowfuture.thanku.network.form.InquireForm;
import com.yellowfuture.thanku.utils.CodeDefinition;
import com.yellowfuture.thanku.utils.SessionUtils;
import com.yellowfuture.thanku.utils.Utils;
import com.yellowfuture.thanku.view.common.BaseActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zuby on 2016-07-15.
 */
public class InquireActivity extends BaseActivity {

    String mEmail;
    String mPhone;

    EditText mEmailEditText;
    EditText mPhoneEditText;
    EditText mContentEditText;
    CheckBox mAgreeCheckBox;
    @Override
    public void initView() {
        super.initView();
        mPhoneEditText = (EditText) findViewById(R.id.phoneEditText);
        mEmailEditText = (EditText) findViewById(R.id.emailEditText);
        mContentEditText = (EditText) findViewById(R.id.contentEditText);
        mAgreeCheckBox = (CheckBox) findViewById(R.id.agreeCheckBox);

        mPhoneEditText.setText(mPhone);
        mEmailEditText.setText(mEmail);
    }

    public void initActionBar() {
        Utils.getActionBar(this, getSupportActionBar(), R.layout.actionbar_default);
        getSupportActionBar().setElevation(0);
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.inquire));
        findViewById(R.id.back).setOnClickListener(this);
    }

    @Override
    public void init() {
        super.init();

        mEmail = SessionUtils.getString(this, CodeDefinition.USER_EMAIL,"");
        mPhone = SessionUtils.getString(this, CodeDefinition.USER_PHONE,"");

        initActionBar();
        initView();
        findViewById(R.id.checkLayout).setOnClickListener(this);
        findViewById(R.id.inquireSendButton).setOnClickListener(this);
    }
    public void sendInquire(){
        if(!checkValid()) return ;
        InquireForm inquireForm = new InquireForm();
        inquireForm.setContent(mContentEditText.getText().toString());
        inquireForm.setEmail(mEmailEditText.getText().toString());
        inquireForm.setPhone(mPhoneEditText.getText().toString());

        InquireController.getInstance(this).inquire(mAccessToken, inquireForm, new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code()==200){
                    Toast.makeText(InquireActivity.this, "정상적으로 접수되었습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(InquireActivity.this, "실패하였습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquire);
        init();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(v.getId() == R.id.checkLayout) {
            mAgreeCheckBox.setChecked(!mAgreeCheckBox.isChecked());
        }else if(v.getId() == R.id.inquireSendButton){
            sendInquire();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private boolean checkValid() {
        String phone = mPhoneEditText.getText().toString();
        String email = mEmailEditText.getText().toString();
        String content = mContentEditText.getText().toString();

        if (phone.length() <= 0) {
            Toast.makeText(this, "휴대폰 번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!Utils.validCheck(Utils.ValidType.PHONE, phone)) {
            Toast.makeText(this, "휴대폰 형식을 확인해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (email.length() <= 0) {
            Toast.makeText(this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!Utils.validCheck(Utils.ValidType.EMAIL, email)) {
            Toast.makeText(this, "이메일 형식을 확인해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(content.length()<10) {
            Toast.makeText(this, "내용을 10글자 이상 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;

        }
        if (!mAgreeCheckBox.isChecked()) {
            Toast.makeText(this, "약관에 동의해주세요.", Toast.LENGTH_SHORT).show();
            return false;

        }
        return true;
    }

}
