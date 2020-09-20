package com.suks.sittiporn.lslamic.about;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.suks.sittiporn.lslamic.R;
import com.suks.sittiporn.lslamic.about.ui.main.AboutFragment;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, AboutFragment.newInstance())
                    .commitNow();
        }
    }
}