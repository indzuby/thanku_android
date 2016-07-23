package com.yellowfuture.thanku.view.common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.tsengvn.typekit.TypekitContextWrapper;
import com.yellowfuture.thanku.R;
import com.yellowfuture.thanku.utils.CodeDefinition;
import com.yellowfuture.thanku.utils.SessionUtils;
import com.yellowfuture.thanku.utils.Utils;

/**
 * Created by user on 2016-01-07.
 */
public class BaseActivity extends AppCompatActivity implements View.OnClickListener{

    private ProgressDialog progressDialog;
    protected String mAccessToken;
    public void initView(){
        endProgress();
    }
    public void init(){
        mAccessToken = SessionUtils.getString(this, CodeDefinition.ACCESS_TOKEN,"");
        progressDialog = new ProgressDialog(this);
        startProgress();

    }
    public void startProgress(){

        if(progressDialog!=null) progressDialog.show();
    }
    public void endProgress(){
        if(progressDialog !=null) progressDialog.dismiss();
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()== R.id.back) {
            Utils.hideKeyboard(this,findViewById(R.id.back));
            finish();
            return;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode!=RESULT_OK) return ;
    }

}
