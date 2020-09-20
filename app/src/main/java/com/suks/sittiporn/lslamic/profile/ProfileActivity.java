package com.suks.sittiporn.lslamic.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.suks.sittiporn.lslamic.R;
import com.suks.sittiporn.lslamic.profile.ui.main.ProfileFragment;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, ProfileFragment.newInstance())
                    .commitNow();
        }
    }
}