package com.yellowfuture.thanku.view.quick;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.bumptech.glide.gifdecoder.GifDecoder;
import com.skp.Tmap.TMapData;
import com.skp.Tmap.TMapPoint;
import com.skp.Tmap.TMapPolyLine;
import com.skp.Tmap.TMapView;
import com.yellowfuture.thanku.R;
import com.yellowfuture.thanku.utils.CodeDefinition;
import com.yellowfuture.thanku.utils.ContextUtils;
import com.yellowfuture.thanku.view.common.BaseFragment;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zuby on 2016. 6. 15..
 */
public class QuickFragment extends BaseFragment {
    TMapView mMapView;
    LocationManager locationManager;
    Location location;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.element_quick, container, false);
        init();
        return mView;
    }

    public void init() {
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

        initMap();

        mView.findViewById(R.id.start_point_text).setOnClickListener(this);
        mView.findViewById(R.id.end_point_text).setOnClickListener(this);
    }

    LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            // Called when a new location is found by the network location provider.
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onProviderDisabled(String provider) {
        }
    };


    public void initMap() {
        FrameLayout mapLayout = (FrameLayout) mView.findViewById(R.id.map);
        mMapView = new TMapView(getContext());
        mMapView.setSKPMapApiKey(getString(R.string.map_key));
        initCurrentLocation();
        mapLayout.addView(mMapView, 0);
        mView.findViewById(R.id.current_button).setOnClickListener(this);
    }

    public void initCurrentLocation() {
        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        setCurrentLocation();
        samplePath();

    }

    public void setCurrentLocation() {
        mMapView.setCenterPoint(location.getLongitude(), location.getLatitude(), true);
        mMapView.setLocationPoint(location.getLongitude(), location.getLatitude());
        mMapView.setZoomLevel(17);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_location_on_black_24dp);
        mMapView.setIcon(bitmap);
        mMapView.setIconVisibility(true);
    }

    public void samplePath() {
        TMapPoint startPoint = mMapView.getLocationPoint();
        TMapPoint endPoint = new TMapPoint(37.5838699,127.0565884);
        TMapData mapData = new TMapData();
        mapData.findPathDataWithType(TMapData.TMapPathType.CAR_PATH, startPoint, endPoint,
                new TMapData.FindPathDataListenerCallback() {
                    @Override
                    public void onFindPathData(TMapPolyLine polyLine) {
                        polyLine.setLineWidth(15f);
                        polyLine.setLineColor(ContextCompat.getColor(getContext(),R.color.colorAccent));
                        mMapView.addTMapPath(polyLine);
                        mMapView.setZoomLevel(12);
                        Log.e("DISTANCE",polyLine.getDistance()+"");
                    }
                });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.current_button:
                setCurrentLocation();
                break;
            case R.id.start_point_text:
                startPointSearch(0);
                break;
            case R.id.end_point_text:
                startPointSearch(1);
                break;
        }
    }
    public void startPointSearch(int type) {
        Intent intent = new Intent(getActivity(),PointSearchActivity.class);
        intent.putExtra(CodeDefinition.SEARCH_POINT_PARAM_TYPE,type);
        startActivityForResult(intent, CodeDefinition.REQUEST_SEARCH_POINT);
    }
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
