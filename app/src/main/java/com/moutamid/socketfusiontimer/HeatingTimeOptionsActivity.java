package com.moutamid.socketfusiontimer;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.fxn.stash.Stash;
import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.OnSeekChangeListener;
import com.warkiz.widget.SeekParams;

public class HeatingTimeOptionsActivity extends AppCompatActivity {
    private IndicatorSeekBar seekBar34, seekBar1, seekBar114, seekBar112, seekBar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heating_time_options);
        seekBar34 = findViewById(R.id.indicatorSeekBar34);
        seekBar1 = findViewById(R.id.indicatorSeekBar1);
        seekBar114 = findViewById(R.id.indicatorSeekBar114);
        seekBar112 = findViewById(R.id.indicatorSeekBar112);
        seekBar2 = findViewById(R.id.indicatorSeekBar2);
        loadSeekBarValues();
        setSeekBarListeners();
    }

    public void back(View view) {
        onBackPressed();
    }

    private void loadSeekBarValues() {
        seekBar34.setProgress(Stash.getInt("seekBar34", 11));
        seekBar1.setProgress(Stash.getInt("seekBar1", 16));
        seekBar114.setProgress(Stash.getInt("seekBar114", 19));
        seekBar112.setProgress(Stash.getInt("seekBar112", 22));
        seekBar2.setProgress(Stash.getInt("seekBar2", 26));
    }

    private void setSeekBarListeners() {

        seekBar34.setOnSeekChangeListener(new OnSeekChangeListener() {


            @Override
            public void onSeeking(SeekParams seekParams) {
                Stash.put("seekBar34", seekParams.progress);
                Log.d("seekbar", "1. "+ seekParams.progress);


            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {
            }
        });
        seekBar1.setOnSeekChangeListener(new OnSeekChangeListener() {


            @Override
            public void onSeeking(SeekParams seekParams) {
                Stash.put("seekBar1", seekParams.progress);
                Log.d("seekbar", "2. "+ seekParams.progress);

            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {
            }
        });
        seekBar114.setOnSeekChangeListener(new OnSeekChangeListener() {


            @Override
            public void onSeeking(SeekParams seekParams) {
                Stash.put("seekBar114", seekParams.progress);
                Log.d("seekbar", "3. "+ seekParams.progress);

            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {
            }
        });
        seekBar112.setOnSeekChangeListener(new OnSeekChangeListener() {


            @Override
            public void onSeeking(SeekParams seekParams) {
                Stash.put("seekBar112", seekParams.progress);
                Log.d("seekbar", "4. "+ seekParams.progress);

            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {
            }
        });
        seekBar2.setOnSeekChangeListener(new OnSeekChangeListener() {


            @Override
            public void onSeeking(SeekParams seekParams) {
                Stash.put("seekBar2", seekParams.progress);
                Log.d("seekbar", "5. "+ seekParams.progress);


            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {
            }
        });

    }
}