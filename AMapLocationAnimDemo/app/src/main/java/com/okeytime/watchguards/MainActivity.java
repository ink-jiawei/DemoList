package com.okeytime.watchguards;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void anim1(View view) {
        Intent intent = new Intent(this, Anim1.class);
        startActivity(intent);
    }

    public void anim2(View view) {
        Intent intent = new Intent(this, Anim2.class);
        startActivity(intent);
    }

    public void anim3(View view) {
        Intent intent = new Intent(this, Anim3.class);
        startActivity(intent);
    }
}
