package com.inkhjw.roundprogressbar.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.inkhjw.roundprogressbar.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hjw
 */
public class ScrollPickView extends HorizontalScrollView {
    /**
     * 自定义属性
     */
    private float textSize;
    private int selectedTextColor;
    private int otherTextColor;
    private int rectangleColor;
    private float rectangleMaxHeight;

    private LinearLayout views;//容器veiw
    private float width;//宽度
    private float itemWidth = 0;//每一项的宽度
    int selectedPosition = 0;//选中项的索引
    private float beforeScrollX = 0;//滚动前的x坐标
    private float afterScrollX = 0;//滚动完成后的x坐标
    private float rectangleMaxProgress = 0;//矩形的最大进度基数

    List<Item> items;//数据

    public ScrollPickView(Context context) {
        this(context, null, 0);
    }

    public ScrollPickView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollPickView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
                R.styleable.ScrollPickView);

        textSize = mTypedArray.getDimension(R.styleable.ScrollPickView_textSize,
                16);
        selectedTextColor = mTypedArray.getColor(R.styleable.ScrollPickView_selectedTextColor,
                getResources().getColor(R.color.step_foot_time_rect_color));
        otherTextColor = mTypedArray.getColor(R.styleable.ScrollPickView_otherTextColor,
                getResources().getColor(R.color.text_color_hint));
        rectangleColor = mTypedArray.getColor(R.styleable.ScrollPickView_rectangleColor,
                getResources().getColor(R.color.step_foot_time_rect_color));
        rectangleMaxHeight = mTypedArray.getDimension(R.styleable.ScrollPickView_rectangleMaxHeight,
                100);
        mTypedArray.recycle();

        init(context);
    }

    private int getScrollLeftX() {
        return getScrollX();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.width = w;
        views.addView(addEmptyView(), 0);
        views.addView(addEmptyView(), items.size() + 1);
    }

    public void setItems(List<Item> list) {
        if (null == items) {
            items = new ArrayList<>();
        }
        items.clear();
        items.addAll(list);
        views.removeAllViews();

        for (int i = 0; i < items.size(); i++) {
            int foot = items.get(i).getFoot();
            if (rectangleMaxProgress < foot) {
                rectangleMaxProgress = foot;
            }
        }

        for (int i = 0; i < items.size(); i++) {
            views.addView(createView(i));
        }
    }

    private List<Item> getItems() {
        return items;
    }

    private void init(Context context) {
        views = new LinearLayout(context);
        views.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        views.setOrientation(LinearLayout.HORIZONTAL);
        this.addView(views);
        this.setHorizontalScrollBarEnabled(false);
    }

    private LinearLayout createView(final int position) {
        String text = items.get(position).getDate();
        int foot = items.get(position).getFoot();

        //item
        LinearLayout item = new LinearLayout(getContext());
        item.setOrientation(LinearLayout.VERTICAL);
        item.setGravity(Gravity.CENTER_HORIZONTAL);

        //矩形
        RelativeLayout rectLayout = new RelativeLayout(getContext());
        rectLayout.setGravity(Gravity.BOTTOM);
        RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams((int) textSize, (int) rectangleMaxHeight);
        rectLayout.setLayoutParams(relativeParams);
        TextView rectangle = new TextView(getContext());
        rectangle.setBackgroundColor(rectangleColor);

        //文本
        TextView textView = new TextView(getContext());
        textView.setSingleLine(true);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        textView.setPadding(5, 5, 5, 5);

        if (position == 0) {
            textView.setTextColor(selectedTextColor);
        } else {
            textView.setTextColor(otherTextColor);
        }
        textView.setText(text);
        textView.setGravity(Gravity.CENTER);

        if (itemWidth == 0) {
            itemWidth = (int) (textView.getPaint().measureText(text) + 20);
        }
        LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams((int) itemWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        item.setLayoutParams(itemParams);
        LinearLayout.LayoutParams rectangleParams = new LinearLayout.LayoutParams((int) textSize, (int) ((foot / rectangleMaxProgress) * rectangleMaxHeight));
        rectangle.setLayoutParams(rectangleParams);
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(textParams);

        rectLayout.addView(rectangle);
        item.addView(rectLayout);
        item.addView(textView);

        item.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ScrollPickView.this.smoothScrollTo((int) (position * itemWidth), 0);
                smoothScrollToItem();
            }
        });
        return item;
    }

    /**
     * 这里需要对水平方向进行处理,支 持padding
     */
    private LinearLayout addEmptyView() {
        LinearLayout linearLayout = new LinearLayout(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) ((width - itemWidth - getPaddingLeft() - getPaddingRight()) / 2), 1);
        linearLayout.setLayoutParams(params);
        return linearLayout;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
    }

    private float downScrollX;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downScrollX = getScrollLeftX();
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(downScrollX - getScrollLeftX()) > itemWidth) {
                    selectedPosition = (int) (getScrollLeftX() / itemWidth);
                    changeItemTextColor();
                }
                break;
            case MotionEvent.ACTION_UP:
                smoothScrollToItem();
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 手指离开频幕后滑动处理
     */
    private void smoothScrollToItem() {
        beforeScrollX = getScrollLeftX();
        postDelayed(runnable, 50);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            afterScrollX = getScrollLeftX();
            if (beforeScrollX == afterScrollX) {
                final int offset = (int) (beforeScrollX % itemWidth);
                if (offset > itemWidth / 2) {
                    ScrollPickView.this.post(new Runnable() {
                        @Override
                        public void run() {
                            ScrollPickView.this.smoothScrollTo((int) (afterScrollX - offset + itemWidth), 0);
                            selectedPosition = (int) (afterScrollX / itemWidth) + 1;
                            changeItemTextColor();
                            if (itemPickListener != null) {
                                itemPickListener.itemPick(selectedPosition);
                            }
                        }
                    });
                } else {
                    ScrollPickView.this.post(new Runnable() {
                        @Override
                        public void run() {
                            ScrollPickView.this.smoothScrollTo((int) (afterScrollX - offset), 0);
                            selectedPosition = (int) (beforeScrollX / itemWidth);
                            changeItemTextColor();
                            if (itemPickListener != null) {
                                itemPickListener.itemPick(selectedPosition);
                            }
                        }
                    });
                }
            } else {
                smoothScrollToItem();
            }
        }
    };

    private void changeItemTextColor() {
        int childCount = views.getChildCount();
        for (int i = 1; i < childCount - 1; i++) {
            LinearLayout item = (LinearLayout) views.getChildAt(i);
            if (null == item) {
                return;
            }
            TextView textView = (TextView) item.getChildAt(1);
            if (null == textView) {
                return;
            }
            if (selectedPosition + 1 == i) {
                textView.setTextColor(selectedTextColor);
            } else {
                textView.setTextColor(otherTextColor);
            }
        }
    }

    /**
     * 选中的监听
     */
    public interface ItemPickListener {
        void itemPick(int position);
    }

    ItemPickListener itemPickListener;

    public void setItemPickListener(ItemPickListener itemPickListener) {
        this.itemPickListener = itemPickListener;
    }

    public static final class Item {
        private int foot;
        private String date;

        public Item() {
        }

        public Item(int foot, String date) {
            this.foot = foot;
            this.date = date;
        }

        public int getFoot() {
            return foot;
        }

        public void setFoot(int foot) {
            this.foot = foot;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
}
