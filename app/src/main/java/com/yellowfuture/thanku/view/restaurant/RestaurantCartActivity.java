package com.yellowfuture.thanku.view.restaurant;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yellowfuture.thanku.R;
import com.yellowfuture.thanku.model.OrderObject;
import com.yellowfuture.thanku.model.Restaurant;
import com.yellowfuture.thanku.model.RestaurantOrderMenu;
import com.yellowfuture.thanku.network.controller.OrderController;
import com.yellowfuture.thanku.network.form.OrderObjectForm;
import com.yellowfuture.thanku.utils.CodeDefinition;
import com.yellowfuture.thanku.utils.SessionUtils;
import com.yellowfuture.thanku.utils.Utils;
import com.yellowfuture.thanku.view.common.BaseActivity;
import com.yellowfuture.thanku.view.profile.ProfileActivity;
import com.yellowfuture.thanku.view.search.AddressSearchActivity;
import com.yellowfuture.thanku.view.service.ErrandActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zuby on 2016-07-29.
 */
public class RestaurantCartActivity extends BaseActivity {

    Restaurant mRestaurant;
    List<RestaurantOrderMenu> mOrderMenuList;

    EditText mOrderPhoneEditText;
    TextView mReceiveAddressTextView;
    EditText mCommentEditText;
    TextView mPriceTextView;
    LinearLayout mMenuLayout;
    String mPhone;
    int price;
    double lat,lon;
    @Override
    public void initView() {
        super.initView();
        mOrderPhoneEditText = (EditText) findViewById(R.id.orderPhoneEditText);
        mReceiveAddressTextView = (TextView) findViewById(R.id.addressTextView);
        mPriceTextView = (TextView) findViewById(R.id.priceTextView);
        mCommentEditText = (EditText) findViewById(R.id.commentEditText);
        mMenuLayout = (LinearLayout) findViewById(R.id.menuLayout);

        mOrderPhoneEditText.setText(mPhone);
        mPriceTextView.setText(Utils.getPriceToString(price));

        mMenuLayout.removeAllViews();

        for(RestaurantOrderMenu orderMenu : mOrderMenuList) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_order_menu_detail,null,false);
            view.findViewById(R.id.thumbnail).setVisibility(View.GONE);
            TextView nameView = (TextView) view.findViewById(R.id.nameTextView);
            TextView priceView = (TextView) view.findViewById(R.id.priceTextView);
            TextView countView = (TextView) view.findViewById(R.id.menuCountView);
            countView.setVisibility(View.VISIBLE);
            nameView.setText(orderMenu.getRestaurantMenu().getName());
            priceView.setText(Utils.getPriceToString(orderMenu.getPrice()));
            countView.setText(orderMenu.getCount()+"개");
            mMenuLayout.addView(view);
        }

    }

    public void addCart(){
        String orderPhone = mOrderPhoneEditText.getText().toString();
        String address = mReceiveAddressTextView.getText().toString();
        String comment = mCommentEditText.getText().toString();
        if(orderPhone.length()<=0 || address.length()<=0) {
            Toast.makeText(this, "연락처와 주소를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return ;
        }
        OrderObjectForm form = new OrderObjectForm();
        form.setType(OrderObject.OrderType.RESTAURANT);
        form.setRestaurantId(mRestaurant.getId());
        form.setOrderTel(orderPhone);
        form.setAddress(address);
        form.setComment(comment);
        form.setLat(lat);
        form.setLon(lon);
        form.setMenuList(mOrderMenuList);
        form.setPrice(price);
        OrderController.getInstance(this).addOrder(mAccessToken, form, new Callback<OrderObject>() {
            @Override
            public void onResponse(Call<OrderObject> call, Response<OrderObject> response) {
                if (response.code() == 200) {
                    Toast.makeText(getBaseContext(), "장바구니에 추가되었습니다.", Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder alertDlg = new AlertDialog.Builder(RestaurantCartActivity.this);

                    alertDlg.setTitle("장바구니를 확인하시겠습니까?");
                    alertDlg.setPositiveButton("예", new DialogInterface.OnClickListener() { // 확인 버튼
                        @Override
                        public void onClick(DialogInterface dialog, int whichButton) {
                            Intent intent = new Intent(RestaurantCartActivity.this, ProfileActivity.class);
                            intent.putExtra(CodeDefinition.PROFILE_START_PARAM, CodeDefinition.PROFILE_CART_CODE);
                            startActivityForResult(intent, CodeDefinition.REQUEST_PROFILE_CODE);
                            setResult(RESULT_OK);
                            finish();
                        }
                    });
                    alertDlg.setNegativeButton("아니오", new DialogInterface.OnClickListener() { // 취소 버튼
                        @Override
                        public void onClick(DialogInterface dialog, int whichButton) {

                            dialog.cancel();
                            finish();
                        }
                    });
                    AlertDialog alert = alertDlg.create();
                    alert.show();
                }
            }

            @Override
            public void onFailure(Call<OrderObject> call, Throwable t) {

            }
        });
    }
    public void initActionBar() {
        Utils.getActionBar(this, getSupportActionBar(), R.layout.actionbar_default);
        getSupportActionBar().setElevation(0);
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(mRestaurant.getName());
        findViewById(R.id.back).setOnClickListener(this);
    }
    @Override
    public void init() {
        super.init();
        mPhone = SessionUtils.getString(this, CodeDefinition.USER_PHONE, "");
        price = getIntent().getIntExtra("price", 0);
        mRestaurant = Utils.getRestaurant();
        mOrderMenuList = Utils.getOrderMenuList();
        initActionBar();
        initView();

        findViewById(R.id.addressLayout).setOnClickListener(this);
        findViewById(R.id.addCartButton).setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_cart);
        init();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent = null;
        if(v.getId() == R.id.addressLayout) {
            intent = new Intent(RestaurantCartActivity.this, AddressSearchActivity.class);
            startActivityForResult(intent, CodeDefinition.REQUEST_SEARCH_START);
        }else if(v.getId() == R.id.addCartButton) {
            addCart();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_OK)
            return;
        if(requestCode == CodeDefinition.REQUEST_SEARCH_START) {
            String address = data.getStringExtra(CodeDefinition.RESPONSE_SEARCH_RESULT);
            lat = data.getDoubleExtra(CodeDefinition.RESPONSE_SEARCH_LAT,0);
            lon = data.getDoubleExtra(CodeDefinition.RESPONSE_SEARCH_LON,0);
            mReceiveAddressTextView.setText(address);
        }

    }
}
