package com.moutamid.socketfusiontimer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.fxn.stash.Stash;

public class SettingsActivity extends AppCompatActivity {
    Button btnNext;
    private Switch switchCountDown, switchReadySound, switchCompletionSound, switchVoiceCommands, switchSoundOnly, switchVibrate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        btnNext = findViewById(R.id.btnNext);
        switchCountDown = findViewById(R.id.switchCountDown);
        switchReadySound = findViewById(R.id.switchReadySound);
        switchCompletionSound = findViewById(R.id.switchCompletionSound);
        switchVoiceCommands = findViewById(R.id.switchVoiceCommands);
        switchSoundOnly = findViewById(R.id.switchSoundOnly);
        switchVibrate = findViewById(R.id.switchVibrate);

        // Load the saved states
        loadSettings();

        // Set listeners to save the state when changed
        switchCountDown.setOnCheckedChangeListener(new SettingsChangeListener());
        switchReadySound.setOnCheckedChangeListener(new SettingsChangeListener());
        switchCompletionSound.setOnCheckedChangeListener(new SettingsChangeListener());
        switchVoiceCommands.setOnCheckedChangeListener(new SettingsChangeListener());
        switchSoundOnly.setOnCheckedChangeListener(new SettingsChangeListener());
        switchVibrate.setOnCheckedChangeListener(new SettingsChangeListener());
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingsActivity.this, MainActivity.class));

            }
        });
    }

    public void back(View view) {
        onBackPressed();
    }

    public void timer(View view) {
        startActivity(new Intent(SettingsActivity.this, HeatingTimeOptionsActivity.class));

    }
    private void loadSettings() {
        switchCountDown.setChecked(Stash.getBoolean("count_down", false));
        switchReadySound.setChecked(Stash.getBoolean("ready_sound", true));
        switchCompletionSound.setChecked(Stash.getBoolean("completion_sound", true));
        switchVoiceCommands.setChecked(Stash.getBoolean("voice_commands", false));
        switchSoundOnly.setChecked(Stash.getBoolean("sound_only", false));
        switchVibrate.setChecked(Stash.getBoolean("vibrate", false));
    }

    private class SettingsChangeListener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            int id = buttonView.getId();
            if (id == R.id.switchCountDown) {
                Stash.put("count_down", isChecked);
            } else if (id == R.id.switchReadySound) {
                Stash.put("ready_sound", isChecked);
            } else if (id == R.id.switchCompletionSound) {
                Stash.put("completion_sound", isChecked);
            } else if (id == R.id.switchVoiceCommands) {
                Stash.put("voice_commands", isChecked);
            } else if (id == R.id.switchSoundOnly) {
                Stash.put("sound_only", isChecked);
            } else if (id == R.id.switchVibrate) {
                Stash.put("vibrate", isChecked);
            }
        }
    }
}