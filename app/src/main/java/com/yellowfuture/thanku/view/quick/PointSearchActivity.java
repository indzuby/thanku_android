package com.yellowfuture.thanku.view.quick;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.skp.Tmap.TMapData;
import com.skp.Tmap.TMapPOIItem;
import com.yellowfuture.thanku.R;
import com.yellowfuture.thanku.utils.CodeDefinition;
import com.yellowfuture.thanku.utils.ContextUtils;
import com.yellowfuture.thanku.view.adapter.SearchResultAdapter;
import com.yellowfuture.thanku.view.common.BaseActivity;


import java.util.ArrayList;

/**
 * Created by zuby on 2016. 6. 17..
 */
public class PointSearchActivity extends BaseActivity implements TextWatcher,AdapterView.OnItemClickListener{

    final static int LIMIT_SEARCH_COUNT = 20;

    double lat,lon;

    TMapData mMapData;
    int mType;
    EditText mSearchEditText;
    TextView mPointByCurrentView;
    ListView mSearchListView;
    SearchResultAdapter searchAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_search);
        init();
    }

    public void initActionbar() {
        ContextUtils.getActionBar(this, getSupportActionBar(), R.layout.actionbar_default);
        findViewById(R.id.back).setOnClickListener(this);
        ((TextView)findViewById(R.id.title)).setText("검색");
    }

    @Override
    public void init() {
        initActionbar();

        lat = getIntent().getDoubleExtra(CodeDefinition.PARAM_LATITUDE,0);
        lon = getIntent().getDoubleExtra(CodeDefinition.PARAM_LONGITUDE,0);

        mMapData = new TMapData();
        mSearchListView = (ListView) findViewById(R.id.search_list);
        mType = getIntent().getIntExtra(CodeDefinition.SEARCH_POINT_PARAM_TYPE, 0);
        mSearchEditText = (EditText) findViewById(R.id.search_text_view);
        mPointByCurrentView = (TextView) findViewById(R.id.point_by_current);

        if (mType == 1) {
            mSearchEditText.setHint("도착지 검색");
            mPointByCurrentView.setVisibility(View.GONE);
        } else if (mType == 0) {
            mSearchEditText.setHint("출발지 검색");
            mPointByCurrentView.setText(Html.fromHtml(getString(R.string.search_by_current)));
        }
        mSearchEditText.addTextChangedListener(this);
        mSearchListView.setOnItemClickListener(this);
        mPointByCurrentView.setOnClickListener(this);
        searchAdapter = new SearchResultAdapter(new ArrayList<TMapPOIItem>(),getBaseContext());
        mSearchListView.setAdapter(searchAdapter);
    }

    public void initSearchResult(String keyword){
        int count = keyword.length();
        if(count>=3) {
            mPointByCurrentView.setVisibility(View.GONE);
            mSearchListView.setVisibility(View.VISIBLE);
            mMapData.findAllPOI(keyword.toString(), LIMIT_SEARCH_COUNT, new TMapData.FindAllPOIListenerCallback() {
                @Override
                public void onFindAllPOI(ArrayList<TMapPOIItem> arrayList) {
                    initResultView(arrayList);
                }
            });
        }else if(count<=1){
            mPointByCurrentView.setVisibility(View.VISIBLE);
            mSearchListView.setVisibility(View.GONE);
        }
    }
    public void initResultView(final ArrayList<TMapPOIItem> arrayList){
        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        searchAdapter.setMapPoiList(arrayList);
                        searchAdapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(v.getId() == R.id.point_by_current) {
            mMapData.convertGpsToAddress(lat, lon, new TMapData.ConvertGPSToAddressListenerCallback() {
                @Override
                public void onConvertToGPSToAddress(final String s) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mSearchEditText.setText(s);
                                }
                            });
                        }
                    }).start();
                }
            });
        }
    }

    public void returnPOIData(TMapPOIItem item){
        Log.d("POSITION : ", item.noorLat + " . " + item.noorLon);
        Intent intent = new Intent();
        intent.putExtra(CodeDefinition.PARAM_POI_NAME,item.getPOIName());
        intent.putExtra(CodeDefinition.SEARCH_POINT_PARAM_TYPE,mType);
        intent.putExtra(CodeDefinition.PARAM_LATITUDE,item.noorLat);
        intent.putExtra(CodeDefinition.PARAM_LONGITUDE,item.noorLon);
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TMapPOIItem item = searchAdapter.getItem(position);
        returnPOIData(item);
    }

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
}
