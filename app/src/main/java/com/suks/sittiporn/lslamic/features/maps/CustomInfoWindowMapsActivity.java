package com.suks.sittiporn.lslamic.features.maps;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.suks.sittiporn.lslamic.R;
import com.suks.sittiporn.lslamic.features.checkin.ui.main.CheckInFragment;
import com.suks.sittiporn.lslamic.data.remote.response.LocationListModel;
import com.suks.sittiporn.lslamic.core.util.BaseActivity;

import java.io.Serializable;
import java.util.List;

public class CustomInfoWindowMapsActivity extends BaseActivity {

    public static final String INTENT_PROFILE_MODEL = "INTENT_PROFILE_MODEL";
    LocationListModel locationListModel;


    String latitude;
    String longitude;
    private LocationManager locationManager;
    private static final int REQUEST_LOCATION = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_info_window);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        locationListModel = (LocationListModel) getIntent().getSerializableExtra(INTENT_PROFILE_MODEL);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MapsIslamicFragment.newInstance(locationListModel))
                    .commitNow();
        }
    }

    public static Intent callingIntent(Context context, LocationListModel locationListModel) {
        Intent i = new Intent(context, CustomInfoWindowMapsActivity.class);
        i.putExtra(INTENT_PROFILE_MODEL, locationListModel);
        return i;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.home, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        List<Fragment> fragment = getSupportFragmentManager().getFragments();
//        if (id == R.id.action_list) {
//
////            if (fragment instanceof MapsIslamicFragment) {
////                MapsIslamicFragment.dialogListMapsS(CustomInfoWindowMapsActivity.this);
//
////            if (fragment instanceof MapsIslamicFragment) {
////                (MapsIslamicFragment) (MapsIslamicFragment) fragment).dialogListMapsS(CustomInfoWindowMapsActivity.this);
////
////
//
//                return true;
//
//        }
//        return true;
//    }
}