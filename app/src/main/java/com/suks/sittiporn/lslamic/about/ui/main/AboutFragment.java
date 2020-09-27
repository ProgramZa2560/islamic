package com.suks.sittiporn.lslamic.about.ui.main;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.suks.sittiporn.lslamic.R;
import com.suks.sittiporn.lslamic.main.time.ui.main.TimeFragment;
import com.suks.sittiporn.lslamic.realm.RealmUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class AboutFragment extends Fragment {



    public static AboutFragment newInstance() {
        return new AboutFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.about_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.initinstanceState(view, savedInstanceState);
    }

    private void initinstanceState(View view, Bundle savedInstanceState) {

//        ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.spin_kit);
//        Sprite doubleBounce = new DoubleBounce();
//        progressBar.setIndeterminateDrawable(doubleBounce);
//
//        llviewlooad = (LinearLayout) view.findViewById(R.id.llviewlooad);
//        switch1 = (Switch) view.findViewById(R.id.switch1);
//        switch2 = (Switch) view.findViewById(R.id.switch2);
//        switch3 = (Switch) view.findViewById(R.id.switch3);
//        switch4 = (Switch) view.findViewById(R.id.switch4);
//        switch5 = (Switch) view.findViewById(R.id.switch5);
//        llviewlooad = (LinearLayout) view.findViewById(R.id.llviewlooad);
//        llviewlooad.setVisibility(View.GONE);
//        initi();
//
//
//        id = RealmUtil.getMemberId();

        initi();


    }

    private void initi() {
//        llviewlooad.setVisibility(View.GONE);
//        Calendar c = Calendar.getInstance();
//        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
//        currentDate = date.format(c.getTime());
//        getTime(currentDate);

    }
}