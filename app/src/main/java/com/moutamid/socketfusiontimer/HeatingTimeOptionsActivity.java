package com.moutamid.socketfusiontimer;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.OnSeekChangeListener;
import com.warkiz.widget.SeekParams;

public class HeatingTimeOptionsActivity extends AppCompatActivity {
    private IndicatorSeekBar indicatorSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heating_time_options);
        indicatorSeekBar = findViewById(R.id.indicatorSeekBar);

        // Set initial value
        indicatorSeekBar.setProgress(9);

        indicatorSeekBar.setOnSeekChangeListener(new OnSeekChangeListener() {
            @Override
            public void onSeeking(SeekParams seekParams) {
            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {
            }

        });
    }

    public void back(View view) {
        onBackPressed();
    }
}