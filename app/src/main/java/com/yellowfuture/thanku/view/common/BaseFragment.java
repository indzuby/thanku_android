package com.yellowfuture.thanku.view.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yellowfuture.thanku.utils.CodeDefinition;
import com.yellowfuture.thanku.utils.SessionUtils;

/**
 * Created by 주현 on 2016-02-05.
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener{
    protected View mView;
    protected String mAccessToken;

    public void init(){
        mAccessToken = SessionUtils.getString(getContext(), CodeDefinition.ACCESS_TOKEN,"");
    }
    public abstract void initView();
    @Override
    public void onClick(View v) {

    }
}
