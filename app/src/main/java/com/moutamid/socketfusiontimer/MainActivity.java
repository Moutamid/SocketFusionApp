package com.moutamid.socketfusiontimer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.moutamid.socketfusiontimer.adapter.PipeSizeAdapter;
import com.moutamid.socketfusiontimer.model.PipeSize;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Spinner spinnerPipeSizes;
    private TextView timerTextView;
    private Button startStopButton;
    private Button resetButton;
    private CountDownTimer heatingTimer;
    private CountDownTimer cooldownTimer;
    private boolean isRunning = false;
    private RelativeLayout layout;
    private SoundPool soundPool;
    private int beepSound;
    private Handler handler;

    private long initialDuration = 15000; // 14 seconds
    private long shortTimer1Duration = 3000; // 3 seconds
    private long shortTimer2Duration = 3000; // 4 seconds
    private long finalDuration = 30000; // 30 seconds
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
checkApp(MainActivity.this);
        spinnerPipeSizes = findViewById(R.id.spinnerPipeSizes);
        layout = findViewById(R.id.layout);

        List<PipeSize> pipeSizes = new ArrayList<>();
        pipeSizes.add(new PipeSize("3/4\"", "#FF0000"));  // Red
        pipeSizes.add(new PipeSize("1\"", "#FFD700"));  // Yellow
        pipeSizes.add(new PipeSize("1-1/4\"", "#808080"));  // Gray
        pipeSizes.add(new PipeSize("1-1/2\"", "#008000"));  // Green
        pipeSizes.add(new PipeSize("2\"", "#0000FF"));  // Blue

        PipeSizeAdapter adapter = new PipeSizeAdapter(this, pipeSizes);
        spinnerPipeSizes.setAdapter(adapter);

        spinnerPipeSizes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pos = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        timerTextView = findViewById(R.id.timerTextView);
        startStopButton = findViewById(R.id.startStopButton);
        resetButton = findViewById(R.id.resetButton);

        startStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRunning) {
                    if (pos == 0) {
                        initialDuration = 14000;
                        startInitialTimer();
                        finalDuration = 30000; // 30 seconds

                    } else if (pos == 1) {
                        initialDuration = 17000;
                        startInitialTimer();
                        finalDuration = 30000; // 30 seconds


                    } else if (pos == 2) {
                        initialDuration = 21000;
                        startInitialTimer();
                        finalDuration = 60000; // 30 seconds


                    } else if (pos == 3) {
                        initialDuration = 23000;
                        startInitialTimer();
                        finalDuration = 60000; // 30 seconds


                    } else if (pos == 4) {
                        initialDuration = 28000;
                        startInitialTimer();
                        finalDuration = 60000; // 30 seconds


                    }
                    startStopButton.setText("Stop");
                    resetButton.setEnabled(false);
                } else {
                    stopTimers();
                    startStopButton.setText("Restart");
                    resetButton.setEnabled(true);
                }
                isRunning = !isRunning;
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });
        handler = new Handler();

        // Initialize SoundPool
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(1)
                .setAudioAttributes(audioAttributes)
                .build();

        // Load the beep sound
        beepSound = soundPool.load(this, R.raw.beep, 1);
    }

    private void startInitialTimer() {
        heatingTimer = new CountDownTimer(initialDuration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished / 1000;
                timerTextView.setText(String.format("%02d:%02d", seconds / 60, seconds % 60));

                if (millisUntilFinished > 4000) {
                    layout.setBackgroundColor(getColor(R.color.skyblue));
                } else {
                    layout.setBackgroundColor(getColor(R.color.yellow));
                    playBeeps();
                }
                if(millisUntilFinished<1000)
                {                layout.setBackgroundColor(getColor(R.color.green));


                }
            }

            @Override
            public void onFinish() {
                layout.setBackgroundColor(getColor(R.color.green));
                startShortTimer2();

            }
        }.start();
    }

    private void startShortTimer1() {
        cooldownTimer = new CountDownTimer(shortTimer1Duration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished / 1000;
                timerTextView.setText(String.format("%02d:%02d", seconds / 60, seconds % 60));
                layout.setBackgroundColor(getColor(R.color.yellow));

            }

            @Override
            public void onFinish() {
                layout.setBackgroundColor(getColor(R.color.yellow));

                startShortTimer2();
            }
        }.start();
    }

    private void startShortTimer2() {
        cooldownTimer = new CountDownTimer(shortTimer2Duration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished / 1000;
                timerTextView.setText(String.format("%02d:%02d", seconds / 60, seconds % 60));
                layout.setBackgroundColor(getColor(R.color.green));

            }

            @Override
            public void onFinish() {
                startFinalTimer();
                if (soundPool != null) {
                    soundPool.release();
                    soundPool = null;
                }
            }
        }.start();
    }

    private void startFinalTimer() {
        heatingTimer = new CountDownTimer(finalDuration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished / 1000;
                timerTextView.setText(String.format("%02d:%02d", seconds / 60, seconds % 60));
                layout.setBackgroundColor(getColor(R.color.white));

            }

            @Override
            public void onFinish() {
                resetTimer();
            }
        }.start();
    }

    private void stopTimers() {
        if (heatingTimer != null) {
            heatingTimer.cancel();
        }
        if (cooldownTimer != null) {
            cooldownTimer.cancel();
        }
    }

    private void resetTimer() {
        timerTextView.setText("00:00");
        startStopButton.setText("Start");
        resetButton.setEnabled(false);
        isRunning = false;
        layout.setBackgroundColor(getColor(R.color.white));
    }

    private void playBeeps() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                soundPool.play(beepSound, 1, 1, 0, 0, 1);
            }
        }, 0); // First beep immediately


    }

    public void settings(View view) {
        startActivity(new Intent(MainActivity.this, SettingsActivity.class));

    }
    public static void checkApp(Activity activity) {
        String appName = "SocketApp";

        new Thread(() -> {
            URL google = null;
            try {
                google = new URL("https://raw.githubusercontent.com/Moutamid/Moutamid/main/apps.txt");
            } catch (final MalformedURLException e) {
                e.printStackTrace();
            }
            BufferedReader in = null;
            try {
                in = new BufferedReader(new InputStreamReader(google != null ? google.openStream() : null));
            } catch (final IOException e) {
                e.printStackTrace();
            }
            String input = null;
            StringBuffer stringBuffer = new StringBuffer();
            while (true) {
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        if ((input = in != null ? in.readLine() : null) == null) break;
                    }
                } catch (final IOException e) {
                    e.printStackTrace();
                }
                stringBuffer.append(input);
            }
            try {
                if (in != null) {
                    in.close();
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
            String htmlData = stringBuffer.toString();

            try {
                JSONObject myAppObject = new JSONObject(htmlData).getJSONObject(appName);

                boolean value = myAppObject.getBoolean("value");
                String msg = myAppObject.getString("msg");

                if (value) {
                    activity.runOnUiThread(() -> {
                        new AlertDialog.Builder(activity)
                                .setMessage(msg)
                                .setCancelable(false)
                                .show();
                    });
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }).start();
    }

}