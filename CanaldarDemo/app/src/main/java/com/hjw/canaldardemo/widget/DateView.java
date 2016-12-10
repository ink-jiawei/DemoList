package com.hjw.canaldardemo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author hjw
 * @deprecated 显示日历的视图
 */
public class DateView extends ViewGroup {
    Calendar calendar = Calendar.getInstance(Locale.getDefault());


    /**
     * 日历视图数据的绑定
     */
    DayBindListener listener;
    /**
     * 日期选择的回调接口, 数据的更新
     */
    DateSelectCallback callback;


    /**
     * 默认的日历视图的大小/设置为频幕的宽度
     */
    public float size = 300;

    /**
     * 测量后的日历视图的大小
     */
    public float measureHeight = 200;
    public float measureWidth = 300;
    /**
     * 测量后每一天的视图大小
     */
    public float dayHeight = 28.571428f;
    public float dayWidth = 42.857143f;

    private final float ROW = 7;//日历视图的行数<包括顶部一周的周期>
    private final float COL = 7;//日历视图的列数
    /**
     * 日历上显示的年、月、日、索引
     */
    private int year = 1970;
    private int month = 1;
    private int day = 1;
    private int index = 0;
    /**
     * 当前的年、月、日、当前日期所在的索引位置
     */
    private int curYear = 1970;
    private int curMonth = 1;
    private int curDay = 1;
    private int curIndex = 0;
    /**
     * 选中的年、月、日、选中日期所在的索引位置
     */
    private int selectedYear = 1970;
    private int selectedMonth = 1;
    private int selectedDay = 1;
    private int selectedItemIndex = 0;
    /**
     * 默认星期的item的背景颜色
     */
    private int weekItemColor = Color.parseColor("#55aa0000");
    /**
     * 默认当前日期item的背景颜色
     */
    private int curItemColor = Color.parseColor("#550000ee");
    /**
     * 默认选中日期item的背景颜色
     */
    private int selectedItemColor = Color.parseColor("#55ee0000");
    /**
     * 默认每一天的视图的背景颜色
     */
    private int dayItemColor = Color.parseColor("#33ff0000");
    /**
     * 默认非当前月item的背景颜色
     */
    private int otherMonthItemColor = Color.parseColor("#22ff0000");

    public DateView(Context context) {
        super(context);
        init(context);
    }

