package com.suks.sittiporn.lslamic.features.maps;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.suks.sittiporn.lslamic.R;
import com.suks.sittiporn.lslamic.features.checkinlist.detail.DetailCheckInActivity;
import com.suks.sittiporn.lslamic.data.remote.response.LocationListModel;
import com.suks.sittiporn.lslamic.data.remote.response.LocationReponseModel;
//import com.suks.sittiporn.lslamic.core.util.GPSTracker;
//import com.suks.sittiporn.lslamic.core.util.GPSTracker2;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class MapsIslamicFragment extends Fragment implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, OnMapReadyCallback {


    protected GoogleApiClient mGoogleApiClient;
    protected Location mCurrentLocation;
    protected LocationRequest mLocationRequest;
    protected String mLastUpdateTime;

    boolean isCheck = false;

    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 5000;

    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 5;


    private GoogleMap mMap;
    private Circle circle;
    List<LocationReponseModel> list;

    Dialog dialog;

    private MapWrapperLayout mapWrapperLayout;
    private ViewGroup infoWindow;
    private TextView infoTitle;
    private TextView infoSnippet;
    private Button infoButton;
    private OnInfoWindowElemTouchListener infoButtonListener;
    public static String ARG_LAT = "ARG_LAT";
    public static String ARG_LNG = "ARG_LNG";
    public static String ARG_LIST = "ARG_LIST";
    static LocationListModel locationListModel;

    List<LocationReponseModel> modelList = new ArrayList<>();

//    LatLng currentLatLng;

//    private static final int REQUEST_LOCATION = 1;
//    Button btnGetLocation;
//    TextView showLocation;
//    private LocationManager locationManager;
    Double latitude = 0.0, longitude = 0.0;
    ImageButton searchMaps;
    ImageButton refresh;
    FixLocationListAdapter locationAdapter;
    private RecyclerView.LayoutManager manager;
//    GPSTracker gpsTracker;
//    GPSTracker2 gpsTracker2;


    private void addLocation(LatLng latLng, String locationName) {
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .title(locationName);
        mMap.addMarker(markerOptions);
//        mMap.addMarker(new MarkerOptions().position(latLng).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {


        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
//        mMap.getUiSettings().setMyLocationButtonEnabled(true);
//        mMap.getUiSettings().setMapToolbarEnabled(true);


//        latitude = Double.valueOf(getArguments().getString(ARG_LAT));
//        latitude = Double.valueOf(getArguments().getString(ARG_LNG));


//

        // MapWrapperLayout initialization
        // 39 - default marker height
        // 20 - offset between the default InfoWindow bottom edge and it's content bottom edge
        mapWrapperLayout.init(mMap, getPixelsFromDp(getContext(), 39 + 20));

        this.infoWindow = (ViewGroup) getLayoutInflater().inflate(R.layout.custom_info_window, null);
        this.infoTitle = (TextView) infoWindow.findViewById(R.id.title);
        this.infoSnippet = (TextView) infoWindow.findViewById(R.id.snippet);
        this.infoButton = (Button) infoWindow.findViewById(R.id.button);

        this.infoButtonListener = new OnInfoWindowElemTouchListener(infoButton,
                getResources().getDrawable(R.drawable.button_normal),
                getResources().getDrawable(R.drawable.button_pressed)) {
            @Override
            protected void onClickConfirmed(View v, Marker marker) {
                // Here we can perform some action triggered after clicking the button
//                Toast.makeText(getContext(), marker.getTitle() + "'s button clicked! \n" +
//                        marker.getSnippet() + "'s button getSnippet! \n", Toast.LENGTH_SHORT).show();

                String sub = marker.getSnippet();

                Intent intent = new Intent(getContext(), DetailCheckInActivity.class);
                intent.putExtra("locationId", sub.split(", ")[2]);
                intent.putExtra("time", "");
                intent.putExtra("room", "");
                intent.putExtra("person", "");
                intent.putExtra("name", "");
                intent.putExtra("lat", sub.split(", ")[0]);
                intent.putExtra("lng", sub.split(", ")[1]);
                intent.putExtra("location", "");
                intent.putExtra("desc", "");

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(intent);

            }
        };
        this.infoButton.setOnTouchListener(infoButtonListener);


        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                // Setting up the infoWindow with current's marker info
                infoTitle.setText(marker.getTitle());
                infoSnippet.setText(marker.getSnippet());
                infoButtonListener.setMarker(marker);

                // We must call this to set the current marker and infoWindow references
                // to the MapWrapperLayout
                mapWrapperLayout.setMarkerWithInfoWindow(marker, infoWindow);
                return infoWindow;
            }
        });


