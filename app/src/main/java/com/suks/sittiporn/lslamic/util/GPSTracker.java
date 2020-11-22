package com.suks.sittiporn.lslamic.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by niceinkeaw on 7/12/2558.
 */
public class GPSTracker implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, android.location.LocationListener {
    private Listener listener;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private final static String TAG = "GPSTracker";
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;

    public interface Listener {
        void onLocationChanged(Location location);

        void onLocationServiceNotAvailable();

        void onDetectMockLocation();

        void onLocationFailed();
    }

    private GoogleApiClient mGoogleApiClient;
    private final Context mContext;

    // flag for GPS status
    boolean isGPSEnabled = false;
    // flag for network status
    boolean isNetworkEnabled = false;

    static volatile boolean canGetLocation = false;

    Location currentLocation; // currentLocation

    static volatile double LATITUDE = 0; // LATITUDE
    static volatile double LONGITUDE = 0; // LONGITUDE


    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    //private long UPDATE_INTERVAL = 30000;  /* 60 secs */
    private long UPDATE_INTERVAL = 10000;  /* 30 secs */
    private long FASTEST_INTERVAL = 5000; /* 5 secs */

    private Location lastMockLocation; // last mock currentLocation
    private boolean isMockLocation; // mock currentLocation status
    private int numGoodReadings;

    // Declaring a Location Manager
    protected LocationManager mLocationManager;

    public GPSTracker(Context context) {
        this.mContext = context;
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
        }

    }

    public void connect() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    public void connect(Listener listener) {
        this.listener = listener;
        connect();
    }

    public void disconnect() {
        // Disconnecting the client invalidates it.

        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
            listener = null;
        }
    }

    private boolean _isLocationEnabled() {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(mContext.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        } else {
            locationProviders = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }

    }

    public static LatLng CurrentLatLng() {
        LatLng currentLatLng;
//        Realm realm = Realm.getDefaultInstance();
//        if (realm.where(SaveFixLocationRealm.class).findAll().size() > 0) {
//            double lat = Double.parseDouble(realm.where(SaveFixLocationRealm.class).findFirst().getLat());
//            double lng = Double.parseDouble(realm.where(SaveFixLocationRealm.class).findFirst().getLng());
//            currentLatLng = new LatLng(lat, lng);
//        } else {
            currentLatLng = new LatLng(GPSTracker.getLatitude(), GPSTracker.getLongitude());
//        }

        return currentLatLng;
    }


    @SuppressLint("MissingPermission")
    public Location getLocation() {
//        if (currentLocation != null)
//            return currentLocation;
//        double location;


//        Realm realm = Realm.getDefaultInstance();
//        if (realm.where(SaveFixLocationRealm.class).findAll().size() == 0) {


//            LATITUDE = Double.parseDouble(realm.where(SaveFixLocationRealm.class).findFirst().getLat());
//            LONGITUDE = Double.parseDouble(realm.where(SaveFixLocationRealm.class).findFirst().getLng());
//            location = Double.parseDouble(LATITUDE + "," + LONGITUDE);
//            return location;
//
//        } else {


            try {
                mLocationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

                // getting GPS status
                if (mLocationManager != null) {
                    isGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);


                    // getting network status
                    isNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                } else {
                    return currentLocation;
                }

                if (!isGPSEnabled && !isNetworkEnabled) {
                    // no network provider is enabled
                    Log.d("Network&GPS", "Network&GPS is not enable");
                } else {
                    //this.canGetLocation = true;

                    // if GPS Enabled get lat/long using GPS Services
                    if (isNetworkEnabled) {
                        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("Network", "Network");
                        if (mLocationManager != null) {
                            currentLocation = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                            if (currentLocation != null && LATITUDE == 0 && LONGITUDE == 0) {
                                LATITUDE = currentLocation.getLatitude();
                                LONGITUDE = currentLocation.getLongitude();
                                canGetLocation = true;
                            }
                        }
                        return currentLocation;
                    }

                    if (isGPSEnabled) {

                        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                        Log.d("GPS Enabled", "GPS Enabled");

                        if (mLocationManager != null) {
                            //currentLocation = getLastKnownLocation();
                            currentLocation = mLocationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
                            if (currentLocation != null && LATITUDE == 0 && LONGITUDE == 0) {
                                canGetLocation = true;
                                LATITUDE = currentLocation.getLatitude();
                                LONGITUDE = currentLocation.getLongitude();
                            }
                        }

                        return currentLocation;
                    }

                    // First get currentLocation from Network Provider


                }
            } catch (Exception e) {
                Toast.makeText(mContext, "Cannot get currentLocation. Please check GPS or internet enabled.", Toast.LENGTH_LONG).show();
                if (listener != null)
                    listener.onLocationServiceNotAvailable();
            } finally {
                if (currentLocation == null) {
                    if (listener != null)
                        listener.onLocationServiceNotAvailable();
                    Toast.makeText(mContext, "Cannot get currentLocation. Something wrong.", Toast.LENGTH_LONG).show();
                }

            }
