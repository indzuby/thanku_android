package com.yellowfuture.thanku.view.main;

import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.skp.Tmap.TMapData;
import com.skp.Tmap.TMapPOIItem;
import com.yellowfuture.thanku.R;
import com.yellowfuture.thanku.control.GpsControl;
import com.yellowfuture.thanku.model.User;
import com.yellowfuture.thanku.network.RestApi;
import com.yellowfuture.thanku.network.controller.UserController;
import com.yellowfuture.thanku.utils.CodeDefinition;
import com.yellowfuture.thanku.utils.SessionUtils;
import com.yellowfuture.thanku.utils.Utils;
import com.yellowfuture.thanku.view.profile.ProfileActivity;
import com.yellowfuture.thanku.view.search.AddressSearchActivity;
import com.yellowfuture.thanku.view.service.BuyActivity;
import com.yellowfuture.thanku.view.common.BaseActivity;
import com.yellowfuture.thanku.view.service.ErrandActivity;
import com.yellowfuture.thanku.view.service.QuickActivity;
import com.yellowfuture.thanku.view.restaurant.RestaurantActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

    DrawerLayout mDrawerLayout;
    boolean active = true;
    TMapData mMapData;
    LocationManager mLocationManager;

    @Override
    public void initView() {
        super.initView();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

    }

    public void initActionBar() {
        findViewById(R.id.menu).setOnClickListener(this);

    }

    public void initData() {

        String token = SessionUtils.getString(this,CodeDefinition.REG_ID,"");
        if(token.isEmpty()) return;
        UserController.getInstance(this).myInfo(mAccessToken,token, new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    User user = response.body();
                    ImageView profileView = (ImageView) findViewById(R.id.profileImage);
                    TextView nameView = (TextView) findViewById(R.id.nameTextView);
                    TextView pointView = (TextView) findViewById(R.id.pointTextView);
                    Glide.with(MainActivity.this).load(user.getProfilePath()).into(profileView);
                    nameView.setText(user.getName());
                    if (user.getNickname() != null && user.getNickname().length() > 0)
                        nameView.setText(user.getNickname());
                    pointView.setText(user.getPoint() + "p");
                    SessionUtils.putString(getBaseContext(), CodeDefinition.USER_PHONE, user.getPhone());
                    SessionUtils.putString(getBaseContext(), CodeDefinition.USER_EMAIL, user.getEmail());
                    SessionUtils.putString(getBaseContext(), CodeDefinition.USER_ADDRESS, user.getAddress());

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    public void initNavigationLayout() {
        findViewById(R.id.profileLayout).setOnClickListener(this);
        findViewById(R.id.notificationLayout).setOnClickListener(this);
        findViewById(R.id.cartLayout).setOnClickListener(this);
        findViewById(R.id.orderlayout).setOnClickListener(this);
        findViewById(R.id.announcementLayout).setOnClickListener(this);
        findViewById(R.id.qnaLayout).setOnClickListener(this);
        findViewById(R.id.advertisementLayout).setOnClickListener(this);
        findViewById(R.id.settingsButton).setOnClickListener(this);
        initData();
    }

    public void initMyLocationView() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Location location = GpsControl.getInstance(MainActivity.this).getLocation();
                    String address = mMapData.convertGpsToAddress(location.getLatitude(), location.getLongitude());
                    ArrayList<TMapPOIItem> list = mMapData.findAllPOI(address, 1);
                    if (list.size() > 0 && active) {
                        final TMapPOIItem item = list.get(0);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                TextView nowAddressTextView = (TextView) findViewById(R.id.nowAddressTextView);
                                nowAddressTextView.setText(Utils.parsePOIAddressOld(item));
                            }
                        });
                    }
                    ;
                } catch (Exception e) {

                }
            }
        }).start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    public void init() {
        super.init();
        initActionBar();
        initView();
        initNavigationLayout();
        initBannerAdvertisement();
        mMapData = new TMapData();
        initMyLocationView();
        findViewById(R.id.serviceRestaurantLayout).setOnClickListener(this);
        findViewById(R.id.serviceBuyLayout).setOnClickListener(this);
        findViewById(R.id.serviceErrandLayout).setOnClickListener(this);
        findViewById(R.id.serviceQuickLayout).setOnClickListener(this);
        findViewById(R.id.addressLayout).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent;
        if (v.getId() == R.id.menu) {
            mDrawerLayout.openDrawer(GravityCompat.START);
        } else if (v.getId() == R.id.serviceRestaurantLayout) {
            intent = new Intent(MainActivity.this, RestaurantActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.serviceBuyLayout) {
            intent = new Intent(MainActivity.this, BuyActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.serviceErrandLayout) {
            intent = new Intent(MainActivity.this, ErrandActivity.class);
            startActivity(intent);

        } else if (v.getId() == R.id.serviceQuickLayout) {
            intent = new Intent(MainActivity.this, QuickActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.profileLayout || v.getId() == R.id.settingsButton) {
            intent = new Intent(MainActivity.this, ProfileActivity.class);
            intent.putExtra(CodeDefinition.PROFILE_START_PARAM, CodeDefinition.PROFILE_EDIT_CODE);
            startActivityForResult(intent, CodeDefinition.REQUEST_PROFILE_CODE);
            mDrawerLayout.closeDrawers();
        } else if (v.getId() == R.id.orderlayout) {
            intent = new Intent(MainActivity.this, ProfileActivity.class);
            intent.putExtra(CodeDefinition.PROFILE_START_PARAM, CodeDefinition.PROFILE_ORDER_CODE);
            startActivityForResult(intent, CodeDefinition.REQUEST_PROFILE_CODE);
            mDrawerLayout.closeDrawers();
        } else if (v.getId() == R.id.cartLayout) {
            intent = new Intent(MainActivity.this, ProfileActivity.class);
            intent.putExtra(CodeDefinition.PROFILE_START_PARAM, CodeDefinition.PROFILE_CART_CODE);
            startActivityForResult(intent, CodeDefinition.REQUEST_PROFILE_CODE);
            mDrawerLayout.closeDrawers();
        } else if (v.getId() == R.id.advertisementLayout || v.getId() == R.id.qnaLayout) {
            intent = new Intent(MainActivity.this, InquireActivity.class);
            startActivity(intent);
            mDrawerLayout.closeDrawers();

        } else if (v.getId() == R.id.addressLayout) {
            intent = new Intent(MainActivity.this, AddressSearchActivity.class);
            startActivityForResult(intent, CodeDefinition.REQUEST_SEARCH_START);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == CodeDefinition.LOGOUT)
            finish();
        if (requestCode == CodeDefinition.REQUEST_SEARCH_START && resultCode == RESULT_OK) {
            double lat = data.getDoubleExtra(CodeDefinition.RESPONSE_SEARCH_LAT,0);
            double lon = data.getDoubleExtra(CodeDefinition.RESPONSE_SEARCH_LON,0);
            Location location = new Location("location");
            location.setLatitude(lat);
            location.setLongitude(lon);
            GpsControl.getInstance(this).setLocation(location);
            initMyLocationView();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        active = false;
    }

}
