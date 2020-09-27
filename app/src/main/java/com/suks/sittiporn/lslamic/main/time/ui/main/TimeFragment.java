package com.suks.sittiporn.lslamic.main.time.ui.main;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

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
import com.suks.sittiporn.lslamic.manager.Retrofit2;
import com.suks.sittiporn.lslamic.manager.http.ApiService;
import com.suks.sittiporn.lslamic.model.reponse.GetTimeModel;
import com.suks.sittiporn.lslamic.realm.RealmUtil;
import com.suks.sittiporn.lslamic.util.DateTimeAppUtils;
import com.suks.sittiporn.lslamic.util.DateTimeUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
    DateTimeAppUtils dateTimeAppUtils;
    TextView dateText;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener datedate;

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.initinstanceState(view, savedInstanceState);
    }

    private void initinstanceState(View view, Bundle savedInstanceState) {

        ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.spin_kit);
        Sprite doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);

        llviewlooad = (LinearLayout) view.findViewById(R.id.llviewlooad);
        ic_date = (ImageView) view.findViewById(R.id.ic_date);
        dateText = (TextView) view.findViewById(R.id.date);
        switch1 = (Switch) view.findViewById(R.id.switch1);
        switch2 = (Switch) view.findViewById(R.id.switch2);
        switch3 = (Switch) view.findViewById(R.id.switch3);
        switch4 = (Switch) view.findViewById(R.id.switch4);
        switch5 = (Switch) view.findViewById(R.id.switch5);
        llviewlooad = (LinearLayout) view.findViewById(R.id.llviewlooad);
        llviewlooad.setVisibility(View.GONE);
        initi();


        id = RealmUtil.getMemberId();

        initi();



    }
    private void initi(){
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
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dateText.setText(sdf.format(myCalendar.getTime()));
        getTime(dateText.getText().toString());
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

                        switch1.setText(dataModel.getData().get(0).getTime_name1());
                        switch2.setText(dataModel.getData().get(0).getTime_name2());
                        switch3.setText(dataModel.getData().get(0).getTime_name3());
                        switch4.setText(dataModel.getData().get(0).getTime_name4());
                        switch5.setText(dataModel.getData().get(0).getTime_name5());

                        llviewlooad.setVisibility(View.GONE);
//                        if (dataModel.getResult().equals("true")){
//                            llviewlooad.setVisibility(View.GONE);
//                            status = dataModel.getGetStatusDataReponseModel().get(0).getExamination_status_status();
//                            if (status.equals("2")){
//                                Intent intent = new Intent(getContext(), ExaminationSetActivity.class);
//                                intent.putExtra("examination_head_id", examination_status_examination_id);
//                                intent.putExtra("examination_id", dataModel.getGetStatusDataReponseModel().get(0).getExamination_status_id());
//                                intent.addCategory(Intent.CATEGORY_HOME);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                startActivity(intent);
//                            }else if(status.equals("1")){
//                                Intent intent = new Intent(getContext(), ExaminationSetActivity.class);
//                                intent.putExtra("status", status);
//                                intent.putExtra("examination_id", dataModel.getGetStatusDataReponseModel().get(0).getExamination_status_id());
//                                intent.putExtra("examination_head_id", examination_head_id);
//                                intent.addCategory(Intent.CATEGORY_HOME);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                startActivity(intent);
//                            }else {
//                                Intent intent = new Intent(getContext(), ExaminationSetActivity.class);
//                                intent.putExtra("status", status);
//                                intent.putExtra("examination_id", dataModel.getGetStatusDataReponseModel().get(0).getExamination_status_id());
//                                intent.putExtra("examination_head_id", examination_head_id);
//                                intent.addCategory(Intent.CATEGORY_HOME);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                startActivity(intent);
//                            }
//
//                        }else {
//                            CreateStatusPaymentRequestModel requestModel = new CreateStatusPaymentRequestModel();
//                            requestModel.setExamination_status_head_id(examination_head_id);
//                            requestModel.setExamination_status_examination_id(examination_status_examination_id);
//                            requestModel.setExamination_status_user_id(user_id);
//                            requestModel.setExamination_status_user(user);
//                            requestModel.setExamination_status_status("0");
//                            requestModel.setExamination_status_slip("");
//                            createStatus(requestModel);
//                        }
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

}