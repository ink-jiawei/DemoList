package com.hjw.canaldardemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;

import com.hjw.canaldardemo.widget.DateSelectCallback;
import com.hjw.canaldardemo.widget.DateView;

public class MainActivity extends AppCompatActivity {
    DateView dateView;
    TextView date;

    private Button up;
    private Button down;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        date = (TextView) findViewById(R.id.date);

        dateView = (DateView) findViewById(R.id.dateView);
        dateView.addDateSelectCallback(new DateSelectCallback() {
            @Override
            public void onDataChange(String changeData) {
                date.setText(changeData);
            }
        });

        date.setText(dateView.getShowDate());

        up = (Button) findViewById(R.id.up);
        down = (Button) findViewById(R.id.down);

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateView.upMonth();
            }
        });
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateView.nextMonth();
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateView.refreshDate(MainActivity.this);
            }
        });
    }
}
