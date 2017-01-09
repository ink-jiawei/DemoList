package com.hjw.testapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author hjw
 */

public class TMaterialCalendarView extends MaterialCalendarView {
    private DayViewDecorator decorator;

    public TMaterialCalendarView(Context context) {
        super(context);
        init();
    }

    public TMaterialCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        ArrayList<CalendarDay> today = new ArrayList<>();
        today.add(CalendarDay.today());
        decorator = new EventDecorator(today);
    }

    @Override
    public void clearSelection() {
        super.clearSelection();
        addTodayBackGround();
    }


    @Override
    protected void onDateClicked(@NonNull CalendarDay date, boolean nowSelected) {
        super.onDateClicked(date, nowSelected);
        addTodayBackGround();
    }

    private void addTodayBackGround() {
        setDateSelected(new Date(), true);

        List<CalendarDay> selectedDates = getSelectedDates();

        CalendarDay curDate = CalendarDay.today();
        CalendarDay selectedDate = selectedDates.get(0);

        if (curDate.getYear() == selectedDate.getYear()
                && curDate.getMonth() == selectedDate.getMonth()
                && curDate.getDay() == selectedDate.getDay()) {
            removeDecorators();
        } else {
            addDecorator(decorator);
        }
    }
}
