package com.suks.sittiporn.lslamic.main.favorite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.suks.sittiporn.lslamic.R;
import com.suks.sittiporn.lslamic.main.checkinlist.ui.main.CheckInListFragment;
import com.suks.sittiporn.lslamic.main.favorite.ui.main.FavoriteFragment;
import com.suks.sittiporn.lslamic.util.BaseActivity;

public class FavoriteActivity  extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, FavoriteFragment.newInstance())
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