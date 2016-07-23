package com.yellowfuture.thanku.view.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.yellowfuture.thanku.model.Advertisement;
import com.yellowfuture.thanku.network.RestApi;

import java.util.List;

/**
 * Created by zuby on 2016. 7. 23..
 */
public class AdvertisementPagerAdapter extends PagerAdapter {
    Context mContext;

    //    int[] sampleRes ={R.mipmap.samplebg1,R.mipmap.samplebg2,R.mipmap.samplebg3,R.mipmap.samplebg4};
    List<Advertisement> advertisementList;
    public AdvertisementPagerAdapter(Context context, List<Advertisement> advertisementList) {
        mContext = context;
        this.advertisementList =advertisementList;
    }
    @Override
    public int getCount() {
        return advertisementList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(mContext);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(params);
        Glide.with(mContext).load(RestApi.url+advertisementList.get(position).getUrl()).fitCenter().into(imageView);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
