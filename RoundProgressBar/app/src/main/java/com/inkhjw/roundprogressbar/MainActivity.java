package com.inkhjw.roundprogressbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.inkhjw.roundprogressbar.widget.RoundProgressBar;
import com.inkhjw.roundprogressbar.widget.ScrollPickView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RoundProgressBar roundProgressBar;
    ScrollPickView scrollPickView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        roundProgressBar = (RoundProgressBar) findViewById(R.id.round_progress_bar);
        scrollPickView = (ScrollPickView) findViewById(R.id.scroll_pick_view);

        roundProgressBar.setMax(4000);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                roundProgressBar.setProgress(1800);
            }
        }).start();

        final List<ScrollPickView.Item> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new ScrollPickView.Item(400 * i, "12/1" + i));
        }
        scrollPickView.setItems(list);
        scrollPickView.setItemPickListener(new ScrollPickView.ItemPickListener() {
            @Override
            public void itemPick(int position) {
                roundProgressBar.setProgress(list.get(position).getFoot());
            }
        });
    }
}
