package com.suks.sittiporn.lslamic.main.checkin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.suks.sittiporn.lslamic.R;
import com.suks.sittiporn.lslamic.main.checkin.ui.main.CheckInFragment;

public class CheckInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_in_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, CheckInFragment.newInstance())
                    .commitNow();
        }
    }
}