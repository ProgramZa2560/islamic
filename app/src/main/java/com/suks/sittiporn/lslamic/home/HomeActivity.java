package com.suks.sittiporn.lslamic.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.akexorcist.localizationactivity.core.LocalizationApplicationDelegate;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;

import com.suks.sittiporn.lslamic.R;
import com.suks.sittiporn.lslamic.about.AboutActivity;
import com.suks.sittiporn.lslamic.login.LoginActivity;
import com.suks.sittiporn.lslamic.realm.RealmUtil;
import com.suks.sittiporn.lslamic.util.BaseActivity;

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
        if (id == R.id.logout) {

            final AlertDialog.Builder adbConfirmExit = new AlertDialog.Builder(this);
            adbConfirmExit.create();
            adbConfirmExit.setTitle("ออกจากระบบ");
            adbConfirmExit.setMessage("คุณต้องการออกจากระบบใช่หรือไม่!");
            adbConfirmExit.setNegativeButton("ยกเลิก", null);
            adbConfirmExit.setPositiveButton("ออกจากระบบ", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    int count = RealmUtil.deleteRealm(HomeActivity.this);
                    if (count == 0) {
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                }
            });
            adbConfirmExit.create().show();
            return true;
        }

        if (id == R.id.info) {

            Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
            startActivity(intent);

            return true;
        }
//        if (id == R.id.setting) {
//
//            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
//            intent.addCategory(Intent.CATEGORY_HOME);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
//
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

}