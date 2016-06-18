package com.yellowfuture.thanku.view.quick;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yellowfuture.thanku.R;
import com.yellowfuture.thanku.utils.CodeDefinition;
import com.yellowfuture.thanku.utils.ContextUtils;
import com.yellowfuture.thanku.view.common.BaseActivity;

/**
 * Created by zuby on 2016. 6. 17..
 */
public class PointSearchActivity extends BaseActivity {

    int mType;

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(v.getId() == R.id.back)
            finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_search);
        init();
    }

    public void initActionbar() {
        ContextUtils.getActionBar(this, getSupportActionBar(), R.layout.actionbar_search_point);
        findViewById(R.id.back).setOnClickListener(this);
    }

    @Override
    public void init() {
        initActionbar();

        mType = getIntent().getIntExtra(CodeDefinition.SEARCH_POINT_PARAM_TYPE, 0);
        EditText searchTextView = (EditText) findViewById(R.id.search_text_view);
        TextView pointByCurrnet = (TextView) findViewById(R.id.point_by_current);

        if (mType == 1) {
            searchTextView.setHint("도착지 검색");
            pointByCurrnet.setVisibility(View.GONE);
        } else if (mType == 0) {
            searchTextView.setHint("출발지 검색");
            pointByCurrnet.setText(Html.fromHtml(getString(R.string.search_by_current)));
        }


    }
}
