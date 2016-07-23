package com.yellowfuture.thanku.view.common;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.yellowfuture.thanku.R;
import com.yellowfuture.thanku.utils.Utils;

/**
 * Created by zuby on 2016. 7. 23..
 */
public class ProgressDialog extends Dialog {
    public ProgressDialog(Context context) {
        super(context,android.R.style.Theme_DeviceDefault_Light_NoActionBar);
        init();
    }
    public void init(){
        getWindow().setBackgroundDrawable(ContextCompat.getDrawable(getContext(),R.color.transparent));
        Utils.setStatusColor(getContext(),getWindow(),R.color.black);
        setContentView(R.layout.element_popup_loading);
        View loading = findViewById(R.id.loading);
        Animation rotate = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
        loading.startAnimation(rotate);
    }

}
