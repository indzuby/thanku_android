package com.yellowfuture.thanku.view.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skp.Tmap.TMapPOIItem;
import com.yellowfuture.thanku.R;
import com.yellowfuture.thanku.utils.ContextUtils;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by zuby on 2016. 6. 19..
 */
public class SearchResultAdapter extends BaseAdapter {
    @Setter
    List<TMapPOIItem> mapPoiList;
    @Getter
    Context context;

    public SearchResultAdapter(List<TMapPOIItem> mapPoiList, Context context) {
        this.mapPoiList = mapPoiList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mapPoiList.size();
    }

    @Override
    public TMapPOIItem getItem(int position) {
        return mapPoiList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView v;
        if(convertView == null)
            v = new TextView(getContext());
        else
            v = (TextView) convertView;
        TMapPOIItem item = getItem(position);
        v.setText(item.getPOIName());
        v.setTextSize(14);
        v.setTextColor(ContextCompat.getColor(getContext(),R.color.black));
        int verticalMargin = ContextUtils.pxFromDp(getContext(),16);
        int horizontalMargin = ContextUtils.pxFromDp(getContext(),8);
        v.setPadding(verticalMargin,horizontalMargin,verticalMargin,horizontalMargin);
        return v;
    }
}
