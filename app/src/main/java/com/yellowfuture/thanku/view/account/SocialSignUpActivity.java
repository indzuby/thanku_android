package com.yellowfuture.thanku.view.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.helper.log.Logger;
import com.yellowfuture.thanku.R;
import com.yellowfuture.thanku.model.User;
import com.yellowfuture.thanku.network.controller.UserController;
import com.yellowfuture.thanku.network.form.LoginForm;
import com.yellowfuture.thanku.network.form.LoginResponseForm;
import com.yellowfuture.thanku.network.form.SignUpForm;
import com.yellowfuture.thanku.utils.CodeDefinition;
import com.yellowfuture.thanku.utils.SessionUtils;
import com.yellowfuture.thanku.utils.Utils;
import com.yellowfuture.thanku.view.common.BaseActivity;
import com.yellowfuture.thanku.view.main.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zuby on 2016. 7. 13..
 */
public class SocialSignUpActivity extends BaseActivity {

    EditText mPhoneEditText;
    EditText mNameEditText;
    EditText mEmailEditText;
    CheckBox mCheckBox;
    boolean isKakao;

    String socialAccessToken;

    @Override
    public void initView() {
        super.initView();
        mPhoneEditText = (EditText) findViewById(R.id.phoneEditText);
        mNameEditText = (EditText) findViewById(R.id.nameEditText);
        mEmailEditText = (EditText) findViewById(R.id.emailEditText);
        mCheckBox = (CheckBox) findViewById(R.id.checkBox);


    }

    public void initActionBar() {
        Utils.getActionBar(this, getSupportActionBar(), R.layout.actionbar_default);
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.addProfile));
        findViewById(R.id.back).setOnClickListener(this);
    }

    @Override
    public void init() {
        super.init();
        socialAccessToken = getIntent().getStringExtra(CodeDefinition.SOCIAL_TOKEN);
        isKakao = getIntent().getBooleanExtra(CodeDefinition.IS_KAKAO, true);
        initView();
        initActionBar();
        initBannerAdvertisement();

        findViewById(R.id.signUpButton).setOnClickListener(this);
        findViewById(R.id.checkLayout).setOnClickListener(this);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_social);
        init();
    }

    private void requestMe() {
        UserManagement.requestMe(new MeResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
            }

            @Override
            public void onSuccess(UserProfile userProfile) {
                Logger.d("UserProfile : " + userProfile);
                signUpBySocial(userProfile.getNickname(), userProfile.getProfileImagePath());
            }

            @Override
            public void onNotSignedUp() {
            }
        });
    }

    private void signUpBySocial(String nickname, String profilePath) {
        String phone = mPhoneEditText.getText().toString();
        String name = mNameEditText.getText().toString();
        String email = mEmailEditText.getText().toString();
        SignUpForm user = new SignUpForm();
        if (isKakao)
            user.setSignUpType(User.SignUpType.KAKAO);
        else
            user.setSignUpType(User.SignUpType.FACEBOOK);
        user.setEmail(email);
        user.setPhone(phone);
        user.setSocialAccessToken(socialAccessToken);
        user.setName(name);
        user.setPassword("");
        user.setNickname(nickname);
        user.setProfilePath(profilePath);
        UserController.getInstance(this).signUpBySocial(user, new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                login(socialAccessToken, socialAccessToken);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void login(final String username, String password) {
        UserController.getInstance(this).login(new LoginForm(username, password), new Callback<LoginResponseForm>() {
            @Override
            public void onResponse(Call<LoginResponseForm> call, Response<LoginResponseForm> response) {
                if (response.code() == 200) {
                    Intent intent = new Intent(SocialSignUpActivity.this, MainActivity.class);
                    startActivity(intent);
                    setResult(RESULT_OK);
                    SessionUtils.putString(SocialSignUpActivity.this, CodeDefinition.ACCESS_TOKEN, response.body().getAccessToken());
                    finish();
                }
            }

            @Override
            public void onFailure(Call<LoginResponseForm> call, Throwable t) {
                Toast.makeText(SocialSignUpActivity.this, "로그인 에 실패하였습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.signUpButton) {

            if (checkValid()) {
                if (isKakao)
                    requestMe();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



}
