package com.yellowfuture.thanku.control;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;

import com.skp.Tmap.TMapData;
import com.skp.Tmap.TMapGpsManager;
import com.skp.Tmap.TMapView;
import com.yellowfuture.thanku.R;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by zuby on 2016. 7. 19..
 */
public class GpsControl {
    @Getter @Setter
    private Context context;
    private static GpsControl instance;
    TMapGpsManager mMapGps;
    @Getter @Setter
    TMapView mMapView;
    @Getter @Setter
    private Location location;

    private LocationManager mLocationManager;

    public GpsControl(Context context) {
        this.context = context;
    }

    public static GpsControl getInstance(Context context) {
        synchronized (GpsControl.class) {
            if (instance == null) {
                instance = new GpsControl(context);
            }
        }
        return instance;
    }
    public void startGpsTrace(){
        mLocationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        location = getLastBestLocation();
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,locationListener);
        if(mMapView == null) {
            mMapView = new TMapView(getContext());
            mMapView.setSKPMapApiKey(getContext().getString(R.string.map_key));
        }


    }
    private Location getLastBestLocation() {
        Location locationGPS = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Location locationNet = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        long GPSLocationTime = 0;
        if (null != locationGPS) { GPSLocationTime = locationGPS.getTime(); }

        long NetLocationTime = 0;

        if (null != locationNet) {
            NetLocationTime = locationNet.getTime();
        }

        if ( 0 < GPSLocationTime - NetLocationTime ) {
            return locationGPS;
        }
        else {
            return locationNet;
        }
    }
    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            setLocation(location);
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
    };

}
