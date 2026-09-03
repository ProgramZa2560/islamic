package com.suks.sittiporn.lslamic.features.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.akexorcist.localizationactivity.core.LocalizationApplicationDelegate;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;

import com.suks.sittiporn.lslamic.R;
import com.suks.sittiporn.lslamic.features.about.AboutActivity;
import com.suks.sittiporn.lslamic.alarm.activities.MainAlramActivity;
import com.suks.sittiporn.lslamic.features.login.LoginActivity;
import com.suks.sittiporn.lslamic.features.profile.ProfileActivity;
import com.suks.sittiporn.lslamic.data.local.RealmUtil;
import com.suks.sittiporn.lslamic.core.util.BaseActivity;

public class HomeActivity extends BaseActivity {

    LocalizationApplicationDelegate localizationDelegate = new LocalizationApplicationDelegate(getApplication());

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        localizationDelegate.onConfigurationChanged(getApplication());
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, HomeFragment.newInstance())
                    .commitNow();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.info) {

            Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
            startActivity(intent);

            return true;
        }
        if (id == R.id.profile) {

//            Intent intent = new Intent(getApplicationContext(), MainAlramActivity.class);
//            intent.addCategory(Intent.CATEGORY_HOME);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);

            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}