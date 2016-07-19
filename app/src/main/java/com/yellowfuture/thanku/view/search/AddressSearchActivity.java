package com.yellowfuture.thanku.view.search;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skp.Tmap.TMapData;
import com.skp.Tmap.TMapPOIItem;
import com.yellowfuture.thanku.R;
import com.yellowfuture.thanku.control.GpsControl;
import com.yellowfuture.thanku.utils.Utils;
import com.yellowfuture.thanku.view.basic.BaseActivity;

import java.util.ArrayList;

/**
 * Created by zuby on 2016. 7. 14..
 */
public class AddressSearchActivity extends BaseActivity {
    EditText mAddressEditText;
    TMapData mMapData;
    View mHelpLayout;
    View mResultLayout;
    View mDetailLayout;
    LinearLayout mResultListLayout;
    TextView mMyAddressView;
    TextView mMyAddressName;
    boolean active = true;
    LocationManager mLocationManager;
    EditText mAddressBasicEditText;
    EditText mAddressDetailEditText;

    @Override
    public void initView() {
        mAddressEditText = (EditText) findViewById(R.id.addressEditText);
        mHelpLayout = findViewById(R.id.helpLayout);
        mResultLayout = findViewById(R.id.resultLayout);
        mDetailLayout = findViewById(R.id.detailLayout);
        mResultListLayout = (LinearLayout) findViewById(R.id.resultListLayout);
        mMyAddressView = (TextView) findViewById(R.id.myAddressView);
        mMyAddressName = (TextView) findViewById(R.id.myAddressName);
        mAddressBasicEditText = (EditText) findViewById(R.id.addressBasicEditText);
        mAddressDetailEditText = (EditText) findViewById(R.id.addressDetailEditText);

    }

    public void initActionBar() {
        Utils.getActionBar(this, getSupportActionBar(), R.layout.actionbar_default);
        getSupportActionBar().setElevation(0);
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.findAddress));
        findViewById(R.id.back).setOnClickListener(this);
    }

    public void initMyLocationView() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (active) {
                    try {
                        Location location = GpsControl.getInstance(AddressSearchActivity.this).getLocation();
                        String address = mMapData.convertGpsToAddress(location.getLatitude(), location.getLongitude());
                        ArrayList<TMapPOIItem> list = mMapData.findAllPOI(address, 1);
                        if (list.size() > 0) {
                            final TMapPOIItem item = list.get(0);
                            findViewById(R.id.myLocationLayout).setTag(item);
                            findViewById(R.id.myLocationLayout).setOnClickListener(resultItemListener);
                            if (active)
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mMyAddressName.setText(getString(R.string.addressName, item.getPOIName()));
                                        mMyAddressView.setText(getString(R.string.addressText, Utils.parsePOIAddressOld(item)));
                                    }
                                });
                        }
                        Thread.sleep(10000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();
    }

    public void initResultView(final ArrayList<TMapPOIItem> list) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                for (TMapPOIItem item : list) {
                    View view = LayoutInflater.from(AddressSearchActivity.this).inflate(R.layout.item_address_search, null, false);

                    TextView addressName = (TextView) view.findViewById(R.id.addressName);
                    TextView addressView = (TextView) view.findViewById(R.id.addressView);

                    addressName.setText(getString(R.string.addressName, item.getPOIName()));
                    addressView.setText(getString(R.string.addressText, Utils.parsePOIAddressOld(item)));
                    mResultListLayout.addView(view);
                    view.setTag(item);
                    view.setOnClickListener(resultItemListener);
                }
            }
        });
    }

    public void initSearchResult(String keyword) {
        int count = keyword.length();
        if (count >= 3) {
            mHelpLayout.setVisibility(View.GONE);
            mResultLayout.setVisibility(View.VISIBLE);
            mDetailLayout.setVisibility(View.GONE);
            mResultListLayout.removeAllViews();
            mMapData.findAllPOI(keyword.toString(), 10, new TMapData.FindAllPOIListenerCallback() {
                @Override
                public void onFindAllPOI(ArrayList<TMapPOIItem> arrayList) {
                    initResultView(arrayList);
                }
            });
        } else if (count <= 1) {
            mHelpLayout.setVisibility(View.VISIBLE);
            mResultLayout.setVisibility(View.GONE);
            mDetailLayout.setVisibility(View.GONE);
            mResultListLayout.removeAllViews();

        }

    }

    @Override
    public void init() {
        initActionBar();
        initView();
        mMapData = new TMapData();
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        initMyLocationView();
        mAddressEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                initSearchResult(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_search);
        init();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        active = false;
    }

    View.OnClickListener resultItemListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mDetailLayout.setVisibility(View.VISIBLE);
            mResultLayout.setVisibility(View.GONE);
            mHelpLayout.setVisibility(View.GONE);
            TMapPOIItem item = (TMapPOIItem) v.getTag();
            mAddressBasicEditText.setText(item.getPOIName());
            Utils.hideKeyboard(getBaseContext(),mAddressBasicEditText);
        }
    };
}
