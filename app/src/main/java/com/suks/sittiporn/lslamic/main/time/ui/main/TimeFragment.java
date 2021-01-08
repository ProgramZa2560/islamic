package com.suks.sittiporn.lslamic.main.time.ui.main;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.suks.sittiporn.lslamic.R;
import com.suks.sittiporn.lslamic.alram.createalarm.CreateAlarmViewModel;
import com.suks.sittiporn.lslamic.alram.data.Alarm;
import com.suks.sittiporn.lslamic.manager.Retrofit2;
import com.suks.sittiporn.lslamic.manager.http.ApiService;
import com.suks.sittiporn.lslamic.model.reponse.GetTimeModel;
import com.suks.sittiporn.lslamic.model.reponse.SuccessModel;
import com.suks.sittiporn.lslamic.model.request.TimeStatusRequestModel;
import com.suks.sittiporn.lslamic.realm.RealmUtil;
import com.suks.sittiporn.lslamic.util.DateTimeAppUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class TimeFragment extends Fragment {

    String id;
    String currentDate;
    LinearLayout llviewlooad;
    ImageView ic_date;
    Switch switch1;
    Switch switch2;
    Switch switch3;
    Switch switch4;
    Switch switch5;

    String switch1String;
    String switch2String;
    String switch3String;
    String switch4String;
    String switch5String;

    String statusTime1;
    String statusTime2;
    String statusTime3;
    String statusTime4;
    String statusTime5;

    DateTimeAppUtils dateTimeAppUtils;
    TextView dateText;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener datedate;

    private CreateAlarmViewModel createAlarmViewModel;

    public static TimeFragment newInstance() {
        return new TimeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.time_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.initinstanceState(view, savedInstanceState);
    }

    private void initinstanceState(View view, Bundle savedInstanceState) {

        ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.spin_kit);
        Sprite doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);

        createAlarmViewModel = ViewModelProviders.of(this).get(CreateAlarmViewModel.class);

        llviewlooad = (LinearLayout) view.findViewById(R.id.llviewlooad);
        ic_date = (ImageView) view.findViewById(R.id.ic_date);
        dateText = (TextView) view.findViewById(R.id.date);
        switch1 = (Switch) view.findViewById(R.id.switch1);
        switch2 = (Switch) view.findViewById(R.id.switch2);
        switch3 = (Switch) view.findViewById(R.id.switch3);
        switch4 = (Switch) view.findViewById(R.id.switch4);
        switch5 = (Switch) view.findViewById(R.id.switch5);
        llviewlooad.setVisibility(View.GONE);
