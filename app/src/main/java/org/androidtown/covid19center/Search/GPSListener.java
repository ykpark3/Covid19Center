package org.androidtown.covid19center.Search;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import org.androidtown.covid19center.Map.LocationConsts;

public class GPSListener implements LocationListener {
    @Override
    public void onLocationChanged(Location location) {
        LocationConsts.NOW_X = location.getLatitude();
        LocationConsts.NOW_Y = location.getLongitude();
        Log.d("0546", String.valueOf(location.getLatitude()));
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
}
