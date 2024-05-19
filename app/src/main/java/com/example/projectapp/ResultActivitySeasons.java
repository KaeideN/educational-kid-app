package com.example.projectapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;

public class ResultActivitySeasons extends AppCompatActivity {
        MaterialToolbar quizResult;
        MaterialCardView home,tryagain;
        TextView correctt,wrongt,resultinfo,resultscore,correctText,wrongText,tryAgainSeasonsText,returnHomepage;
        ImageView resultImage;
        private Context context;
        private Resources resources;
        private String language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result_seasons);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SharedPreferences sharedPreferences = getSharedPreferences("Settings", MODE_PRIVATE);
        language = LocaleHelper.getLanguage(this);
        context = LocaleHelper.setLocale(ResultActivitySeasons.this, language);
        resources = context.getResources();

        home = findViewById(R.id.returnHome);
        tryagain = findViewById(R.id.tryAgainSeasons);
        correctt = findViewById(R.id.correctScore);
        wrongt = findViewById(R.id.wrongScore);
        resultinfo = findViewById(R.id.resultInfo);
        resultscore = findViewById(R.id.resultScore);
        resultImage = findViewById(R.id.resultImage);
        correctText = findViewById(R.id.correctText);
        wrongText = findViewById(R.id.wrongText);
        tryAgainSeasonsText = findViewById(R.id.tryAgainSeasonsText);
        returnHomepage = findViewById(R.id.returnHomepage);
        quizResult = findViewById(R.id.quizResult);

        quizResult.setTitle(resources.getString(R.string.quiz_result));
        resultinfo.setText(resources.getString(R.string.quiz_result));
        correctText.setText(resources.getString(R.string.correct));
        wrongText.setText(resources.getString(R.string.wrong));
        tryAgainSeasonsText.setText(resources.getString(R.string.try_again_button));
        returnHomepage.setText(resources.getString(R.string.home_button));

        int correct = getIntent().getIntExtra("correct",0);
        int wrong = getIntent().getIntExtra("wrong",0);
        int score = correct*5;

        correctt.setText(""+correct);
        wrongt.setText(""+wrong);
        resultscore.setText(""+score);

        if(correct>=0&&correct<=2){
            resultinfo.setText(resources.getString(R.string.take_again));
            resultImage.setImageResource(R.drawable.sad_face);
        }else if (correct>=3&&correct<=4){
            resultinfo.setText(resources.getString(R.string.try_more));
            resultImage.setImageResource(R.drawable.neutral_face);
        }else if (correct==5){
            resultinfo.setText(resources.getString(R.string.pretty_good));
            resultImage.setImageResource(R.drawable.smiling_face);
        }else {
            resultinfo.setText(resources.getString(R.string.very_good));
            resultImage.setImageResource(R.drawable.smiling_face);
        }
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResultActivitySeasons.this,MainActivity.class));
                finish();
            }
        });
        tryagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResultActivitySeasons.this,MainActivitySeasonsPlay.class));
                finish();
            }
        });
    }

}