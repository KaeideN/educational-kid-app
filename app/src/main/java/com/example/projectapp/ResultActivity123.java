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

public class ResultActivity123 extends AppCompatActivity {

        Button buttonHomePage , buttonTryAgain;
        TextView highestScoreText, personalScoreText,yourScore123;
        Context context;
        Resources resources;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result123);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        SharedPreferences sharedPreferences = getSharedPreferences("Settings", MODE_PRIVATE);
        String language = LocaleHelper.getLanguage(this);
        context = LocaleHelper.setLocale(ResultActivity123.this, language);
        resources = context.getResources();


        buttonHomePage = findViewById(R.id.homeButton123);
        buttonTryAgain = findViewById(R.id.tryAgain123);
        personalScoreText = findViewById(R.id.yourScore);
        yourScore123 = findViewById(R.id.yourScore123);

        yourScore123.setText(resources.getString(R.string.your_score));
        personalScoreText.setText(resources.getString(R.string.your_score));
        buttonTryAgain.setText(resources.getString(R.string.try_again_button));
        buttonHomePage.setText(resources.getString(R.string.home_button));

        // Retrieve user's score and highest score from Intent extras
        Intent intent = getIntent();
        int userScore = intent.getIntExtra("userScore", 0);

        personalScoreText.setText(""+userScore);

        buttonHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity123.this,MainActivity.class);
                startActivity(intent);
            }
        });
        buttonTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity123.this,MainActivity123Play.class);
                startActivity(intent);
            }
        });
    }
}