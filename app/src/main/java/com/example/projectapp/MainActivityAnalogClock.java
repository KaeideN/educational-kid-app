package com.example.projectapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivityAnalogClock extends AppCompatActivity {
    private AnalogClockView analogClockView;
    private Button hourIncreaseButton, hourDecreaseButton, minuteIncreaseButton, minuteDecreaseButton;
    private TextView timeTextView, minute, hour;
    private Button buttonPlay;
    private Button backToMainFromAnalog;
    private Context context;
    private Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferred language
        SharedPreferences sharedPreferences = getSharedPreferences("Settings", MODE_PRIVATE);
        String language = LocaleHelper.getLanguage(this);
        context = LocaleHelper.setLocale(MainActivityAnalogClock.this, language);
        resources = context.getResources();

        setContentView(R.layout.activity_main_analog_clock);

        // Find views by their IDs
        analogClockView = findViewById(R.id.analogClockView);
        hourIncreaseButton = findViewById(R.id.increaseHour);
        hourDecreaseButton = findViewById(R.id.decreaseHour);
        minuteIncreaseButton = findViewById(R.id.increaseMinute);
        minuteDecreaseButton = findViewById(R.id.decreaseMinute);
        buttonPlay = findViewById(R.id.buttonPlay);
        timeTextView = findViewById(R.id.timeTextView);
        minute = findViewById(R.id.minute);
        hour = findViewById(R.id.hour);
        backToMainFromAnalog = findViewById(R.id.backToMainFromAnalog);

        // Set texts with the selected language
        hour.setText(resources.getString(R.string.hour));
        minute.setText(resources.getString(R.string.minute));
        buttonPlay.setText(resources.getString(R.string.play));
        backToMainFromAnalog.setText(resources.getString(R.string.back));

        // Set click listeners for the buttons
        hourIncreaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                analogClockView.increaseHour();
                updateTimeDisplay();
            }
        });

        hourDecreaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                analogClockView.decreaseHour();
                updateTimeDisplay();
            }
        });

        minuteIncreaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                analogClockView.increaseMinute();
                updateTimeDisplay();
            }
        });

        minuteDecreaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                analogClockView.decreaseMinute();
                updateTimeDisplay();
            }
        });

        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivityAnalogClock.this, MainActivityAnalogClockPlay.class);
                startActivity(intent);
            }
        });

        backToMainFromAnalog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivityAnalogClock.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Set initial time display
        updateTimeDisplay();
    }

    private void updateTimeDisplay() {
        // Get current time from AnalogClockView
        int hour = analogClockView.getHour();
        int minute = analogClockView.getMinute();

        // Format the time string (adjust as needed)
        String timeString = String.format("%02d:%02d", hour, minute);

        // Update the TextView
        timeTextView.setText(timeString);
    }
}