//        initi();


        id = RealmUtil.getMemberId();

        initi();


    }

    private void initi() {


        myCalendar = Calendar.getInstance();
        llviewlooad.setVisibility(View.GONE);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat date2 = new SimpleDateFormat("yyyy-MM-dd");
        currentDate = date2.format(c.getTime());
        getTime(currentDate);
        dateText.setText(currentDate);


        datedate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        ic_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String datestart = DateTimeUtils.toFormat(dateTimeAppUtils.getCalendar(), DateTimeUtils.yyyy_MM_dd);
//                String datestart = dateTimeAppUtils.createDatePicketDialog();
                new DatePickerDialog(getContext(), datedate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();


            }
        });


        switch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int check = (switch1.isChecked()) ? 1 : 0;
                TimeStatusRequestModel model = new TimeStatusRequestModel();
                model.setId(id);
                model.setTime_status1(check);
                model.setTime_status2(Integer.parseInt(statusTime2));
                model.setTime_status3(Integer.parseInt(statusTime3));
                model.setTime_status4(Integer.parseInt(statusTime4));
                model.setTime_status5(Integer.parseInt(statusTime5));

                updateTimeStatus(model);
                scheduleAlarm(switch1String, switch1.isChecked(), "ซุบฮิ");
            }
        });

        switch2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int check = (switch2.isChecked()) ? 1 : 0;
                TimeStatusRequestModel model = new TimeStatusRequestModel();
                model.setId(id);
                model.setTime_status1(Integer.parseInt(statusTime1));
                model.setTime_status2(check);
                model.setTime_status3(Integer.parseInt(statusTime3));
                model.setTime_status4(Integer.parseInt(statusTime4));
                model.setTime_status5(Integer.parseInt(statusTime5));

                updateTimeStatus(model);
                scheduleAlarm("21:45", switch2.isChecked(), "ดุอริ");
            }
        });

        switch3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int check = (switch3.isChecked()) ? 1 : 0;
                TimeStatusRequestModel model = new TimeStatusRequestModel();
                model.setId(id);
                model.setTime_status1(Integer.parseInt(statusTime1));
                model.setTime_status2(Integer.parseInt(statusTime2));
                model.setTime_status3(check);
                model.setTime_status4(Integer.parseInt(statusTime4));
                model.setTime_status5(Integer.parseInt(statusTime5));


                updateTimeStatus(model);
                scheduleAlarm(switch3String, switch3.isChecked(), "อัสริ");
            }
        });

        switch4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int check = (switch4.isChecked()) ? 1 : 0;
                TimeStatusRequestModel model = new TimeStatusRequestModel();
                model.setId(id);
                model.setTime_status1(Integer.parseInt(statusTime1));
                model.setTime_status2(Integer.parseInt(statusTime2));
                model.setTime_status3(Integer.parseInt(statusTime3));
                model.setTime_status4(check);
                model.setTime_status5(Integer.parseInt(statusTime5));

                updateTimeStatus(model);
                scheduleAlarm(switch4String, switch4.isChecked(), "มักริก");
            }
        });

        switch5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int check = (switch5.isChecked()) ? 1 : 0;
                TimeStatusRequestModel model = new TimeStatusRequestModel();
                model.setId(id);
                model.setTime_status1(Integer.parseInt(statusTime1));
                model.setTime_status2(Integer.parseInt(statusTime2));
                model.setTime_status3(Integer.parseInt(statusTime3));
                model.setTime_status4(Integer.parseInt(statusTime4));
                model.setTime_status5(check);
                updateTimeStatus(model);
                scheduleAlarm(switch5String, switch5.isChecked(), "อิชา");
            }
        });
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dateText.setText(sdf.format(myCalendar.getTime()));
        getTime(dateText.getText().toString());
    }

    private void setString(GetTimeModel dataModel) {

        switch1String = dataModel.getData().get(0).getTime_name1();
        switch2String = dataModel.getData().get(0).getTime_name2();
        switch3String = dataModel.getData().get(0).getTime_name3();
        switch4String = dataModel.getData().get(0).getTime_name4();
        switch5String = dataModel.getData().get(0).getTime_name5();

        statusTime1 = chknull(dataModel.getData().get(0).getTime_status1());
        statusTime2 = chknull(dataModel.getData().get(0).getTime_status2());
        statusTime3 = chknull(dataModel.getData().get(0).getTime_status3());
        statusTime4 = chknull(dataModel.getData().get(0).getTime_status4());
        statusTime5 = chknull(dataModel.getData().get(0).getTime_status5());
        id = dataModel.getData().get(0).getTime_id();


        switch1.setText(switch1String);
        switch2.setText(switch2String);
        switch3.setText(switch3String);
        switch4.setText(switch4String);
        switch5.setText(switch5String);

        if (statusTime1.equals("1")) switch1.setChecked(true);
        else switch1.setChecked(false);
        if (statusTime2.equals("1")) switch2.setChecked(true);
        else switch2.setChecked(false);
        if (statusTime3.equals("1")) switch3.setChecked(true);
        else switch3.setChecked(false);
        if (statusTime4.equals("1")) switch4.setChecked(true);
        else switch4.setChecked(false);
        if (statusTime5.equals("1")) switch5.setChecked(true);
        else switch5.setChecked(false);

    }

    private String chknull(String chk) {
        if (chk == null){
            return "";
        } else return chk;

    }

    private void getTime(final String date) {
        llviewlooad.setVisibility(View.VISIBLE);
        ApiService apiService = Retrofit2.getApiService();
        Observable<GetTimeModel> observable = apiService.getTime(date);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<GetTimeModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GetTimeModel dataModel) {
                        setString(dataModel);
                        llviewlooad.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        llviewlooad.setVisibility(View.GONE);

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void updateTimeStatus(TimeStatusRequestModel date) {
        llviewlooad.setVisibility(View.VISIBLE);
        ApiService apiService = Retrofit2.getApiService();
        Observable<SuccessModel> observable = apiService.updateTimeStatus(date);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<SuccessModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SuccessModel dataModel) {
                        getTime(dateText.getText().toString());

                    }

                    @Override
                    public void onError(Throwable e) {
                        llviewlooad.setVisibility(View.GONE);

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    public void onResume() {
        super.onResume();
        initi();
    }

    private void scheduleAlarm(String switch1String, boolean checked, String name) {
        int alarmId = new Random().nextInt(Integer.MAX_VALUE);

        Alarm alarm = new Alarm(
                alarmId,
                Integer.parseInt(switch1String.split(":")[0]),
                Integer.parseInt(switch1String.split(":")[1]),
                name,
                System.currentTimeMillis(),
                checked,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false
        );

        createAlarmViewModel.insert(alarm);

        alarm.schedule(getContext());
    }

}