package com.yellowfuture.thanku.view.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yellowfuture.thanku.R;
import com.yellowfuture.thanku.model.Errand;
import com.yellowfuture.thanku.view.account.AccountFragment;
import com.yellowfuture.thanku.view.errand.ErrandFragment;
import com.yellowfuture.thanku.view.quick.QuickFragment;

/**
 * Created by zuby on 2016. 6. 15..
 */
public class MainTabPagerAdapter extends FragmentPagerAdapter {
    Context mContext;
    int[] imageResId={R.mipmap.ic_insert_comment_white_24dp,R.mipmap.ic_local_shipping_white_24dp,R.mipmap.ic_person_white_24dp};
    String[] title ={"잔심부름","퀵서비스","내 정보"};
    public MainTabPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) return new ErrandFragment();
        else if (position == 1) return new QuickFragment();
        else if (position == 2) return new AccountFragment();
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
    public View getTabView(int position) {
        // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
        View v = LayoutInflater.from(mContext).inflate(R.layout.element_tab, null);
        TextView tv = (TextView) v.findViewById(R.id.textView);
        tv.setText(title[position]);
        ImageView img = (ImageView) v.findViewById(R.id.image);
        img.setImageResource(imageResId[position]);
        return v;
    }
}
