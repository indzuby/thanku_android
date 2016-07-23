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
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import com.skp.Tmap.TMapPOIItem;
import com.yellowfuture.thanku.view.common.BaseActivity;
import com.yellowfuture.thanku.view.common.PagerPoint;

import java.util.regex.Pattern;

/**
 * Created by user on 2016-01-11.
 */
public class Utils {
    public enum ValidType{
        EMAIL,PHONE,NAME
    }

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
    public static void setStatusColor(Context context, Window window, int color) {

        if (Build.VERSION.SDK_INT>=21) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(context, color));
        }
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
    public static String parsePOIAddressOld(TMapPOIItem item){
        String subAddress="";
        if(item.firstNo!=null)
            subAddress = item.firstNo ;
        if(item.secondNo!=null && !item.secondNo.equals("0"))
            subAddress+="-" + item.secondNo;
        String address = item.getPOIAddress().replace("null","");

        return address +" "+ subAddress;
    }

    public static boolean validCheck(ValidType pattern, String str){
        boolean okPattern = false;
        String regex = null;

        //이메일 체크
        if(pattern == ValidType.EMAIL){
            regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        }

        //휴대폰번호 체크
        else if(pattern == ValidType.PHONE){
            regex = "^01(?:0|1[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$";
        }
        else if(pattern == ValidType.NAME) {
            regex = "^[가-힣]{2,6}$";
        }

        okPattern = Pattern.matches(regex, str);
        return okPattern;
    }
}
