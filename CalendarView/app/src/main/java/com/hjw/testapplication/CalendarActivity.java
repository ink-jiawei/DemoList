package com.hjw.testapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.Calendar;
import java.util.Date;

public class CalendarActivity extends AppCompatActivity {
    TMaterialCalendarView mcv;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        mcv = (TMaterialCalendarView) findViewById(R.id.calendarView);

        mcv.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(2016, 12, 1))
                .setMaximumDate(CalendarDay.from(2017, 12, 1))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        mcv.setDateSelected(new Date(), true);
        mcv.setCurrentDate(new Date());

        mcv.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

            }
        });
    }
}
