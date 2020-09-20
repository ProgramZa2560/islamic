package com.suks.sittiporn.lslamic.main.checkinlist.detail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.suks.sittiporn.lslamic.R;
import com.suks.sittiporn.lslamic.main.checkinlist.detail.ui.main.DetailCheckInFragment;

public class DetailCheckInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_check_in_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, DetailCheckInFragment.newInstance())
                    .commitNow();
        }
    }
}