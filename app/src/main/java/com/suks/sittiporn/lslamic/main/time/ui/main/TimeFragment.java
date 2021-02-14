package com.suks.sittiporn.lslamic.main.time.ui.main;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.suks.sittiporn.lslamic.R;
import com.suks.sittiporn.lslamic.alram.alarmslist.AlarmsListViewModel;
import com.suks.sittiporn.lslamic.alram.createalarm.CreateAlarmViewModel;
import com.suks.sittiporn.lslamic.alram.data.Alarm;
import com.suks.sittiporn.lslamic.login.LoginActivity;
import com.suks.sittiporn.lslamic.manager.Retrofit2;
import com.suks.sittiporn.lslamic.manager.http.ApiService;
import com.suks.sittiporn.lslamic.model.reponse.GetTimeModel;
import com.suks.sittiporn.lslamic.model.reponse.SuccessModel;
import com.suks.sittiporn.lslamic.model.request.TimeStatusRequestModel;
import com.suks.sittiporn.lslamic.realm.AlertModelRealm;
import com.suks.sittiporn.lslamic.realm.DiskRealmDao;
import com.suks.sittiporn.lslamic.realm.RealmUtil;
import com.suks.sittiporn.lslamic.util.DateTimeAppUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;

import static io.realm.Realm.getDefaultInstance;


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

    EditText timeTextview;
    ImageView timeImageView;


    DateTimeAppUtils dateTimeAppUtils;
    TextView dateText;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener datedate;

    int alarmId = 0;
    DiskRealmDao diskRealmDao;

    int idStatus1;
    int idStatus2;
    int idStatus3;
    int idStatus4;
    int idStatus5;

    boolean count = false;

    private CreateAlarmViewModel createAlarmViewModel;

    private AlarmsListViewModel alarmsListViewModel;

    List<Alarm> alarmsList = new ArrayList<>();

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

        diskRealmDao = new DiskRealmDao(getContext());


        ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.spin_kit);
        Sprite doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);

        createAlarmViewModel = ViewModelProviders.of(this).get(CreateAlarmViewModel.class);
        alarmsListViewModel = ViewModelProviders.of(this).get(AlarmsListViewModel.class);
        alarmsListViewModel.getAlarmsLiveData().observe(getActivity(), new androidx.lifecycle.Observer<List<Alarm>>() {
            @Override
            public void onChanged(List<Alarm> alarms) {
                if (alarms != null) {
                    alarmsList = alarms;
                }
            }
        });

        llviewlooad = (LinearLayout) view.findViewById(R.id.llviewlooad);
        ic_date = (ImageView) view.findViewById(R.id.ic_date);
        dateText = (TextView) view.findViewById(R.id.date);
        switch1 = (Switch) view.findViewById(R.id.switch1);
        switch2 = (Switch) view.findViewById(R.id.switch2);
        switch3 = (Switch) view.findViewById(R.id.switch3);
        switch4 = (Switch) view.findViewById(R.id.switch4);
        switch5 = (Switch) view.findViewById(R.id.switch5);

        timeTextview = (EditText) view.findViewById(R.id.timeTextview);
        timeImageView = (ImageView) view.findViewById(R.id.timeImageView);


        timeImageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        timeTextview.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });


        llviewlooad.setVisibility(View.GONE);