//        mMap.addMarker(new MarkerOptions()
//                .title("")
//                .snippet("Czech Republic")
//                .position(new LatLng(50.08, 14.43)));
//

        // Let's add a couple of markers



    }
//    @SuppressLint("MissingPermission")
//    private void getLocation() {
//        locationManager = (LocationManager) getContext()
//                .getSystemService(LOCATION_SERVICE);
//
//        if (ActivityCompat.checkSelfPermission(
//                getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
//        } else {
//            LocationListener locationListener = new LocationListener() {
//
//                public void onLocationChanged(Location location) {
//                    double lat = location.getLatitude();
//                    double longi = location.getLongitude();
//
//                    latitude = location.getLatitude();
//                    longitude = location.getLongitude();
//                    currentLatLng = new LatLng(latitude, longitude);
//                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f));
//                    drawCircle(currentLatLng, 5000.0);
////                    latitude = String.valueOf(lat);
////                    longitude = String.valueOf(longi);
////                    txt_location.setText(latitude +", "+ longitude);
//
//                }
//
//                public void onStatusChanged(String provider, int status, Bundle extras) {}
//
//                public void onProviderEnabled(String provider) {}
//
//                public void onProviderDisabled(String provider) {}
//            };
//            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
//        }
//
//    }

    public void mapsLocation() {



//        locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) this);
//        gpsTracker = new GPSTracker(getContext());
//        gpsTracker2 = new GPSTracker2(getContext());



//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
//        fusedLocationClient.getLastLocation()
//                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
//                    @Override
//                    public void onSuccess(Location location) {
//                        // Got last known location. In some rare situations this can be null.
//                        if (location != null) {
//                            latitude = location.getLatitude();
//                            longitude = location.getLongitude();
//                            currentLatLng = new LatLng(latitude, longitude);
//                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f));
//                            drawCircle(currentLatLng, 5000.0);
//                        }
//                    }
//                });


//        LatLng currentLatLng = new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude());

//        mMap.addCircle()
//        addLocation(currentLatLng,"home");
//        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 20f));

        setMapsLocation(locationListModel.getData());
    }


    public void setMapsLocation(List<LocationReponseModel> list) {
        if (list != null) {
//            for (int i = 0; i < list.size(); i++) {
//                mapsSet(list.get(i));
//                LatLng latLng = new LatLng(ist.get(i))
//                CalculationByDistance();
//            }
            for (LocationReponseModel reponseModel : list) {
                if (!reponseModel.getLongitude().equals("") || !reponseModel.getLatitude().equals("")) {
                    mapsSet(reponseModel);
                    Double aDoubleLat = Double.valueOf(reponseModel.getLatitude());
                    Double aDoubleLng = Double.valueOf(reponseModel.getLongitude());
                    LatLng latLngEnd = new LatLng(aDoubleLat, aDoubleLng);
//                gpsTracker = new GPSTracker2(getContext());
                    LatLng latLnnS = new LatLng(latitude, longitude);
                    Double distance = CalculationByDistance(latLnnS, latLngEnd);
                    String dis = String.valueOf(distance);
                    dis.split(".", 2);
                    reponseModel.setDistance(String.valueOf(distance));
                    modelList.add(reponseModel);

                }


            }
        }
    }

    public double CalculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);

        return Radius * c;
    }

    private void mapsSet(LocationReponseModel locationReponseModel) {

//        String time = locationReponseModel.getTimeStart().split(":")[0] + ":" +
//                locationReponseModel.getTimeStart().split(":")[1] + "-" +
//                locationReponseModel.getTimeEnd().split(":")[0] + ":" +
//                locationReponseModel.getTimeEnd().split(":")[1] + " น.";

        String sub =
                locationReponseModel.getLatitude() + ", " +
                        locationReponseModel.getLongitude() + ", " +
                        locationReponseModel.getId();

        mMap.addMarker(new MarkerOptions()
                .title(locationReponseModel.getName())
                .snippet(sub)
                .draggable(true)
                .visible(true)
                .position(new LatLng(Double.parseDouble(locationReponseModel.getLatitude()), Double.parseDouble(locationReponseModel.getLongitude()))));

    }

    private void drawCircle(LatLng latLng, Double radius) {
        if (mMap == null) return;
        if (circle != null)
            circle.remove();

        int strokeColor = getResources().getColor(R.color.border_primary);
        int shadeColor = getResources().getColor(R.color.primary_tran);
        CircleOptions circleOptions = new CircleOptions().center(latLng)
                .radius(radius)

                .fillColor(shadeColor)
                .strokeColor(strokeColor)
                .strokeWidth(1);

        circle = mMap.addCircle(circleOptions);

    }

    public static int getPixelsFromDp(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 3.0f);
    }

    public static MapsIslamicFragment newInstance(LocationListModel locationListModel) {
        MapsIslamicFragment fragment = new MapsIslamicFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_LIST, locationListModel);
