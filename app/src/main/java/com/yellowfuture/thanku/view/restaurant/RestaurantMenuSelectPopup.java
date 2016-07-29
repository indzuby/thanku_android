package com.yellowfuture.thanku.view.restaurant;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yellowfuture.thanku.R;
import com.yellowfuture.thanku.model.RestaurantMenu;
import com.yellowfuture.thanku.model.RestaurantOrderMenu;
import com.yellowfuture.thanku.utils.Utils;

/**
 * Created by zuby on 2016-07-29.
 */
public class RestaurantMenuSelectPopup extends Dialog implements View.OnClickListener{
    RestaurantMenu mMenu;
    RestaurantOrderMenu mOrderMenu;
    TextView priceView;
    Activity activity;
    public RestaurantMenuSelectPopup(Activity context,RestaurantMenu menu) {
        super(context,android.R.style.Theme_DeviceDefault_Light_NoActionBar);
        this.activity = context;
        mMenu = menu;
        mOrderMenu = new RestaurantOrderMenu();
        mOrderMenu.setRestaurantMenu(menu);
        init();
    }
    public void init(){
        setContentView(R.layout.element_popup_menu_select);
        getWindow().setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.color.black50));

        TextView nameView = (TextView) findViewById(R.id.nameTextView);
        priceView = (TextView) findViewById(R.id.priceTextView);
        EditText countEditText = (EditText) findViewById(R.id.countEditText);

        nameView.setText(mMenu.getName());
        priceView.setText(Utils.getPriceToString(mMenu.getPrice()));
        countEditText.setText("1");

        mOrderMenu.setCount(1);
        mOrderMenu.setPrice(mMenu.getPrice());
        countEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count>0) {
                    int cnt = Integer.parseInt(s.toString());
                    priceView.setText(Utils.getPriceToString(cnt * mMenu.getPrice()));
                    mOrderMenu.setCount(cnt);
                    mOrderMenu.setPrice(cnt * mMenu.getPrice());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        findViewById(R.id.closeButton).setOnClickListener(this);
        findViewById(R.id.cancelButton).setOnClickListener(this);
        findViewById(R.id.addButton).setOnClickListener(this);
        setCanceledOnTouchOutside(true);
    }

    @Override
    public void onClick(View v) {;
        if(v.getId() == R.id.addButton) {
            Toast.makeText(getContext(),"추가되었습니다.",Toast.LENGTH_SHORT).show();
            ((RestaurantDetailActivity) activity).addMenuInCart(mOrderMenu);
        }
        dismiss();
    }
}
