package com.yellowfuture.thanku.view.common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.tsengvn.typekit.TypekitContextWrapper;
import com.yellowfuture.thanku.R;
import com.yellowfuture.thanku.model.Advertisement;
import com.yellowfuture.thanku.network.controller.AdvertisementController;
import com.yellowfuture.thanku.utils.CodeDefinition;
import com.yellowfuture.thanku.utils.SessionUtils;
import com.yellowfuture.thanku.utils.Utils;
import com.yellowfuture.thanku.view.adapter.ImagePagerAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by user on 2016-01-07.
 */
public class BaseActivity extends AppCompatActivity implements View.OnClickListener{

    boolean isActive = true;
    private ProgressDialog progressDialog;
    protected String mAccessToken;
    public void initView(){
        endProgress();
    }
    public void init(){
        mAccessToken = SessionUtils.getString(this, CodeDefinition.ACCESS_TOKEN,"");
        progressDialog = new ProgressDialog(this);
        startProgress();

    }
    public void startProgress(){

        if(progressDialog!=null) progressDialog.show();
    }
    public void endProgress(){
        if(progressDialog !=null) progressDialog.dismiss();
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

@Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()== R.id.back) {
            Utils.hideKeyboard(this,findViewById(R.id.back));
            finish();
            return;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode!=RESULT_OK) return ;
    }

    protected void initBannerAdvertisement(){
        AdvertisementController.getInstance(this).findByType(Advertisement.AdvertisementType.BANNER, new Callback<List<Advertisement>>() {
            @Override
            public void onResponse(Call<List<Advertisement>> call, final Response<List<Advertisement>> response) {
                if(response.code() == 200){
                    ImagePagerAdapter adapter = new ImagePagerAdapter(getBaseContext(),response.body());
                    final ViewPager bannerViewPager = (ViewPager) findViewById(R.id.bannerViewPager);
                    final LinearLayout ovalLayout = (LinearLayout) findViewById(R.id.ovalLayout);
                    bannerViewPager.setAdapter(adapter);
                    Utils.setOvalContainer(getBaseContext(), ovalLayout, response.body().size());
                    Utils.selectOval(ovalLayout, bannerViewPager, 0);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while (isActive) {
                                try {
                                    Thread.sleep(3000);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            bannerViewPager.setCurrentItem((bannerViewPager.getCurrentItem() + 1) % response.body().size(),true);
                                            Utils.selectOval(ovalLayout,bannerViewPager,bannerViewPager.getCurrentItem());
                                        }
                                    });
                                }catch (Exception e){
                                }
                            }
                        }
                    }).start();
                }
            }

            @Override
            public void onFailure(Call<List<Advertisement>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isActive = false;
    }
}