//        initi();

        id = RealmUtil.getMemberId();
        initi();


    }

    private boolean checkDate(String currentDate, String dateText) {

//        String sDate1 = "2013-08-13";
//        String sDate2 = "2013-08-12";

        boolean chk = false;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date1 = df.parse(currentDate);
            Date date2 = df.parse(dateText);

            if (date1.compareTo(date2) > 0) {
                System.out.println(currentDate + " after date " + dateText);
                chk = true;
            } else if (date1.compareTo(date2) == 0) {
                chk = true;
                System.out.println(currentDate + " equal date " + dateText);
            } else {
                chk = false;
                System.out.println(currentDate + " before date " + dateText);

            }
        } catch (ParseException e) {

            e.printStackTrace();

        }

        return true;
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

                boolean cc = checkDate(currentDate, dateText.getText().toString());
                if (cc) {
                    count = false;



                    Realm realm = getDefaultInstance();
                    realm.where(AlertModelRealm.class).findAll();

//                    RealmUtil.addAlertAlarmRealm();

                    diskRealmDao.saveS(String.valueOf(idStatus1), "true", "1", getContext());

//                    TimeStatusRequestModel model = new TimeStatusRequestModel();
//                    model.setId(id);
//                    model.setTime_status1(check);
//                    model.setTime_status2(Integer.parseInt(statusTime2));
//                    model.setTime_status3(Integer.parseInt(statusTime3));
//                    model.setTime_status4(Integer.parseInt(statusTime4));
//                    model.setTime_status5(Integer.parseInt(statusTime5));
//
//                    updateTimeStatus(model);
                    if (switch1.isChecked()) {
                        for (Alarm alarm : alarmsList) {
                            if (alarm.getAlarmId() == idStatus1) {
                                alarm.schedule(getContext());
                                alarmsListViewModel.update(alarm);
                                count = true;
                                break;
                            }
                        }
                        if (!count)
                            scheduleAlarm(switch1String, switch1.isChecked(), "ซุบฮิ", 1);

                    } else {
                        diskRealmDao.saveS(String.valueOf(idStatus1), "false", "1", getContext());
                        for (Alarm alarm : alarmsList) {
                            if (alarm.getAlarmId() == idStatus1) {
                                alarm.cancelAlarm(getContext());
                                alarmsListViewModel.update(alarm);
                                break;

                            }
                        }
                    }
                } else {
                    final AlertDialog.Builder adbConfirmExit = new AlertDialog.Builder(getContext());
                    adbConfirmExit.create();
                    adbConfirmExit.setCancelable(true);
                    adbConfirmExit.setTitle("แจ้งเตือน");
                    adbConfirmExit.setMessage("ไม่สามารถตั้งเตือนล่วงหน้าได้!");
                    adbConfirmExit.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            switch1.setChecked(false);
                        }
                    });
                    adbConfirmExit.create().show();
                }
            }
        });

        switch2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean cc = checkDate(currentDate, dateText.getText().toString());
                if (cc) {
                    count = false;
                    diskRealmDao.saveS(String.valueOf(idStatus2), "true", "2", getContext());
//                    int check = (switch2.isChecked()) ? 1 : 0;
//                    int idStatus = idAlarm(switch2String, 2);
//                    TimeStatusRequestModel model = new TimeStatusRequestModel();
//                    model.setId(id);
//                    model.setTime_status1(Integer.parseInt(statusTime1));
//                    model.setTime_status2(0);
//                    model.setTime_status3(Integer.parseInt(statusTime3));
//                    model.setTime_status4(Integer.parseInt(statusTime4));
//                    model.setTime_status5(Integer.parseInt(statusTime5));
//
//                    updateTimeStatus(model);

                    if (switch2.isChecked()) {
                        for (Alarm alarm : alarmsList) {
                            if (alarm.getAlarmId() == idStatus2) {
                                alarm.schedule(getContext());
                                alarmsListViewModel.update(alarm);
                                count = true;
                                break;
                            }
                        }
                        if (!count)
                            scheduleAlarm(switch2String, switch2.isChecked(), "ดุอริ", 2);
                    } else {
                        diskRealmDao.saveS(String.valueOf(idStatus2), "false", "2", getContext());
                        for (Alarm alarm : alarmsList) {
                            if (alarm.getAlarmId() == idStatus2) {
                                alarm.cancelAlarm(getContext());
                                alarmsListViewModel.update(alarm);
                                break;

                            }
                        }
                    }
                } else {
                    final AlertDialog.Builder adbConfirmExit = new AlertDialog.Builder(getContext());
                    adbConfirmExit.create();
                    adbConfirmExit.setCancelable(true);
                    adbConfirmExit.setTitle("แจ้งเตือน");
                    adbConfirmExit.setMessage("ไม่สามารถตั้งเตือนล่วงหน้าได้!");
                    adbConfirmExit.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            switch2.setChecked(false);
                        }
                    });
                    adbConfirmExit.create().show();
                }
            }
        });

        switch3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean cc = checkDate(currentDate, dateText.getText().toString());
                if (cc) {
                    diskRealmDao.saveS(String.valueOf(idStatus3), "true", "3", getContext());
                    count = false;
//                    int idStatus = idAlarm(switch3String, 3);
//                    int check = (switch3.isChecked()) ? 1 : 0;
//                    TimeStatusRequestModel model = new TimeStatusRequestModel();
//                    model.setId(id);
//                    model.setTime_status1(Integer.parseInt(statusTime1));
//                    model.setTime_status2(Integer.parseInt(statusTime2));
//                    model.setTime_status3(check);
//                    model.setTime_status4(Integer.parseInt(statusTime4));
//                    model.setTime_status5(Integer.parseInt(statusTime5));
//
//                    updateTimeStatus(model);

                    if (switch3.isChecked()) {
                        for (Alarm alarm : alarmsList) {
                            if (alarm.getAlarmId() == idStatus3) {
                                alarm.schedule(getContext());
                                alarmsListViewModel.update(alarm);
                                count = true;
                                break;
                            }
                        }
                        if (!count)
                            scheduleAlarm(switch3String, switch3.isChecked(), "อัสริ", 3);
                    } else {
                        diskRealmDao.saveS(String.valueOf(idStatus3), "false", "3", getContext());
                        for (Alarm alarm : alarmsList) {
                            if (alarm.getAlarmId() == idStatus3) {
                                alarm.cancelAlarm(getContext());
                                alarmsListViewModel.update(alarm);
                                break;

                            }
                        }
                    }
                } else {
                    final AlertDialog.Builder adbConfirmExit = new AlertDialog.Builder(getContext());
                    adbConfirmExit.create();
                    adbConfirmExit.setCancelable(true);
                    adbConfirmExit.setTitle("แจ้งเตือน");
                    adbConfirmExit.setMessage("ไม่สามารถตั้งเตือนล่วงหน้าได้!");
                    adbConfirmExit.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            switch3.setChecked(false);
                        }
                    });
                    adbConfirmExit.create().show();
                }
            }
        });

        switch4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean cc = checkDate(currentDate, dateText.getText().toString());
                if (cc) {
                    count = false;
                    diskRealmDao.saveS(String.valueOf(idStatus4), "true", "4", getContext());
//                    int idStatus = idAlarm(switch4String, 4);
//                    int check = (switch4.isChecked()) ? 1 : 0;
//                    TimeStatusRequestModel model = new TimeStatusRequestModel();
//                    model.setId(id);
//                    model.setTime_status1(Integer.parseInt(statusTime1));
//                    model.setTime_status2(Integer.parseInt(statusTime2));
//                    model.setTime_status3(Integer.parseInt(statusTime3));
//                    model.setTime_status4(check);
//                    model.setTime_status5(Integer.parseInt(statusTime5));
//                    updateTimeStatus(model);

                    if (switch4.isChecked()) {
                        for (Alarm alarm : alarmsList) {
                            if (alarm.getAlarmId() == idStatus4) {
                                alarm.schedule(getContext());
                                alarmsListViewModel.update(alarm);
                                count = true;
                                break;
                            }
                        }
                        if (!count)
                            scheduleAlarm(switch4String, switch4.isChecked(), "มักริก", 4);

                    } else {
                        diskRealmDao.saveS(String.valueOf(idStatus4), "false", "4", getContext());
                        for (Alarm alarm : alarmsList) {
                            if (alarm.getAlarmId() == idStatus4) {
                                alarm.cancelAlarm(getContext());
                                alarmsListViewModel.update(alarm);
                                break;

                            }
                        }
                    }
                } else {
                    final AlertDialog.Builder adbConfirmExit = new AlertDialog.Builder(getContext());
                    adbConfirmExit.create();
                    adbConfirmExit.setCancelable(true);
                    adbConfirmExit.setTitle("แจ้งเตือน");
                    adbConfirmExit.setMessage("ไม่สามารถตั้งเตือนล่วงหน้าได้!");
                    adbConfirmExit.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            switch4.setChecked(false);
                        }
                    });
                    adbConfirmExit.create().show();
                }
            }
        });

        switch5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean cc = checkDate(currentDate, dateText.getText().toString());
                if (cc) {
                    count = false;
                    diskRealmDao.saveS(String.valueOf(idStatus5), "true", "5", getContext());
//                    int idStatus = idAlarm(switch5String, 5);
//                    int check = (switch5.isChecked()) ? 1 : 0;
//                    TimeStatusRequestModel model = new TimeStatusRequestModel();
//                    model.setId(id);
//                    model.setTime_status1(Integer.parseInt(statusTime1));
//                    model.setTime_status2(Integer.parseInt(statusTime2));
//                    model.setTime_status3(Integer.parseInt(statusTime3));
//                    model.setTime_status4(Integer.parseInt(statusTime4));
//                    model.setTime_status5(check);
//                    updateTimeStatus(model);

                    if (switch5.isChecked()) {
                        for (Alarm alarm : alarmsList) {
                            if (alarm.getAlarmId() == idStatus5) {
                                alarm.schedule(getContext());
                                alarmsListViewModel.update(alarm);
                                count = true;
                                break;
                            }
                        }
                        if (!count)
                            scheduleAlarm(switch5String, switch5.isChecked(), "อิชา", 5);
                    } else {
                        diskRealmDao.saveS(String.valueOf(idStatus5), "false", "5", getContext());
                        for (Alarm alarm : alarmsList) {
                            if (alarm.getAlarmId() == idStatus5) {
                                alarm.cancelAlarm(getContext());
                                alarmsListViewModel.update(alarm);
                                break;

                            }
                        }
                    }
                } else {

                    final AlertDialog.Builder adbConfirmExit = new AlertDialog.Builder(getContext());
                    adbConfirmExit.create();
                    adbConfirmExit.setCancelable(true);
                    adbConfirmExit.setTitle("แจ้งเตือน");
                    adbConfirmExit.setMessage("ไม่สามารถตั้งเตือนล่วงหน้าได้!");
                    adbConfirmExit.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            switch5.setChecked(false);
                        }
                    });
                    adbConfirmExit.create().show();
                }
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

        idStatus1 = idAlarm(switch1String, 1);
        idStatus2 = idAlarm(switch2String, 2);
        idStatus3 = idAlarm(switch3String, 3);
        idStatus4 = idAlarm(switch4String, 4);
        idStatus5 = idAlarm(switch5String, 5);
