package com.suks.sittiporn.lslamic.main.checkinlist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.suks.sittiporn.lslamic.main.checkinlist.ui.main.CheckInListFragment;

public class CheckInListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_in_list_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, CheckInListFragment.newInstance())
                    .commitNow();
        }
    }
}