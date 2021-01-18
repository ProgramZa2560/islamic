package com.suks.sittiporn.lslamic.main.maps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MenuItem;

import com.suks.sittiporn.lslamic.R;
import com.suks.sittiporn.lslamic.main.time.ui.main.TimeFragment;
import com.suks.sittiporn.lslamic.util.BaseActivity;

public class MapsIslamicActivity extends BaseActivity {

    String latitude;
    String longitude;
    private LocationManager locationManager;
    private static final int REQUEST_LOCATION = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_islamic);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MapsIslamicFragment.newInstance(null))
                    .commitNow();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        locationManager = (LocationManager) MapsIslamicActivity.this
                .getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(
                MapsIslamicActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                MapsIslamicActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MapsIslamicActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            LocationListener locationListener = new LocationListener() {

                public void onLocationChanged(Location location) {
                    double lat = location.getLatitude();
                    double longi = location.getLongitude();
                    latitude = String.valueOf(lat);
                    longitude = String.valueOf(longi);
                }

                public void onStatusChanged(String provider, int status, Bundle extras) {}

                public void onProviderEnabled(String provider) {}

                public void onProviderDisabled(String provider) {}
            };
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}