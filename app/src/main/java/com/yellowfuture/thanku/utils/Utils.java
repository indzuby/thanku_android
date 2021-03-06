package com.yellowfuture.thanku.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import com.skp.Tmap.TMapPOIItem;
import com.yellowfuture.thanku.model.Restaurant;
import com.yellowfuture.thanku.model.RestaurantOrderMenu;
import com.yellowfuture.thanku.view.common.BaseActivity;
import com.yellowfuture.thanku.view.common.PagerPoint;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by user on 2016-01-11.
 */
public class Utils {

    @Getter
    @Setter
    public static Restaurant restaurant;

    @Getter
    @Setter
    public static List<RestaurantOrderMenu> orderMenuList;

    public enum ValidType {
        EMAIL, PHONE, NAME
    }

    public static int pxFromDp(Context context, int dp) {
        return Math.round(dp * context.getResources().getDisplayMetrics().density);
    }

    public static int percentFromDp(Context context, float percent) {
        float pxWidth = context.getResources().getDisplayMetrics().widthPixels;
        return Math.round(percent * pxWidth);
    }

    public static Drawable getDrawable(Context context, int id) {
        return Build.VERSION.SDK_INT >= 21 ? context.getResources().getDrawable(id, context.getTheme()) : context.getResources().getDrawable(id);
    }

    public static void setStatusColor(Context context, Window window, int color) {

        if (Build.VERSION.SDK_INT >= 21) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(context, color));
        }
    }

    public static View getActionBar(Context context, ActionBar actionBar, int layout) {
        View v = LayoutInflater.from(context).inflate(layout, null);
        actionBar.setCustomView(v);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setHomeButtonEnabled(false);
        Toolbar parent = (Toolbar) v.getParent();
        parent.setContentInsetsAbsolute(0, 0);
        return v;
    }

    public static void checkPermission(Context context) {
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
            ((BaseActivity) context).init();
        } else {
            ActivityCompat.requestPermissions((BaseActivity) context,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    CodeDefinition.REQUEST_PERMISSIONS_CODE);
        }
    }

    public static void setOvalContainer(Context context, LinearLayout layout, int size) {
        layout.removeAllViews();
        for (int i = 0; i < size; i++)
            layout.addView(PagerPoint.getPoint(context));
    }

    private static void selectOval(LinearLayout layout, int position) {
        for (int i = 0; i < layout.getChildCount(); i++)
            layout.getChildAt(i).setSelected(false);

        layout.getChildAt(position).setSelected(true);

    }

    public static void selectOval(final LinearLayout layout, ViewPager viewPager, int position) {
        selectOval(layout, position);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                selectOval(layout, position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public static void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static String parsePOIAddressOld(TMapPOIItem item) {
        String subAddress = "";
        if (item.firstNo != null)
            subAddress = item.firstNo;
        if (item.secondNo != null && !item.secondNo.equals("0"))
            subAddress += "-" + item.secondNo;
        String address = item.getPOIAddress().replace("null", "");

        return address + " " + subAddress;
    }

    public static boolean validCheck(ValidType pattern, String str) {
        boolean okPattern = false;
        String regex = null;

        //이메일 체크
        if (pattern == ValidType.EMAIL) {
            regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        }

        //휴대폰번호 체크
        else if (pattern == ValidType.PHONE) {
            regex = "^01(?:0|1[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$";
        } else if (pattern == ValidType.NAME) {
            regex = "^[가-힣]{2,6}$";
        }

        okPattern = Pattern.matches(regex, str);
        return okPattern;
    }

    private static double getDistanceFromCompany(double lat, double lon) {
        Location a = new Location("Company");
        a.setLatitude(CodeDefinition.COMPANY_LAT);
        a.setLongitude(CodeDefinition.COMPANY_LON);
        Location b = new Location("Destination");
        b.setLatitude(lat);
        b.setLongitude(lon);
        return a.distanceTo(b);
    }

    private static double getDistanceFromAToB(double alat, double alon, double blat, double blon) {
        Location a = new Location("START");
        a.setLatitude(alat);
        a.setLongitude(alon);
        Location b = new Location("Destination");
        b.setLatitude(blat);
        b.setLongitude(blon);
        return a.distanceTo(b);
    }

    private static int getDistancePrice(double distance) {
        int price = 3000;
        if (distance > 2000)
            price += 100 * Math.floor((distance - 2000) / 120);
        return price;
    }

    public static int getDistancePriceFromCompany(double lat, double lon) {
        return getDistancePrice(getDistanceFromCompany(lat, lon));
    }

    public static int getDistancePriceFromAToB(double alat, double alon, double blat, double blon) {
        return getDistancePrice(getDistanceFromAToB(alat, alon, blat, blon));
    }

    public static String getDistancePriceToString(int distance) {
        return getPriceToString(distance);
    }

    public static String getPriceToString(long price) {
        return String.format("%,d원", price);
    }

    public static String getDistanceToStringFromAToB(double alat, double alon, double blat, double blon) {
        double distance = getDistanceFromAToB(alat, alon, blat, blon);
        if (distance > 1000)
            return Math.round(distance / 100) / 10 + "km";
        else
            return Math.round(distance) + "m";
    }

    public static String getDateBefore(Date time) {
        return getDateBefore(new DateTime(time));
    }

    public static String getDateBefore(DateTime time) {
        String str = "";
        if (Days.daysBetween(time, new DateTime()).getDays() <= 0)
            if (Hours.hoursBetween(time, new DateTime()).getHours() <= 0)
                if (Minutes.minutesBetween(time, new DateTime()).getMinutes() == 0)
                    str = "방금 전";
                else
                    str = (Minutes.minutesBetween(time, new DateTime()).getMinutes() + "분 전");
            else
                str = (Hours.hoursBetween(time, new DateTime()).getHours() + "시간 전");
        else
            str = (Days.daysBetween(time, new DateTime()).getDays() + "일 전");
        return str;
    }
}
