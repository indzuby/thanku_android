package com.yellowfuture.thanku.view.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yellowfuture.thanku.R;
import com.yellowfuture.thanku.model.User;
import com.yellowfuture.thanku.network.controller.UserController;
import com.yellowfuture.thanku.network.form.SignUpForm;
import com.yellowfuture.thanku.utils.Utils;
import com.yellowfuture.thanku.view.common.BaseActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zuby on 2016. 7. 13..
 */
public class SignUpActivity extends BaseActivity {

    EditText mPhoneEditText;
    EditText mNameEditText;
    EditText mPasswordEditText;
    EditText mPasswordReEditText;
    EditText mEmailEditText;
    CheckBox mCheckBox;

    @Override
    public void initView() {
        super.initView();
        mPhoneEditText = (EditText) findViewById(R.id.phoneEditText);
        mNameEditText = (EditText) findViewById(R.id.nameEditText);
        mPasswordEditText = (EditText) findViewById(R.id.passwordEditText);
        mPasswordReEditText = (EditText) findViewById(R.id.passwordReEditText);
        mEmailEditText = (EditText) findViewById(R.id.emailEditText);
        mCheckBox = (CheckBox) findViewById(R.id.checkBox);

    }

    public void initActionBar() {
        Utils.getActionBar(this, getSupportActionBar(), R.layout.actionbar_default);
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.signUp));
        findViewById(R.id.back).setOnClickListener(this);
    }

    @Override
    public void init() {
        super.init();
        initView();
        initActionBar();
        initBannerAdvertisement();

        findViewById(R.id.signUpButton).setOnClickListener(this);
        findViewById(R.id.checkLayout).setOnClickListener(this);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        init();
    }

    private void signUpByEmail() {
        String phone = mPhoneEditText.getText().toString();
        String name = mNameEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();
        String email = mEmailEditText.getText().toString();
        SignUpForm user = new SignUpForm();
        user.setEmail(email);
        user.setPhone(phone);
        user.setPassword(password);
        user.setName(name);
        UserController.getInstance(this).signUpByEmail(user, new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Toast.makeText(SignUpActivity.this, "회원가입 완료.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.signUpButton) {

            if (checkValid()) {
                signUpByEmail();
            }
        } else if (v.getId() == R.id.checkLayout) {
            if (mCheckBox.isChecked()) mCheckBox.setChecked(false);
            else mCheckBox.setChecked(true);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
        }
    }

    private boolean checkValid() {
        String phone = mPhoneEditText.getText().toString();
        String name = mNameEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();
        String passwordRe = mPasswordReEditText.getText().toString();
        String email = mEmailEditText.getText().toString();

        if (phone.length() <= 0) {
            Toast.makeText(this, "휴대폰 번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!Utils.validCheck(Utils.ValidType.PHONE, phone)) {
            Toast.makeText(this, "휴대폰 형식을 확인해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (name.length() <= 0) {
            Toast.makeText(this, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!Utils.validCheck(Utils.ValidType.NAME, name)) {
            Toast.makeText(this, "이름 형식을 확인해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.length() < 6) {
            Toast.makeText(this, "비밀번호는 6글자 이상 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!password.equals(passwordRe)) {
            Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (email.length() <= 0) {
            Toast.makeText(this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!Utils.validCheck(Utils.ValidType.EMAIL, email)) {
            Toast.makeText(this, "이메일 형식을 확인해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!mCheckBox.isChecked()) {
            Toast.makeText(this, "약관에 동의해주세요.", Toast.LENGTH_SHORT).show();
            return false;

        }
        return true;
    }


}