    public DateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public DateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context) {

        //初始化默认的日历
        calendar.setTime(new Date());
        curYear = calendar.get(Calendar.YEAR);
        curMonth = calendar.get(Calendar.MONTH);
        curDay = calendar.get(Calendar.DAY_OF_MONTH);

//        Log.e("test", "curYear:" + curYear);
//        Log.e("test", "curMonth:" + curMonth);
//        Log.e("test", "curDay:" + curDay);

        year = curYear;
        month = curMonth;
        day = curDay;

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        size = windowManager.getDefaultDisplay().getWidth();// 手机屏幕的宽度
        createDayView(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawDiv(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 测量子View的大小
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        measureHeight = measureHeight(heightMode, heightSize);
        measureWidth = measureWidth(widthMode, widthSize);

        dayHeight = measureHeight / COL;
        dayWidth = measureWidth / ROW;

        setMeasuredDimension((int) measureWidth, (int) measureHeight);
    }

    /**
     * 测量视图的高度
     *
     * @param widthMode
     * @param widthSize
     */
    private int measureWidth(int widthMode, int widthSize) {
        int widthS = 0;

        //如果是确定的大小
        if (widthMode == MeasureSpec.EXACTLY) {
            widthS = widthSize + getPaddingLeft() + getPaddingRight();
        }
        //如果是不确定的大小
        else {
            widthS = (int) size;
        }
        return widthS;
    }

    /**
     * 测量视图的高度
     *
     * @param heightMode
     * @param heightSize
     */
    private int measureHeight(int heightMode, int heightSize) {
        int heightS = 0;

        //如果是确定的大小
        if (heightMode == MeasureSpec.EXACTLY) {
            heightS = heightSize + getPaddingTop() + getPaddingBottom();
        }
        //如果是不确定的大小
        else {
            heightS = (int) (size);
        }
        return heightS;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {
            int row = i / (int) COL;//第几行
            int col = i % (int) ROW;//第几列
            View child = getChildAt(i);
            child.layout((int) (dayWidth * col), (int) (dayHeight * row), (int) (dayWidth * (col + 1)), (int) (dayHeight * (row + 1)));
        }
    }

    /**
     * 画日历视图的分割线
     */
    private void drawDiv(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setAntiAlias(true);

        //画行
        for (float i = 0; i < ROW; i++) {
            canvas.drawLine(0, (i / ROW) * measureHeight, measureWidth, (i / ROW) * measureHeight, paint);
        }
        //画列
        for (int i = 0; i < COL; i++) {
            canvas.drawLine((i / COL) * measureWidth, 0, (i / COL) * measureWidth, measureHeight, paint);
        }
    }

    /**
     * 要使用日历视图，就必须绑定此数据绑定监听<填充数据|刷新数据>
     *
     * @param listener
     */
    private void setDayListener(DayBindListener listener) {
        this.listener = listener;
    }

    /**
     * 提供给外部的回调,监听选中日期事件
     *
     * @param callback
     */
    public void addDateSelectCallback(DateSelectCallback callback) {
        this.callback = callback;
    }

    /**
     * 创建日历中每一天的view,初始化当前日期
     */
    public void createDayView(Context context) {
        int[] monthBound = countMonthBound();

        int dayWeek = 0;
        if (curDay != 1) {
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            //一号是星期几
            dayWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
            calendar.set(Calendar.DAY_OF_MONTH, curDay);
        } else {
            //一号是星期几
            dayWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        }

        curIndex = day + dayWeek - 1;//-1保证索引从0开始
        int n = 0;

        //初始化日历视图的第一个view是几号
        calendar.add(Calendar.DAY_OF_MONTH, -curIndex);

        setDayListener(new DayBindListenerImp(context));

        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                if (i == 0) {
                    TextView textView = listener.createDayView(this);
                    textView.setText(parseWeek(j));
                    textView.setTextSize(13);
                    DayContainerLayout dayLayout = addDayContainerLayout(context, textView);
                    dayLayout.setBackgroundColor(weekItemColor);
                    setAnim(dayLayout);
                    this.addView(dayLayout);
                } else {
                    TextView textView = listener.createDayView(this);
                    listener.bindDayView(textView, calendar);
                    DayContainerLayout dayLayout = addDayContainerLayout(context, textView);
                    otherMonthAddShade(monthBound, n, dayLayout);

                    if (curIndex == n) {
                        dayLayout.setBackgroundColor(curItemColor);
                    }
                    setAnim(dayLayout);
                    this.addView(dayLayout);
                    calendar.add(Calendar.DAY_OF_MONTH, +1);
                    n++;
                }
            }
        }
    }

    /**
     * 计算每一天的容器的layout
     *
     * @param viewIndex
     * @return
     */
    public Point countLayoutXY(int viewIndex) {
        Point point = new Point();

        int row = viewIndex / (int) COL;//第几行
        int col = viewIndex % (int) ROW;//第几列
        point.x = (int) (dayWidth * col);
        point.y = (int) (dayHeight * row);
        return point;
    }

    /**
     * 给TexView添加一层DayContainerLayout
     */
    public DayContainerLayout addDayContainerLayout(Context context, TextView textView) {
        DayContainerLayout dayLayout = new DayContainerLayout(context);
        dayLayout.setPadding(1, 1, 1, 1);
        dayLayout.setGravity(Gravity.CENTER);
        dayLayout.addView(textView);
        return dayLayout;
    }

    private String parseWeek(int n) {
        String result = null;
        switch (n) {
            case 0:
                result = "星期天";
                break;
            case 1:
                result = "星期一";
                break;
            case 2:
                result = "星期二";
                break;
            case 3:
                result = "星期三";
                break;
            case 4:
                result = "星期四";
                break;
            case 5:
                result = "星期五";
                break;
            case 6:
                result = "星期六";
                break;
            default:
                result = "无效";
                break;
        }
        return result;
    }

    /**
     * 上一月的视图
     */
    public void upMonth() {
        //重置上一个日历视图的1号的背景颜色
        getChildAt(index + 7).setBackgroundColor(dayItemColor);
        Log.e("test", "index=" + index);

        month--;
        if (month < 0) {
            month = 11;
            year--;
        }
        /**
         * 判断接下来显示的视图选中项有哪些,选中、当前日期、1号
         */
        if (year == curYear && month == curMonth) {
            day = curDay;
        } else {
            if (selectedYear == curYear && selectedMonth == curMonth) {
                day = 1;
            } else if (year == selectedYear && month == selectedMonth) {
                day = selectedDay;
            } else {
                day = 1;
            }
        }


        //初始化默认的日历
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        countMonth(year, month);
    }

    /**
     * 下一月的视图
     */
    public void nextMonth() {
        //重置上一个日历视图的1号的背景颜色
        getChildAt(index + 7).setBackgroundColor(dayItemColor);
        Log.e("test", "index=" + index);

        month++;
        if (month > 11) {
            month = 0;
            year++;
        }
        /**
         * 判断接下来显示的视图选中项有哪些,选中、当前日期、1号
         */
        if (year == curYear && month == curMonth) {
            day = curDay;
        } else {
            if (selectedYear == curYear && selectedMonth == curMonth) {
                day = 1;
            } else if (year == selectedYear && month == selectedMonth) {
                day = selectedDay;
            } else {
                day = 1;
            }
        }
        //初始化默认的日历
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        countMonth(year, month);
    }

    /**
     * 计算出一个月中的每一天的位置和是几号
     */
    public void countMonth(int year, int month) {
//        this.removeAllViews();
//        createDayView(getContext());
        callback.onDataChange(getShowDate());
//        postInvalidate();

        int[] monthBound = countMonthBound();

        index = day + getWeekDay() - 1;//-1保证索引从0开始
        //初始化日历视图的第一个view是几号
        calendar.add(Calendar.DAY_OF_MONTH, -index);

        int childCount = getChildCount();
        for (int i = 7; i < childCount; i++) {
            DayContainerLayout dayLayout = (DayContainerLayout) getChildAt(i);
            TextView textView = (TextView) dayLayout.getChildAt(0);

            otherMonthAddShade(monthBound, i - 7, dayLayout);

            listener.bindDayView(textView, calendar);
            calendar.add(Calendar.DAY_OF_MONTH, +1);

            //重置当前日期的背景颜色
            if (i - 7 == curIndex) {
                if (year == curYear && month == curMonth) {
                    dayLayout.setBackgroundColor(curItemColor);
                }
            }

            //重置上一次选中位置索引的item的背景颜色
            if (i - 7 == selectedItemIndex) {
                if (year == selectedYear && month == selectedMonth) {
                    dayLayout.setBackgroundColor(selectedItemColor);
                }
            }

            //当前月如果没有选中项，默认为1号
            if (selectedItemIndex != 0) {
                if (!(year == selectedYear && month == selectedMonth)) {
                    if (day == 1 && i == index + 7) {
                        dayLayout.setBackgroundColor(selectedItemColor);
                    }
                }
            } else {
                if (day == 1 && i == index + 7) {
                    dayLayout.setBackgroundColor(selectedItemColor);
                }
            }
        }
    }

    /**
     * 获取每月的一号是星期几
     */
    public int getWeekDay() {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        int dayWeek = 0;
        if (day != 1) {
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            //一号是星期几
            dayWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
            calendar.set(Calendar.DAY_OF_MONTH, day);
        } else {
            //一号是星期几
            dayWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        }

        return dayWeek;
    }

    /**
     * 计算当前月份的日期索引范围
     */
    public int[] countMonthBound() {
        int[] monthBound = new int[2];

        int startIndex = getWeekDay();
        int endIndex = calendar.getActualMaximum(Calendar.DATE) + startIndex;

        monthBound[0] = startIndex;
        monthBound[1] = endIndex;
        return monthBound;
    }

    /**
     * 设置当前月份、上一月剩余、下一月剩余的背景
     */
    public void otherMonthAddShade(int[] bounds, int index, DayContainerLayout dayLayout) {
        if (index >= bounds[0] && index < bounds[1]) {
            dayLayout.setBackgroundColor(dayItemColor);
            Log.e("test", "index=" + index);
        } else {
            dayLayout.setBackgroundColor(otherMonthItemColor);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    float downX = 0;
    float downY = 0;
    float upX = 0;
    float upY = 0;

    /**
     * 处理触摸事件
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                downX = event.getX();
                downY = event.getY();
                break;

            case MotionEvent.ACTION_UP:
                upX = event.getX();
                upY = event.getY();

                float xDiscount = upX - downX;
                float yDiscount = upY - downY;
                if (xDiscount > 0) {
                    if (xDiscount > size / 3 && yDiscount < size / 2) {
                        upMonth();
                    } else {
                        selelctDay(downX, downY);
                    }
                } else if (xDiscount < 0) {
                    if (Math.abs(xDiscount) > size / 3 && yDiscount < size / 2) {
                        nextMonth();
                    } else {
                        selelctDay(downX, downY);
                    }
                } else {
                    selelctDay(downX, downY);
                }

                break;
        }
        return true;
    }

    /**
     * 选择日期的触摸处理
     *
     * @param clickX
     * @param clickY
     */
    private void selelctDay(float clickX, float clickY) {
        int[] bounds = countMonthBound();

        int childCount = getChildCount();
        for (int i = 7; i < childCount; i++) {
            DayContainerLayout dayLayout = (DayContainerLayout) getChildAt(i);

            float x = dayLayout.getX();
            float y = dayLayout.getY();

            if (clickX > x && clickX < x + dayWidth && clickY > y && clickY < y + dayHeight) {
                dayLayout.setBackgroundColor(selectedItemColor);
                selectedItemIndex = i - 7;

                selectedYear = year;
                selectedMonth = month;
                selectedDay = Integer.parseInt(((TextView) (dayLayout.getChildAt(0))).getText().toString());
                day = selectedDay;

                callback.onDataChange(getShowDate());
            } else if (i - 7 == curIndex && year == curYear && month == curMonth) {
                dayLayout.setBackgroundColor(curItemColor);
            } else {
                otherMonthAddShade(bounds, i - 7, dayLayout);
            }
        }
    }

    /**
     * 刷新日历，更新最新时间
     */
    public void refreshDate(Context context) {
        this.removeAllViews();
        init(context);
        callback.onDataChange(getShowDate());
        postInvalidate();
    }

    public void setAnim(View view) {
        Animation anim = new TranslateAnimation(-size, 0, 0, 0);
        anim.setDuration(1000);
        anim.setFillAfter(true);
        view.setAnimation(anim);
    }

    /**
     * 获得当前日历视图的年份
     *
     * @return year
     */

    public int getYear() {
        return year;
    }

    /**
     * 设置当前日历视图的年份
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * 获得当前日历视图的月份
     *
     * @return month
     */
    public int getMonth() {
        return month;
    }

    /**
     * 设置当前日历视图的月份
     */
    public void setMonth(int month) {
        this.month = month;
    }

    /**
     * 获得当前日历视图是几号
     *
     * @return day
     */
    public int getDay() {
        return day;
    }

    /**
     * 设置当前日历视图是几号
     */
    public void setDay(int day) {
        this.day = day;
    }

    /**
     * 获得日历视图显示的日期
     */
    public String getShowDate() {
        return getYear() + "-" + (getMonth() + 1) + "-" + getDay();
    }
}
