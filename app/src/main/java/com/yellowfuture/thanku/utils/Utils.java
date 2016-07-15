package com.yellowfuture.thanku.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import com.yellowfuture.thanku.view.basic.BaseActivity;
import com.yellowfuture.thanku.view.common.PagerPoint;

/**
 * Created by user on 2016-01-11.
 */
public class Utils {
    public static int pxFromDp(Context context, int dp) {
        return Math.round(dp * context.getResources().getDisplayMetrics().density);
    }
    public static int percentFromDp(Context context, float percent) {
        float pxWidth = context.getResources().getDisplayMetrics().widthPixels;
        return Math.round(percent*pxWidth);
    }
    public static Drawable getDrawable(Context context, int id) {
        return Build.VERSION.SDK_INT >= 21?context.getResources().getDrawable(id, context.getTheme()):context.getResources().getDrawable(id);
    }
    public static View getActionBar(Context context, ActionBar actionBar, int layout) {
        View v = LayoutInflater.from(context).inflate(layout,null);
        actionBar.setCustomView(v);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setHomeButtonEnabled(false);
        Toolbar parent =(Toolbar) v.getParent();
        parent.setContentInsetsAbsolute(0, 0);
        return v;
    }
    public static void checkPermission(Context context){
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
            ((BaseActivity)context).init();
        } else {
            ActivityCompat.requestPermissions((BaseActivity)context,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    CodeDefinition.REQUEST_PERMISSIONS_CODE);
        }
    }

    public static void setOvalContainer(Context context,LinearLayout layout, int size) {
        layout.removeAllViews();
        for(int i = 0 ; i<size;i++)
            layout.addView(PagerPoint.getPoint(context));
    }
    public static void selectOval(LinearLayout layout, int position) {
        for(int i = 0 ; i<layout.getChildCount();i++)
            layout.getChildAt(i).setSelected(false);

        layout.getChildAt(position).setSelected(true);
    }

    public static void hideKeyboard(Context context,View view){
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
