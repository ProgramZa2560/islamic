package com.suks.sittiporn.lslamic.main.maps;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.widget.NestedScrollView;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.suks.sittiporn.lslamic.adapter.ListCheckInAdapter;
import com.suks.sittiporn.lslamic.main.checkinlist.detail.DetailCheckInActivity;
import com.suks.sittiporn.lslamic.main.time.ui.main.TimeFragment;
import com.suks.sittiporn.lslamic.manager.Retrofit2;
import com.suks.sittiporn.lslamic.manager.http.ApiService;
import com.suks.sittiporn.lslamic.model.reponse.LocationListModel;
import com.suks.sittiporn.lslamic.model.reponse.LocationReponseModel;
import com.suks.sittiporn.lslamic.util.GPSTracker;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static android.content.Context.LOCATION_SERVICE;

public class MapsIslamicFragment extends Fragment implements OnMapReadyCallback {

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
    public static String ARG_LIST = "ARG_LIST";
    LocationListModel locationListModel;

    private static final int REQUEST_LOCATION = 1;
    Button btnGetLocation;
    TextView showLocation;
    private  LocationManager locationManager;
    Double latitude, longitude;
    ImageButton searchMaps;
     FixLocationListAdapter locationAdapter;
    private RecyclerView.LayoutManager manager;



    private void addLocation(LatLng latLng, String locationName) {
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .title(locationName);
        mMap.addMarker(markerOptions);
//        mMap.addMarker(new MarkerOptions().position(latLng).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));


    }


    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {


        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(true);

        LatLng currentLatLng = new LatLng(latitude, longitude);
//

        // MapWrapperLayout initialization
        // 39 - default marker height
        // 20 - offset between the default InfoWindow bottom edge and it's content bottom edge
        mapWrapperLayout.init(mMap, getPixelsFromDp(getContext(), 39 + 20));

        // We want to reuse the info window for all the markers,
        // so let's create only one class member instance
        this.infoWindow = (ViewGroup) getLayoutInflater().inflate(R.layout.custom_info_window, null);
        this.infoTitle = (TextView) infoWindow.findViewById(R.id.title);
        this.infoSnippet = (TextView) infoWindow.findViewById(R.id.snippet);
        this.infoButton = (Button) infoWindow.findViewById(R.id.button);

        // Setting custom OnTouchListener which deals with the pressed state
        // so it shows up
        this.infoButtonListener = new OnInfoWindowElemTouchListener(infoButton,
                getResources().getDrawable(R.drawable.button_normal),
                getResources().getDrawable(R.drawable.button_pressed)) {
            @Override
            protected void onClickConfirmed(View v, Marker marker) {
                // Here we can perform some action triggered after clicking the button
                Toast.makeText(getContext(), marker.getTitle() + "'s button clicked! \n" +
                        marker.getSnippet()  + "'s button getSnippet! \n" , Toast.LENGTH_SHORT).show();

                String sub = marker.getSnippet();

                Intent intent = new Intent(getContext(), DetailCheckInActivity.class);
                intent.putExtra("locationId", sub.split(", ")[1]);
                intent.putExtra("time", sub.split(", ")[4]);
                intent.putExtra("room", sub.split(", ")[5]);
                intent.putExtra("person", sub.split(", ")[6]);
                intent.putExtra("name", sub.split(", ")[0]);
                intent.putExtra("lat", sub.split(", ")[2]);
                intent.putExtra("lng", sub.split(", ")[3]);

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

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f));
//        mMap.addCircle()
//        addLocation(currentLatLng,"home");
//        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 20f));
        drawCircle(currentLatLng, 5000.0);
//        mMap.addMarker(new MarkerOptions()
//                .title("")
//                .snippet("Czech Republic")
//                .position(new LatLng(50.08, 14.43)));
//

        // Let's add a couple of markers

        locationListModel = (LocationListModel) getArguments().getSerializable(ARG_LIST);
        setMapsLocation(locationListModel.getData());

    }

    private void setMapsLocation(List<LocationReponseModel> list) {
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
            mapsSet(list.get(i));
            }
        }
    }

    private  void mapsSet(LocationReponseModel locationReponseModel){

        String time =locationReponseModel.getTimeStart().split(":")[0] +":"+
                locationReponseModel.getTimeStart().split(":")[1] +"-"+
                locationReponseModel.getTimeEnd().split(":")[0] +":"+
                locationReponseModel.getTimeEnd().split(":")[1] +" น.";

        String sub = locationReponseModel.getName() + ", " +
                locationReponseModel.getId() + ", " +
                locationReponseModel.getLatitude() + ", " +
                locationReponseModel.getLongitude() + ", " +
                time + ", " +
                locationReponseModel.getRoomnumber() + ", " +
                locationReponseModel.getNumberfull();

        mMap.addMarker(new MarkerOptions()
                .title(locationReponseModel.getName())
                .snippet(sub)
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
        this.init(view, savedInstanceState);
    }

    private void init(View view, Bundle savedInstanceState) {


        mapWrapperLayout = (MapWrapperLayout) view.findViewById(R.id.map_relative_layout);
        searchMaps = (ImageButton) view.findViewById(R.id.searchMaps);

        searchMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogListMapsS();
            }
        });

        getLocation();
//        getList("");


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
    }
    public void dialogListMapsS() {

//        Toast.makeText(getContext(), "testtttttttt", Toast.LENGTH_LONG).show();
//        locationListModel

        dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_ACTION_BAR);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.custom_dialog_location_fix);

        RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.recyclerView);
        NestedScrollView nestedScrollView = (NestedScrollView) dialog.findViewById(R.id.nestedScrollView);
        SearchView search = (SearchView) dialog.findViewById(R.id.search);
        ImageView imgClose = (ImageView) dialog.findViewById(R.id.img_close);
        search.setQueryHint("search");
        dialog.setCancelable(true);

        Window window = dialog.getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);



        manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        locationAdapter = new FixLocationListAdapter(getContext(),  locationListModel.getData(), location);
        nestedScrollView.setNestedScrollingEnabled(false);
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
        this.initinstanceState();
    }

    private void initinstanceState() {

    }

    private void getLocation() {
        locationManager = (LocationManager) getContext()
                .getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(
                getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS != null) {
                double lat = locationGPS.getLatitude();
                double longi = locationGPS.getLongitude();
                latitude = lat;
                longitude = longi;
//                showLocation.setText("Your Location: " + "\n" + "Latitude: " + latitude + "\n" + "Longitude: " + longitude);
            } else {
                Toast.makeText(getContext(), "Unable to find location.", Toast.LENGTH_SHORT).show();
            }
        }
//        txt_location.setText(latitude +", "+ longitude);
    }

    FixLocationListAdapter.Listener location = new FixLocationListAdapter.Listener() {
        @Override
        public void location(LocationReponseModel location) {
//            edt_Name.setText(location.getLocationName());
//            edt_Lat.setText(location.getLat());
//            edt_Lng.setText(location.getLng());
            LatLng latLng = new LatLng(Double.parseDouble(location.getLatitude()),Double.parseDouble(location.getLongitude()));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20f));
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mapsSet(location);
            dialog.dismiss();
//            Toast.makeText(getContext(), location.getLocationName(), Toast.LENGTH_LONG).show();

        }
    };

}