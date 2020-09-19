package com.suks.sittiporn.lslamic.main.time;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.suks.sittiporn.lslamic.R;
import com.suks.sittiporn.lslamic.main.time.ui.main.TimeFragment;

public class TimeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, TimeFragment.newInstance())
                    .commitNow();
        }
    }
}