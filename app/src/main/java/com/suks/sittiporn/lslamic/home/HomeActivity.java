package com.suks.sittiporn.lslamic.home;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;

import com.suks.sittiporn.lslamic.R;
import com.suks.sittiporn.lslamic.main.MapsActivity;
import com.suks.sittiporn.lslamic.main.checkin.CheckInActivity;
import com.suks.sittiporn.lslamic.main.checkinlist.CheckInListActivity;
import com.suks.sittiporn.lslamic.main.place.PlaceActivity;
import com.suks.sittiporn.lslamic.main.time.TimeActivity;

public class HomeActivity extends AppCompatActivity {

    Button bt_menu1;
    Button bt_menu2;
    Button bt_menu3;
    Button bt_menu4;
    Button bt_menu5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bt_menu1 = (Button) findViewById(R.id.bt_menu1);
        bt_menu2 = (Button) findViewById(R.id.bt_menu2);
        bt_menu3 = (Button) findViewById(R.id.bt_menu3);
        bt_menu4 = (Button) findViewById(R.id.bt_menu4);
        bt_menu5 = (Button) findViewById(R.id.bt_menu5);

        bt_menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);

            }
        });
        bt_menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TimeActivity.class);
                startActivity(intent);

            }
        });
        bt_menu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PlaceActivity.class);
                startActivity(intent);

            }
        });
        bt_menu4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CheckInActivity.class);
                startActivity(intent);

            }
        });
        bt_menu5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CheckInListActivity.class);
                startActivity(intent);

            }
        });
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}