//        } else {
//            currentLocation = location();
//            return currentLocation;


        return currentLocation;
//        }


    }

    public static LatLng latLng() {
        LatLng currentLatLng = null;
            currentLatLng = new LatLng(GPSTracker.getLatitude(), GPSTracker.getLongitude());

        return currentLatLng;

    }

    public static Location location() {

        Location location = new Location(Context.LOCATION_SERVICE);
        location.setLatitude(latLng().latitude);
        location.setLongitude(latLng().longitude);

        LATITUDE = location.getLatitude();
        LONGITUDE = location.getLongitude();
        canGetLocation = true;

        return location;
    }

    public static double Lng() {
        return 0;
    }

    public static double getLatitude() {

        // return LATITUDE
        return LATITUDE;
    }

    public static double getLongitude() {

        // return LONGITUDE
        return LONGITUDE;
    }

    public static boolean canGetLocation() {
        return canGetLocation;
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        // Setting Dialog Title
        alertDialog.setTitle("Location is settings");

        // Setting Dialog Message
        alertDialog.setMessage("Location is not enabled. Do you want to go to settings menu?");

        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.delete);

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    public void stopUsingGPS() {
        if (mLocationManager != null) {
            //mLocationManager.removeUpdates(GPSTracker.this);
        }
    }


    @SuppressLint("MissingPermission")
    @Override
    public void onConnected(@Nullable Bundle bundle) {
//        if (_isLocationEnabled()) {
//            // Call Location Services
//            LocationRequest locationRequest = new LocationRequest()
//                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY) //| LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
//                    .setSmallestDisplacement(MIN_DISTANCE_CHANGE_FOR_UPDATES)
//                    .setInterval(UPDATE_INTERVAL)
//                    .setFastestInterval(FASTEST_INTERVAL);
//            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, this);
//        } else {
//            this.listener.onLocationFailed();
//            canGetLocation = false;
//            showSettingsAlert();
//        }
    }


    @Override
    public void onConnectionSuspended(int i) {
        canGetLocation = false;
        if (i == CAUSE_SERVICE_DISCONNECTED) {
            Toast.makeText(mContext, "Disconnected. Please re-connect.", Toast.LENGTH_SHORT).show();
        } else if (i == CAUSE_NETWORK_LOST) {
            Toast.makeText(mContext, "Network lost. Please re-connect.", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            canGetLocation = false;
            // Start an Activity that tries to resolve the error

            /*
             * Thrown if Google Play services canceled the original
             * PendingIntent
             */
        } else {
            canGetLocation = false;
            this.listener.onLocationFailed();
            Toast.makeText(this.mContext,
                    "Sorry. Location services not available to you : " + connectionResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLocation = location;

        isMockLocation = isFromMockGPSProvider(location);

        canGetLocation = true;
        String provider = location.getProvider();
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        double altitude = location.getAltitude();
        float accuracy = location.getAccuracy();
        float bearing = location.getBearing();
        float speed = location.getSpeed();
        long time = location.getTime();

        LATITUDE = latitude;
        LONGITUDE = longitude;

        if (listener != null && isMockLocation)
            listener.onDetectMockLocation();
        if (listener != null)
            listener.onLocationChanged(location);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        //android location
    }

    @Override
    public void onProviderEnabled(String s) {
        //android location
    }

    @Override
    public void onProviderDisabled(String s) {
        //android location
    }

    private void log(String message) {
        Log.d(TAG, message);
    }

    /* checking on mock currentLocation */
    private boolean isMockLocationsOn() {
        // For API level < 18 we have to check the Settings.Secure flag
        if (Build.VERSION.SDK_INT < 18 &&
                !android.provider.Settings.Secure.getString(this.mContext.getContentResolver(), android.provider.Settings
                        .Secure.ALLOW_MOCK_LOCATION).equals("0")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isFromMockGPSProvider() {
        return isFromMockGPSProvider(currentLocation);
    }

    public boolean isFromMockGPSProvider(Location location) {
        if (location == null)
            return false;

        boolean isMock = isMockLocationsOn() || (Build.VERSION.SDK_INT >= 18 && location.isFromMockProvider());

        if (isMock) {
            lastMockLocation = location;
            numGoodReadings = 0;

            return true;
        } else {
            numGoodReadings = Math.min(numGoodReadings + 1, 1000000); // Prevent overflow

            // And finally, if it's more than 1km away from the last known mock, we'll trust it
            if (lastMockLocation != null) {
                double d = location.distanceTo(lastMockLocation);

                if (lastMockLocation.getLatitude() == location.getLatitude()
                        && lastMockLocation.getLongitude() == location.getLongitude()) {
                    return true;
                } else {
                    lastMockLocation = null;

                    mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                            .addApi(LocationServices.API)
                            .addConnectionCallbacks(this)
                            .addOnConnectionFailedListener(this)
                            .build();
                }
            }
        }

        // We only clear that incident record after a significant show of good behavior
        if (numGoodReadings >= 20) lastMockLocation = null;

        return false;
    }

}
