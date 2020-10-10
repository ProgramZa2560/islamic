package com.suks.sittiporn.lslamic.main.checkin;


import android.os.Bundle;
import android.view.MenuItem;

import com.suks.sittiporn.lslamic.R;
import com.suks.sittiporn.lslamic.main.checkin.ui.main.CheckInFragment;
import com.suks.sittiporn.lslamic.util.BaseActivity;

public class CheckInActivity  extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_in_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, CheckInFragment.newInstance())
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