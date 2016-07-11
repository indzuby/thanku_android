package com.yellowfuture.thanku.view.quick;

import android.app.Activity;
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
import android.widget.TextView;
import android.widget.Toast;

import com.skp.Tmap.TMapData;
import com.skp.Tmap.TMapGpsManager;
import com.skp.Tmap.TMapMarkerItem;
import com.skp.Tmap.TMapPoint;
import com.skp.Tmap.TMapPolyLine;
import com.skp.Tmap.TMapView;
import com.yellowfuture.thanku.R;
import com.yellowfuture.thanku.utils.CodeDefinition;
import com.yellowfuture.thanku.view.common.BaseFragment;


/**
 * Created by zuby on 2016. 6. 15..
 */
public class QuickFragment extends BaseFragment implements LocationListener{
    TMapView mMapView;
    LocationManager locationManager;
    Location location;
    TMapGpsManager mMapGps;
    TMapData mMapData;
    TMapPoint mStartPoint, mEndPoint;
    TMapPolyLine mPolyLine;
    TextView mStartTextView, mEndTextView;
    TMapMarkerItem mStartItem, mEndItem;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.element_quick, container, false);
        init();
        return mView;
    }

    public void initGps(){
        mMapGps = new TMapGpsManager(getActivity());
        mMapGps.setProvider(TMapGpsManager.GPS_PROVIDER);
        mMapGps.OpenGps();
        mMapGps.setMinTime(1000);
        mMapGps.setMinDistance(5);
        mMapGps.setLocationCallback();
    }

    public void initData(){
        mMapData = new TMapData();
    }
    public void init() {
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        initData();
        initMap();

        mView.findViewById(R.id.start_point_text).setOnClickListener(this);
        mView.findViewById(R.id.end_point_text).setOnClickListener(this);

        mView.findViewById(R.id.request_button).setOnClickListener(this);

        mStartTextView = (TextView) mView.findViewById(R.id.start_text_view);
        mEndTextView = (TextView) mView.findViewById(R.id.end_text_view);
    }

    public void initMap() {
        FrameLayout mapLayout = (FrameLayout) mView.findViewById(R.id.map);
        if(mMapView == null)
            mMapView = new TMapView(getActivity());
        mMapView.setSKPMapApiKey(getString(R.string.map_key));
        initCurrentLocation();
        mapLayout.addView(mMapView, 0);
        mView.findViewById(R.id.current_button).setOnClickListener(this);
    }

    public void initCurrentLocation() {
        setCurrentLocation();
//        mStartPoint = mMapView.getLocationPoint();
//        mEndPoint = new TMapPoint(37.5838699,127.0565884);
//        findPathByLatLon();

    }

    public void setCurrentLocation() {
        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if(location!=null) {
            mMapView.setCenterPoint(location.getLongitude(), location.getLatitude(), true);
            mMapView.setLocationPoint(location.getLongitude(), location.getLatitude());
            mMapView.setZoomLevel(17);
        }
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.my_location_on);
//        mMapView.setIcon(bitmap);
//        mMapView.setIconVisibility(true);
    }

    public void findPathByLatLon() {
        mMapData.findPathDataWithType(TMapData.TMapPathType.CAR_PATH, mStartPoint, mEndPoint,
                new TMapData.FindPathDataListenerCallback() {
                    @Override
                    public void onFindPathData(TMapPolyLine polyLine) {
                        polyLine.setLineWidth(15f);
                        polyLine.setLineColor(ContextCompat.getColor(getContext(),R.color.colorAccent));
                        mMapView.addTMapPath(polyLine);
                        mMapView.setZoomLevel(12);
                        Log.e("DISTANCE",polyLine.getDistance()+"");
                        mMapView.refreshMap();
                        mPolyLine = polyLine;
                    }
                });
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
        mMapView.setLocationPoint(location.getLongitude(), location.getLatitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

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
            case R.id.request_button:
                requestQuickService();
                break;
        }
    }
    public void startPointSearch(int type) {
        Intent intent = new Intent(getActivity(),PointSearchActivity.class);
        intent.putExtra(CodeDefinition.SEARCH_POINT_PARAM_TYPE,type);
        intent.putExtra(CodeDefinition.PARAM_LONGITUDE,location.getLongitude());
        intent.putExtra(CodeDefinition.PARAM_LATITUDE,location.getLatitude());
        startActivityForResult(intent, CodeDefinition.REQUEST_SEARCH_POINT);
    }
    public void requestQuickService() {
        if(mPolyLine == null) {
            Toast.makeText(getActivity(),"출발지와 도착지를 입력해주세요.",Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(getActivity(),QuickRequestActivity.class);
        intent.putExtra(CodeDefinition.PARAM_START_LATITUDE, mStartPoint.getLatitude());
        intent.putExtra(CodeDefinition.PARAM_START_LONGITUDE, mStartPoint.getLongitude());
        intent.putExtra(CodeDefinition.PARAM_START_NAME, mStartTextView.getText().toString());
        intent.putExtra(CodeDefinition.PARAM_END_LATITUDE, mEndPoint.getLatitude());
        intent.putExtra(CodeDefinition.PARAM_END_LONGITUDE, mEndPoint.getLongitude());
        intent.putExtra(CodeDefinition.PARAM_END_NAME, mEndTextView.getText().toString());
        intent.putExtra(CodeDefinition.PARAM_DISTANCE,mPolyLine.getDistance());
        startActivityForResult(intent, CodeDefinition.REQUEST_QUICK_REQUEST);
    }
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != Activity.RESULT_OK) return;

        if(requestCode == CodeDefinition.REQUEST_SEARCH_POINT) {
            int type = data.getIntExtra(CodeDefinition.SEARCH_POINT_PARAM_TYPE,0);
            String name = data.getStringExtra(CodeDefinition.PARAM_POI_NAME);
            double lat = Double.parseDouble(data.getStringExtra(CodeDefinition.PARAM_LATITUDE));
            double lon = Double.parseDouble(data.getStringExtra(CodeDefinition.PARAM_LONGITUDE));
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_location_on_black_24dp);
            mMapView.removeAllTMapPolyLine();
            if(type == 0) {
                mStartPoint = new TMapPoint(lat,lon);
                if(mStartItem != null) mMapView.removeMarkerItem(mStartItem.getID());
                mStartItem = new TMapMarkerItem();
                mStartItem.setIcon(bitmap);
                mStartItem.setTMapPoint(mStartPoint);
                mMapView.addMarkerItem(name, mStartItem);
                mStartTextView.setText(name);
                if(mEndPoint !=null) findPathByLatLon();
            }
            else if(type==1) {
                mEndPoint = new TMapPoint(lat,lon);
                if(mEndItem != null) mMapView.removeMarkerItem(mEndItem.getID());
                mEndItem = new TMapMarkerItem();
                mEndItem.setIcon(bitmap);
                mEndItem.setTMapPoint(mEndPoint);
                mMapView.addMarkerItem(name, mEndItem);
                mEndTextView.setText(name);
                findPathByLatLon();
            }

            mMapView.setCenterPoint(lon,lat,true);
        }
    }
}
