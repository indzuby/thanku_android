package com.yellowfuture.thanku.view.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yellowfuture.thanku.R;
import com.yellowfuture.thanku.network.controller.UserController;
import com.yellowfuture.thanku.network.form.LoginForm;
import com.yellowfuture.thanku.network.form.LoginResponseForm;
import com.yellowfuture.thanku.utils.CodeDefinition;
import com.yellowfuture.thanku.utils.SessionUtils;
import com.yellowfuture.thanku.utils.Utils;
import com.yellowfuture.thanku.view.common.BaseActivity;
import com.yellowfuture.thanku.view.main.MainActivity;
import com.yellowfuture.thanku.view.main.InquireActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zuby on 2016. 7. 13..
 */
public class LoginActivity extends BaseActivity {


    EditText mIdEditText, mPasswordEditText;



    @Override
    public void initView() {
        super.initView();

        mIdEditText = (EditText) findViewById(R.id.idEditText);
        mPasswordEditText = (EditText) findViewById(R.id.passwordEditText);


    }

    public void initActionBar() {
        Utils.getActionBar(this, getSupportActionBar(), R.layout.actionbar_default);
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.login));
        findViewById(R.id.back).setOnClickListener(this);
    }

    @Override
    public void init() {
        super.init();
        initView();
        initActionBar();
        initBannerAdvertisement();


        findViewById(R.id.loginButton).setOnClickListener(this);
        findViewById(R.id.signUpButton).setOnClickListener(this);
        findViewById(R.id.findPasswordButton).setOnClickListener(this);
        findViewById(R.id.qnaButton).setOnClickListener(this);
    }

    private void login(){
        String username = mIdEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();
        if(!Utils.validCheck(Utils.ValidType.EMAIL,username) && !Utils.validCheck(Utils.ValidType.PHONE,username)) {
            Toast.makeText(this, "아이디 형식을 확인해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        UserController.getInstance(this).login(new LoginForm(username, password), new Callback<LoginResponseForm>() {
            @Override
            public void onResponse(Call<LoginResponseForm> call, Response<LoginResponseForm> response) {
                if(response.code() == 200) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    setResult(RESULT_OK);
                    SessionUtils.putString(LoginActivity.this,CodeDefinition.ACCESS_TOKEN,response.body().getAccessToken());
                    finish();
                }else if(response.code() == 401){
                    Toast.makeText(LoginActivity.this,"없는 아이디입니다.",Toast.LENGTH_SHORT).show();

                }else if(response.code() == 400){
                    Toast.makeText(LoginActivity.this,"비밀번호가 틀렸습니다.",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<LoginResponseForm> call, Throwable t) {
                Toast.makeText(LoginActivity.this,"로그인 에 실패하였습니다.",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent = null;
        if (v.getId() == R.id.loginButton) {
                login();
        }else if (v.getId() == R.id.signUpButton) {
            intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivityForResult(intent,1000);
        }else if(v.getId() == R.id.findPasswordButton){
            intent = new Intent(LoginActivity.this, FindPasswordActivity.class);
            startActivity(intent);

        }else if(v.getId() == R.id.qnaButton) {
            intent = new Intent(LoginActivity.this, InquireActivity.class);
            startActivity(intent);

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK)
            finish();

    }
}
