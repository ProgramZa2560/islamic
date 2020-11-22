package com.suks.sittiporn.lslamic.profile.ui.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.suks.sittiporn.lslamic.R;
import com.suks.sittiporn.lslamic.home.HomeActivity;
import com.suks.sittiporn.lslamic.login.LoginActivity;
import com.suks.sittiporn.lslamic.realm.RealmUtil;


public class ProfileFragment extends Fragment {

    private ProfileViewModel mViewModel;

    ImageView bt_logout;
    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_fragment, container, false);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initinstanceState(View view, Bundle savedInstanceState) {

//        ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.spin_kit);
//        Sprite doubleBounce = new DoubleBounce();
//        progressBar.setIndeterminateDrawable(doubleBounce);
//
//        llviewlooad = (LinearLayout) view.findViewById(R.id.llviewlooad);
//        switch1 = (Switch) view.findViewById(R.id.switch1);
        bt_logout = (ImageView) view.findViewById(R.id.bt_logout);
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

        bt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder adbConfirmExit = new AlertDialog.Builder(getContext());
                adbConfirmExit.create();
                adbConfirmExit.setTitle("ออกจากระบบ");
                adbConfirmExit.setMessage("คุณต้องการออกจากระบบใช่หรือไม่!");
                adbConfirmExit.setNegativeButton("ยกเลิก", null);
                adbConfirmExit.setPositiveButton("ออกจากระบบ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        int count = RealmUtil.deleteRealm(getContext());
                        if (count == 0) {
                            Intent intent = new Intent(getContext(), LoginActivity.class);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                         getActivity().finish();
                        }
                    }
                });
                adbConfirmExit.create().show();
            }
        });
//        llviewlooad.setVisibility(View.GONE);
//        Calendar c = Calendar.getInstance();
//        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
//        currentDate = date.format(c.getTime());
//        getTime(currentDate);

    }
}