//        int check1 = (diskRealmDao.getIDSswitch(String.valueOf(idStatus1))) ? 1 : 0;
        switch1.setChecked(diskRealmDao.getIDSswitch(String.valueOf(idStatus1)));
        switch2.setChecked(diskRealmDao.getIDSswitch(String.valueOf(idStatus2)));
        switch3.setChecked(diskRealmDao.getIDSswitch(String.valueOf(idStatus3)));
        switch4.setChecked(diskRealmDao.getIDSswitch(String.valueOf(idStatus4)));
        switch5.setChecked(diskRealmDao.getIDSswitch(String.valueOf(idStatus5)));

//        if (statusTime1.equals("1")) switch1.setChecked(true);
//        else switch1.setChecked(false);
//        if (statusTime2.equals("1")) switch2.setChecked(true);
//        else switch2.setChecked(false);
//        if (statusTime3.equals("1")) switch3.setChecked(true);
//        else switch3.setChecked(false);
//        if (statusTime4.equals("1")) switch4.setChecked(true);
//        else switch4.setChecked(false);
//        if (statusTime5.equals("1")) switch5.setChecked(true);
//        else switch5.setChecked(false);

    }

    private String chknull(String chk) {
        if (chk == null) {
            return "0";
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

    private int idAlarm(String switch1String, int switchNum) {
        int HH = Integer.parseInt(switch1String.split(":")[0]);
        int mm = Integer.parseInt(switch1String.split(":")[1]);
        String dateString = dateText.getText().toString();

        alarmId = Integer.parseInt(dateString.split("-")[0] +
                dateString.split("-")[1] +
                dateString.split("-")[2] +
                switchNum);
        return alarmId;
    }

    private void scheduleAlarm(String switchString, boolean checked, String name, int switchNum) {
        int id = idAlarm(switchString, switchNum);
        int HH = Integer.parseInt(switchString.split(":")[0]);
        int mm = Integer.parseInt(switchString.split(":")[1]);
        Alarm alarm = new Alarm(
                id,
                HH,
                mm,
                name,
                System.currentTimeMillis(),
                true,
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