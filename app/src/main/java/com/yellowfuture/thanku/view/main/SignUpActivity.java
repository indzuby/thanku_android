package com.yellowfuture.thanku.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yellowfuture.thanku.R;
import com.yellowfuture.thanku.utils.Utils;
import com.yellowfuture.thanku.view.basic.BaseActivity;

/**
 * Created by zuby on 2016. 7. 13..
 */
public class SignUpActivity extends BaseActivity {


    @Override
    public void initView() {



    }
    public void initActionBar(){
        Utils.getActionBar(this,getSupportActionBar(),R.layout.actionbar_default);
        TextView title = (TextView)  findViewById(R.id.title);
        title.setText(getString(R.string.signUp));
        findViewById(R.id.back).setOnClickListener(this);
    }

    @Override
    public void init() {
        initView();
        initActionBar();

        findViewById(R.id.signUpButton).setOnClickListener(this);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        init();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(v.getId() == R.id.signUpButton) {
            Toast.makeText(this,"회원가입 완료.",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
            setResult(RESULT_OK);
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
        }
    }
}
