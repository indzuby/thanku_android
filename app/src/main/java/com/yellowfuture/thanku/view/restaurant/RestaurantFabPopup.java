package com.yellowfuture.thanku.view.restaurant;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.yellowfuture.thanku.R;
import com.yellowfuture.thanku.utils.CodeDefinition;
import com.yellowfuture.thanku.view.main.MainActivity;
import com.yellowfuture.thanku.view.profile.ProfileActivity;

/**
 * Created by zuby on 2016. 7. 18..
 */
public class RestaurantFabPopup extends Dialog implements View.OnClickListener{
    public RestaurantFabPopup(Context context) {
        super(context,android.R.style.Theme_DeviceDefault_Light_NoActionBar);
        init();
    }
    public void init(){
        setContentView(R.layout.element_popup_fab);
        getWindow().setBackgroundDrawable(ContextCompat.getDrawable(getContext(),R.color.black50));
        findViewById(R.id.fabButton).setOnClickListener(this);
        findViewById(R.id.layout).setOnClickListener(this);
        findViewById(R.id.cartButton).setOnClickListener(this);
        setCanceledOnTouchOutside(true);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        if(v.getId() == R.id.cartButton) {
            intent = new Intent(getContext(), ProfileActivity.class);
            intent.putExtra(CodeDefinition.PROFILE_START_PARAM,2);
            getContext().startActivity(intent);
        }
        dismiss();
    }
}
