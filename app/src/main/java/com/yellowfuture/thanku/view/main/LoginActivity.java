package com.yellowfuture.thanku.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yellowfuture.thanku.R;
import com.yellowfuture.thanku.utils.Utils;
import com.yellowfuture.thanku.view.basic.BaseActivity;

/**
 * Created by zuby on 2016. 7. 13..
 */
public class LoginActivity extends BaseActivity {


    EditText mIdEditText, mPasswordEditText;



    @Override
    public void initView() {

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
        initView();
        initActionBar();


        findViewById(R.id.loginButton).setOnClickListener(this);
        findViewById(R.id.signUpButton).setOnClickListener(this);
        findViewById(R.id.findPasswordButton).setOnClickListener(this);
        findViewById(R.id.qnaButton).setOnClickListener(this);
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
            intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            setResult(RESULT_OK);
            finish();
        }else if (v.getId() == R.id.signUpButton) {
            intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivityForResult(intent,1000);
        }else if(v.getId() == R.id.findPasswordButton){
            intent = new Intent(LoginActivity.this, FindPasswordActivity.class);
            startActivity(intent);

        }else if(v.getId() == R.id.qnaButton) {
            intent = new Intent(LoginActivity.this, QnaActivity.class);
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
