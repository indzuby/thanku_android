package com.yellowfuture.thanku.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.yellowfuture.thanku.R;
import com.yellowfuture.thanku.model.Errand;

import java.util.List;

import lombok.Getter;

/**
 * Created by zuby on 2016. 6. 15..
 */
public class ErrandItemAdapter extends BaseAdapter {

    @Getter
    private Context context;
    List<Errand> mList;

    public ErrandItemAdapter(Context context, List<Errand> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Errand getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v == null)
            v = LayoutInflater.from(getContext()).inflate(R.layout.element_errand_item,null);

        return v;
    }
}
