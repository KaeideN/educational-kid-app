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

public class MainActivitySeasons extends AppCompatActivity {
    Button buttonPlay;
    private Context context;
    private Resources resources;
    TextView  summer,winter,fall,spring,summerText,springText,fallText,winterText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_seasons);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SharedPreferences sharedPreferences = getSharedPreferences("Settings", MODE_PRIVATE);
        String language = LocaleHelper.getLanguage(this);
        context = LocaleHelper.setLocale(MainActivitySeasons.this, language);
        resources = context.getResources();

        summer = findViewById(R.id.summer);
        spring = findViewById(R.id.spring);
        winter = findViewById(R.id.winter);
        fall   = findViewById(R.id.fall);
        summerText = findViewById(R.id.summerText);
        springText = findViewById(R.id.springText);
        winterText = findViewById(R.id.winterText);
        fallText   = findViewById(R.id.fallText);

        buttonPlay = findViewById(R.id.buttonPlay);
        buttonPlay.setText(resources.getString(R.string.play));

        summer.setText(resources.getString(R.string.summer));
        spring.setText(resources.getString(R.string.spring));
        winter.setText(resources.getString(R.string.winter));
        fall.setText(resources.getString(R.string.fall));

        summerText.setText(resources.getString(R.string.summer_Text));
        springText.setText(resources.getString(R.string.spring_Text));
        winterText.setText(resources.getString(R.string.winter_Text));
        fallText.setText(resources.getString(R.string.fall_Text));

        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivitySeasons.this,MainActivitySeasonsPlay.class) ;
                startActivity(intent);
            }
        });
    }
}