//package com.suks.sittiporn.lslamic.main.maps.mapslist;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//
//import androidx.fragment.app.Fragment;
//
//import com.google.android.gms.maps.model.LatLng;
//import com.suks.sittiporn.lslamic.R;
//import com.suks.sittiporn.lslamic.util.BaseActivity;
//
//
//public class CurrentMapActivity extends BaseActivity implements LocationListFragment.Delegate {
//
//    public static String ARG_HOSTID = "ARG_HOSTID";
//
//    public static Intent callingIntent(Context context, String hostId) {
//        Intent intent = new Intent(context, CurrentMapActivity.class);
//        intent.putExtra(ARG_HOSTID, hostId);
//        return intent;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_current_map);
//
//        String hostId = getIntent().getStringExtra(ARG_HOSTID);
//
//        getSupportActionBar().hide();
//
//        if (savedInstanceState == null) {
//            addFragment(R.id.fragmentContainer, CurrentMapFragment.newInstance(hostId, ""));
//        }
//    }
//
//    @Override
//    public void onLocationClick(TimeStampCriteriaEntity entity) {
//        Fragment fragment = getSupportFragmentManager().getFragments().get(0);
//        if (fragment instanceof CurrentMapFragment) {
//            Double lat = Double.parseDouble(entity.getLat());
//            Double lng = Double.parseDouble(entity.getLng());
//
//            LatLng latLng = new LatLng(lat, lng);
//            ((CurrentMapFragment) fragment).moveToLocation(latLng);
//        }
//    }
//
//
//}
