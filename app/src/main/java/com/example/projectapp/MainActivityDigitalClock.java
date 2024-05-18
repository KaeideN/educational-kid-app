package com.example.projectapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivityDigitalClock extends AppCompatActivity {
    Button buttonPlay;
    Button backToMainFromDigital;
    TextView textView;
    private Context context;
    private Resources resources;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_digital_clock);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SharedPreferences sharedPreferences = getSharedPreferences("Settings", MODE_PRIVATE);
        String language = LocaleHelper.getLanguage(this);
        context = LocaleHelper.setLocale(MainActivityDigitalClock.this, language);
        resources = context.getResources();

        textView=findViewById(R.id.textView);
        textView.setText(resources.getString(R.string.digital_clock_tutorial));
        buttonPlay = findViewById(R.id.buttonPlay);
        buttonPlay.setText(resources.getString(R.string.play));
        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivityDigitalClock.this,MainActivityDigitalClockPlay.class);
                startActivity(intent);
            }
        });
        backToMainFromDigital=findViewById(R.id.backToMainFromDigital);
        backToMainFromDigital.setText(resources.getString(R.string.back));
        backToMainFromDigital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivityDigitalClock.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}