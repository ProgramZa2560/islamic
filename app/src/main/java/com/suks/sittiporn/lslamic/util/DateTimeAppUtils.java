package com.suks.sittiporn.lslamic.util;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.util.Pair;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Weerawat on 18/6/2559.
 */
public class DateTimeAppUtils {
    private Context context;
    private Calendar calendar;
    private Listener listener;
    private FragmentManager fragmentManager;

    public interface Listener {
        void onSelectDate(String date);

        void onSelectTime(String time);
    }


    public DateTimeAppUtils(Context context, FragmentManager fragmentManager, Listener listener) {
        this.context = context;
        this.listener = listener;
        this.fragmentManager = fragmentManager;
        calendar = Calendar.getInstance();
    }

    public static Pair<Date, Date> getDateRange(int year, int month) {
        Calendar startDateCal = Calendar.getInstance();
        Calendar endDateCal = Calendar.getInstance();
        startDateCal.set(year, month - 1, 1, 0, 0, 0);
        endDateCal.set(year, month - 1, 1, 0, 0, 0);
        int endDay = endDateCal.getActualMaximum(Calendar.DAY_OF_MONTH);
        endDateCal.set(year, month - 1, endDay);
        Date endDate = new Date(endDateCal.getTimeInMillis());
        Date startDate = new Date(startDateCal.getTimeInMillis());

        Pair<Date, Date> pair = new Pair<>(startDate, endDate);
        return pair;
    }

    public void test() {
        int year = Integer.valueOf(DateTimeUtils.getEngDate(DateTimeUtils.yyyy));
        int month = Integer.valueOf(DateTimeUtils.getEngDate(DateTimeUtils.mm));
        Pair<Date, Date> datePair = getDateRange(year, month);
        Date start = datePair.first;
        Date end = datePair.second;
    }

    public void createDatePicketDialog()

    {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        String locale = Locale.getDefault().getLanguage();
        if (locale.equals("th")) {
            com.layernet.thaidatetimepicker.date.DatePickerDialog thDatePicker = com.layernet.thaidatetimepicker.date.DatePickerDialog.newInstance(new com.layernet.thaidatetimepicker.date.DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(com.layernet.thaidatetimepicker.date.DatePickerDialog view, int year1, int monthOfYear, int dayOfMonth) {
                    calendar.set(Calendar.YEAR, year1);
                    calendar.set(Calendar.MONTH, monthOfYear);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    String dateText = DateTimeUtils.toThaiDate(year1, monthOfYear, dayOfMonth);
                    listener.onSelectDate(dateText);

                }
            }, year, month, day);
            thDatePicker.show(fragmentManager, "");
        } else {
            DatePickerDialog enDatePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year1, int month1, int day1) {
                    String dateText = DateTimeUtils.getDateText(year1, month1, day1, DateTimeUtils.dd_MM_yyyy_2);
                    calendar.set(Calendar.YEAR, year1);
                    calendar.set(Calendar.MONTH, month1);
                    calendar.set(Calendar.DAY_OF_MONTH, day1);
                    listener.onSelectDate(dateText);
                }
            }, year, month, day);
            enDatePickerDialog.show();
        }
    }


    public String getDateText() {
        String locale = Locale.getDefault().getLanguage();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String dateText = "";
        if (locale.equals("th")) {
            dateText = DateTimeUtils.toThaiDate(year, month, day);
        } else {
            dateText = DateTimeUtils.getDateText(year, month, day, DateTimeUtils.dd_MM_yyyy_2);
        }
        return dateText;

    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void createTimePickerDialog() {

        final Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String hour1 = String.valueOf(hourOfDay);
                String minute1 = String.valueOf(minute);

                mcurrentTime.set(Calendar.YEAR, Calendar.MONTH, Calendar.DATE, hourOfDay, minute);
                mcurrentTime.getTime();
               String timeselect = DateTimeUtils.convertTime(mcurrentTime.getTime());

                //String timeselect = String.format("%02d:%02d", hour1, minute1);

                listener.onSelectTime(timeselect);


            }
        }, hour, minute, true);
        mTimePicker.show();

    }


}
