package com.yellowfuture.thanku.view.common;

import android.content.Context;
import android.util.AttributeSet;

import com.kakao.usermgmt.LoginButton;
import com.yellowfuture.thanku.R;

/**
 * Created by rlawn on 2016-07-30.
 */
public class KaKaoLoginButton extends LoginButton {
    public KaKaoLoginButton(Context context) {
        super(context);
    }

    public KaKaoLoginButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public KaKaoLoginButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        inflate(getContext(), R.layout.kakao_login_button, this);
    }
}
