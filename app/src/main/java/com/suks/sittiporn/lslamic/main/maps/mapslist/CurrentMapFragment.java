//package com.suks.sittiporn.lslamic.main.maps.mapslist;
//
//
//import android.content.Context;
//import android.location.Location;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageButton;
//
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentTransaction;
//
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.Circle;
//import com.google.android.gms.maps.model.CircleOptions;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MarkerOptions;
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.suks.sittiporn.lslamic.util.GPSTracker;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import java.util.concurrent.CopyOnWriteArrayList;
//
//import io.reactivex.Observable;
//import io.reactivex.ObservableOnSubscribe;
//import io.reactivex.android.schedulers.AndroidSchedulers;
//import io.reactivex.schedulers.Schedulers;
//
//public class CurrentMapFragment extends Fragment implements OnMapReadyCallback {
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
////    @BindView(R.id.imbClose)
////    ImageButton imbClose;
////    @BindView(R.id.fabFindLocation)
////    FloatingActionButton fabFindLocation;
////    Unbinder unbinder;
////    @BindView(R.id.imbLocationList)
////    ImageButton imbLocationList;
//
//    private String hostId;
//    private String mParam2;
//    private GPSTracker gpsTracker;
//    private SupportMapFragment mapFragment;
//    private GoogleMap googleMap;\
//    private Circle circle;
//    LatLng currentLatLng;
//
//
//    public CurrentMapFragment() {
//    }
//
//    public static CurrentMapFragment newInstance(String param1, String param2) {
//        CurrentMapFragment fragment = new CurrentMapFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            hostId = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View v = inflater.inflate(R.layout.fragment_current_map, container, false);
//        unbinder = ButterKnife.bind(this, v);
//        return v;
//    }
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        locations = new CopyOnWriteArrayList<>();
//        gpsTracker = new GPSTracker(getContext());
//        markerHashMap = new HashMap<>();
//        gpsTracker.connect(gpsTrackerListener);
//        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//        loadLocation();
//
//    }
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        this.googleMap = googleMap;
//        this.googleMap.setMyLocationEnabled(true);
//        this.googleMap.getUiSettings().setMyLocationButtonEnabled(false);
//        this.googleMap.getUiSettings().setMapToolbarEnabled(false);
//        this.googleMap.setOnMarkerClickListener(marker -> {
//            TimeStampCriteriaEntity entity = markerHashMap.get(marker.getTitle());
//            drawCircle(marker.getPosition(), entity.getRadius());
//            return false;
//        });
//
////        LatLng latLng1 = new LatLng(15.7831733,101.7568186);
////        LatLng latLng2 = new LatLng(15.784053333333334,101.757085);
////
////        addLocation(latLng2,"demco");
////        addLocation(latLng1,"me");
////        drawCircle(latLng2,50d);
//
//    }
//
//    public void loadLocation() {
//        Observable.create((ObservableOnSubscribe<Boolean>) subscriber -> {
//            if (locations == null || locations.size() == 0) {
//                DiskTimeStampRealmDao dao = new DiskTimeStampRealmDao(getContext());
//                locations = dao.getAllAddressCriteria(hostId);
////                filterLocation();
//
//            }
//            subscriber.onNext(true);
//            subscriber.onComplete();
//        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(e -> {
//            addAllLocation();
//        });
//    }
//
//
//    @OnClick(R.id.imbClose)
//    public void onClose(View view) {
//        getActivity().finish();
//    }
//
//    @OnClick(R.id.fabFindLocation)
//    public void onFindLocation(View view) {
//
//        gpsTracker.getLocation();
//
//        moveCurrentLocation();
//
//    }
//
//    @OnClick(R.id.imbLocationList)
//    public void onShowLocation(View view) {
//
//        List<TimeStampCriteriaEntity> list = calculateDistance();
//        FragmentManager fragmentManager = getFragmentManager();
//         FragmentTransaction transaction = fragmentManager.beginTransaction();
//        transaction.add(R.id.fragmentContainer, LocationListFragment.newInstance(list, ""));
//        transaction.addToBackStack("");
//        transaction.commit();
//
//    }
//
//    public List<TimeStampCriteriaEntity> calculateDistance() {
//
////        Realm realm = Realm.getDefaultInstance();
////        if (realm.where(SaveFixLocationRealm.class).findFirst().getLat() != null) {
////            double lat = Double.parseDouble(realm.where(SaveFixLocationRealm.class).findFirst().getLat());
////            double lng = Double.parseDouble(realm.where(SaveFixLocationRealm.class).findFirst().getLng());
////            currentLatLng = new LatLng(lat, lng);
////        }else {
//            currentLatLng = GPSTracker.CurrentLatLng();
////        }
////        LatLng currentLatLng = new LatLng(GPSTracker.getLatitude(), GPSTracker.getLongitude());
//
//        for (TimeStampCriteriaEntity criteria : locations) {
//            LatLng criteriaLatLng = new LatLng(NumberFormatUtil.toDouble(criteria.getLat()),
//                    NumberFormatUtil.toDouble(criteria.getLng()));
//            double distance = SphericalUtil.computeDistanceBetween(currentLatLng, criteriaLatLng) / 1000;
//            criteria.setDistance(distance);
//        }
//
//        List<TimeStampCriteriaEntity> list = new ArrayList<>(locations);
//        Collections.sort(list, (entity, t1) -> entity.getDistance().compareTo(t1.getDistance()));
//        return list;
//    }
//
//    private void drawCircle(LatLng latLng, Double radius) {
//        if(googleMap == null) return;
//        if (circle != null)
//            circle.remove();
//
//        int strokeColor = getResources().getColor(R.color.border_primary);
//        int shadeColor = getResources().getColor(R.color.primary_tran);
//        CircleOptions circleOptions = new CircleOptions().center(latLng)
//                .radius(radius)
//                .fillColor(shadeColor)
//                .strokeColor(strokeColor)
//                .strokeWidth(1);
//        circle = googleMap.addCircle(circleOptions);
//
//    }
//
//    private void addAllLocation() {
//        if (googleMap != null)
//            for (TimeStampCriteriaEntity entity : locations) {
//                LatLng latLng = new LatLng(Double.parseDouble(entity.getLat()), Double.parseDouble(entity.getLng()));
//                String locationName = entity.getLocationName();
//                addLocation(latLng, locationName);
//                markerHashMap.put(locationName, entity);
//            }
//    }
//
////    private void filterLocation() {
////        if (locations != null && locations.size() != 0)
////            for (TimeStampCriteriaEntity location : locations) {
////                if (location.getLocationName() != null && !location.getLocationName().equals("")) {
////                    locationFiler.add(location);
////                }
////            }
////    }
//
//    private void addLocation(LatLng latLng, String locationName) {
//        MarkerOptions markerOptions = new MarkerOptions()
//                .position(latLng)
//                .title(locationName);
//        googleMap.addMarker(markerOptions);
//
//
//    }
//
//    public void moveToLocation(LatLng latLng) {
//        if (googleMap != null)
//            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20f));
//
//    }
//
//    private void moveCurrentLocation() {
//
//        LatLng latLng = GPSTracker.latLng();
//        moveToLocation(latLng);
//    }
//
//
//    private GPSTracker.Listener gpsTrackerListener = new GPSTracker.Listener() {
//        @Override
//        public void onLocationChanged(Location location) {
//            Log.d("GPSTRACKER", "Location Change");
//            moveCurrentLocation();
//        }
//
//        @Override
//        public void onLocationServiceNotAvailable() {
//            Log.d("GPSTRACKER", "not available");
//        }
//
//        @Override
//        public void onDetectMockLocation() {
//
//        }
//
//        @Override
//        public void onLocationFailed() {
//
//        }
//    };
//
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        unbinder.unbind();
//
//    }
//
//
//}
