package com.suks.sittiporn.lslamic.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.suks.sittiporn.lslamic.R;
import com.suks.sittiporn.lslamic.main.MapsActivity;
import com.suks.sittiporn.lslamic.main.checkin.CheckInActivity;
import com.suks.sittiporn.lslamic.main.checkinlist.CheckInListActivity;
import com.suks.sittiporn.lslamic.main.favorite.FavoriteActivity;
import com.suks.sittiporn.lslamic.main.place.PlaceActivity;
import com.suks.sittiporn.lslamic.main.time.TimeActivity;

public class HomeFragment extends Fragment implements
        SharedPreferences.OnSharedPreferenceChangeListener {

    Button bt_menu1;
    Button bt_menu2;
    Button bt_menu3;
    Button bt_menu4;
    Button bt_menu5;

    public interface Listener {
        void onChangeLanguage(String language);
    }

    private HomeFragment.Listener listener;


    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.init(view, savedInstanceState);
    }

    private void init(View view, Bundle savedInstanceState) {

        bt_menu1 = (Button) view.findViewById(R.id.bt_menu1);
        bt_menu2 = (Button) view.findViewById(R.id.bt_menu2);
        bt_menu3 = (Button) view.findViewById(R.id.bt_menu3);
        bt_menu4 = (Button) view.findViewById(R.id.bt_menu4);
        bt_menu5 = (Button) view.findViewById(R.id.bt_menu5);


//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//        btn_register = (Button) view.findViewById(R.id.btn_register);

        initinstanceState();

    }

    private void initinstanceState() {

        bt_menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MapsActivity.class);
                startActivity(intent);

            }
        });
        bt_menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TimeActivity.class);
                startActivity(intent);

            }
        });
        bt_menu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FavoriteActivity.class);
                startActivity(intent);

            }
        });
        bt_menu4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CheckInActivity.class);
                startActivity(intent);

            }
        });
        bt_menu5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CheckInListActivity.class);
                startActivity(intent);

            }
        });

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

    }
}