//        args.putString(ARG_LAT, lat);
//        args.putString(ARG_LNG, lng);
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps_islamic, null, false);

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        currentLatLng = getLocation();


        this.init(view, savedInstanceState);
    }

    private void init(View view, Bundle savedInstanceState) {

        buildGoogleApiClient();
        ArrayList list = new ArrayList();

        list.add(1);
        list.add(3);
        list.add(5);
        list.add(6);

        list.size();

        mapWrapperLayout = (MapWrapperLayout) view.findViewById(R.id.map_relative_layout);
        searchMaps = (ImageButton) view.findViewById(R.id.searchMaps);
        refresh = (ImageButton) view.findViewById(R.id.refresh);
//        getLocation();

        searchMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogListMapsS();
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
                mapsLocation();
            }
        });


//        getList("");


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
    }


    public void dialogListMapsS() {

        dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_ACTION_BAR);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.custom_dialog_location_fix);

        RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.recyclerView);
//        NestedScrollView nestedScrollView = (NestedScrollView) dialog.findViewById(R.id.nestedScrollView);
        SearchView search = (SearchView) dialog.findViewById(R.id.search);
        ImageView imgClose = (ImageView) dialog.findViewById(R.id.img_close);
        search.setQueryHint("search");
        dialog.setCancelable(true);

        Window window = dialog.getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);


        manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        locationAdapter = new FixLocationListAdapter(getContext(), modelList, location);
//        nestedScrollView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(locationAdapter);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                locationAdapter.getFilter().filter(newText);
                return false;
            }
        });

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    @Override
    public void onResume() {
        super.onResume();

        if(isCheck){
            isCheck = false;
        }

        this.initinstanceState();
    }

    private void initinstanceState() {

    }

//    private LatLng getLocation() {
//        locationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);
//
//        if (ActivityCompat.checkSelfPermission(
//                getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
//        } else {
////            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
////            if (locationGPS != null) {
////                double lat = locationGPS.getLatitude();
////                double longi = locationGPS.getLongitude();
////                latitude = lat;
////                longitude = longi;
////            } else {
////                Toast.makeText(getContext(), "Unable to find location.", Toast.LENGTH_SHORT).show();
////            }
//
//            LocationListener locationListener = new LocationListener() {
//
//                public void onLocationChanged(Location location) {
//                    double lat = location.getLatitude();
//                    double longi = location.getLongitude();
//                    latitude = lat;
//                    longitude = longi;
//                }
//
//                public void onStatusChanged(String provider, int status, Bundle extras) {}
//
//                public void onProviderEnabled(String provider) {}
//
//                public void onProviderDisabled(String provider) {}
//            };
//            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
//        }
//
//        LatLng currentLatLng = new LatLng(latitude, longitude);
//        return currentLatLng;
//    }

    FixLocationListAdapter.Listener location = new FixLocationListAdapter.Listener() {
        @Override
        public void location(LocationReponseModel location) {
//            edt_Name.setText(location.getLocationName());
//            edt_Lat.setText(location.getLat());
//            edt_Lng.setText(location.getLng());
            mMap.clear();
            LatLng latLng = new LatLng(Double.parseDouble(location.getLatitude()), Double.parseDouble(location.getLongitude()));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f));
//            infoButton.setOnTouchListener(infoButtonListener);
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mapsSet(location);
            dialog.dismiss();
//            Toast.makeText(getContext(), location.getLocationName(), Toast.LENGTH_LONG).show();

        }
    };

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        createLocationRequest();
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();

        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);

        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        startLocationUpdates();
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();

        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);

        mGoogleApiClient.disconnect();
    }


    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        latitude = mCurrentLocation.getLatitude();
        longitude = mCurrentLocation.getLongitude();

        if(!isCheck){

            isCheck = true;
//        LatLng latLng = new LatLng(new LatLng(latitude, longitude));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 12f));
            drawCircle(new LatLng(latitude, longitude), 1000.0);

            locationListModel = (LocationListModel) getArguments().getSerializable(ARG_LIST);
            mapsLocation();
        }

//        txt_location.setText(String.valueOf(mCurrentLocation.getLatitude()) +", "+ String.valueOf(mCurrentLocation.getLongitude()));
    }


    @Override
    public void onConnectionFailed(ConnectionResult result) {
//        Log.i("LocationFragment", "Connection failed: ConnectionResult.getErrorCode() " + result.getErrorCode());
    }

    @Override
    public void onConnectionSuspended(int cause) {
        mGoogleApiClient.connect();
    }
    @Override
    public void onConnected(Bundle bundle) {
        // Display the connection status
//        Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
//        if(servicesConnected()) {
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest,this);
//        }
    }




}