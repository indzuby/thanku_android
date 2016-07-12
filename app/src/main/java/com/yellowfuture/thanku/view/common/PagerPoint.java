package com.yellowfuture.thanku.view.common;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yellowfuture.thanku.R;
import com.yellowfuture.thanku.utils.ContextUtils;

/**
 * Created by zuby on 2016. 7. 12..
 */
public class PagerPoint {
    public static View getPoint(Context context) {
        return getPoint(context, LinearLayout.LayoutParams.WRAP_CONTENT);
    }
    public static View getPoint(Context context, int size) {
        ImageView imageView = new ImageView(context);
        int padding = ContextUtils.pxFromDp(context, 5);
        imageView.setImageResource(R.drawable.micro_oval_selector);
        imageView.setPadding(padding, padding, padding, padding);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(size, size));
        return imageView;
    }
}