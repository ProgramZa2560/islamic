package com.suks.sittiporn.lslamic.util;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.akexorcist.localizationactivity.ui.LocalizationActivity;

import java.util.Locale;


/**
 * Created by Semicolon07 on 4/17/2016 AD.
 */
public abstract class BaseActivity extends LocalizationActivity {

//    @Inject
//    protected Navigator navigator;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Locale locale = new Locale(getCurrentLanguage().getLanguage());
        Locale.setDefault(locale);
        if(portraitOnly())
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        else
            setDefaultOrientation();

//        this.getApplicationComponent().inject(this);

        /*this.overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);*/
    }

    protected boolean portraitOnly(){
        return true;
    }

    protected void setDefaultOrientation(){
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }

    protected void setUpButton(){
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }



    /**
     * Adds a {@link Fragment} to this activity's layout.
     *
     * @param containerViewId The container view to where add the fragment.
     * @param fragment The fragment to be added.
     */
    protected void addFragment(int containerViewId, Fragment fragment) {
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(containerViewId, fragment,"fragment");
        fragmentTransaction.commit();
    }

    protected void addFragment(int containerViewId, android.app.Fragment fragment){
        android.app.FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
        fragmentTransaction.add(containerViewId, fragment,"fragment");
        fragmentTransaction.commit();
    }



//    public ApplicationComponent getApplicationComponent() {
//        return ((AndroidApplication)getApplication()).getApplicationComponent();
//    }
//
//    public ActivityModule getActivityModule() {
//        return new ActivityModule(this);
//    }

    @Override
    public void finish() {
        super.finish();
        /*this.overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);*/
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        /*this.overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void showToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
