package com.yellowfuture.thanku.view.account;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yellowfuture.thanku.R;
import com.yellowfuture.thanku.view.common.BaseFragment;

/**
 * Created by zuby on 2016. 6. 15..
 */
public class AccountFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.element_account,container,false);
        return mView;
    }
}
