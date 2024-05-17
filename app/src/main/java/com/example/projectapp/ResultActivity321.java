package com.example.projectapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResultActivity321 extends AppCompatActivity {

    Button buttonHomePage , buttonTryAgain;
    TextView personalScoreText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result321);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        buttonHomePage = findViewById(R.id.homeButton123);
        buttonTryAgain = findViewById(R.id.tryAgain123);
        personalScoreText = findViewById(R.id.yourScore);

        // Retrieve user's score and highest score from Intent extras
        Intent intent = getIntent();
        int userScore = intent.getIntExtra("userScore", 0);

        personalScoreText.setText(""+userScore);

        buttonHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity321.this,MainActivity.class);
                startActivity(intent);
            }
        });
        buttonTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity321.this,MainActivity123Play.class);
                startActivity(intent);
            }
        });
    }
}