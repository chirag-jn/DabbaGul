package com.precog.dabbagul;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class LocationGetter implements LocationListener {

    protected LocationManager locationManager;

    protected double latitude,longitude;
    protected boolean gps_enabled,network_enabled;

    protected Context context;

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

    public void initialize(Context context) {
        this.context = context;

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        // getting GPS status
        gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // getting network status
        network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        try {
            // TODO: handle correctly

            if (gps_enabled) {
                locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
            } else if (network_enabled) {
                locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
            }
        } catch (SecurityException e) {

        }
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    public double[] getCoordinates() {
        double toRet[] = new double[]{latitude, longitude};
        return toRet;
    }
}
