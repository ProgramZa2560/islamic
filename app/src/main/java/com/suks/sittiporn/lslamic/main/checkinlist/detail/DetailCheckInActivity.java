package com.suks.sittiporn.lslamic.main.checkinlist.detail;

import android.os.Bundle;
import android.view.MenuItem;

import com.suks.sittiporn.lslamic.R;
import com.suks.sittiporn.lslamic.main.checkinlist.detail.ui.main.DetailCheckInFragment;
import com.suks.sittiporn.lslamic.util.BaseActivity;

public class DetailCheckInActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_check_in_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, DetailCheckInFragment.newInstance())
                    .commitNow();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}