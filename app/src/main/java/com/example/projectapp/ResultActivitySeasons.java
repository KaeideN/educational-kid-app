package com.example.projectapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.card.MaterialCardView;

public class ResultActivitySeasons extends AppCompatActivity {

        MaterialCardView home,tryagain;
        TextView correctt,wrongt,resultinfo,resultscore;
        ImageView resultImage;
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

        home = findViewById(R.id.returnHome);
        tryagain = findViewById(R.id.tryAgainSeasons);
        correctt = findViewById(R.id.correctScore);
        wrongt = findViewById(R.id.wrongScore);
        resultinfo = findViewById(R.id.resultInfo);
        resultscore = findViewById(R.id.resultScore);
        resultImage = findViewById(R.id.resultImage);

        int correct = getIntent().getIntExtra("correct",0);
        int wrong = getIntent().getIntExtra("wrong",0);
        int score = correct*5;

        correctt.setText(""+correct);
        wrongt.setText(""+wrong);
        resultscore.setText(""+score);

        if(correct>=0&&correct<=2){
            resultinfo.setText("You have to take the test again.");
            resultImage.setImageResource(R.drawable.sad_face);
        }else if (correct>=3&&correct<=4){
            resultinfo.setText("You have to try little more.");
            resultImage.setImageResource(R.drawable.neutral_face);
        }else if (correct==5){
            resultinfo.setText("You are pretty good.");
            resultImage.setImageResource(R.drawable.smiling_face);
        }else {
            resultinfo.setText("You are very good congratulations");
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