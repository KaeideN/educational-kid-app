package com.example.projectapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import android.os.Handler;
import java.util.Random;


import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity123Play extends AppCompatActivity {

    private TextView textView;
    private TextView highestScoreTextView;
    private GridLayout gridLayout;
    private ArrayList<Integer> numbers;
    private ArrayList<Integer> shownNumbers;
    private int currentNumberIndex;
    private int level;
    private int highestScore;
    private int n; // Declare n as a class-level variable
    Context context;
    Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity123_play);

        SharedPreferences sharedPreferences = getSharedPreferences("Settings", MODE_PRIVATE);
        String language = LocaleHelper.getLanguage(this);
        context = LocaleHelper.setLocale(MainActivity123Play.this, language);
        resources = context.getResources();


        textView = findViewById(R.id.textView);
        gridLayout = findViewById(R.id.gridLayout);
        highestScoreTextView = findViewById(R.id.highestScoreTextView); // Initialize highestScoreTextView


        highestScore = 0;
        numbers = new ArrayList<>();
        shownNumbers = new ArrayList<>(); // Initialize shownNumbers
        n = 0; // Initialize n
        for (int i = 1; i <= 9; i++) {
            numbers.add(i);
        }

        startGame();
    }

    private int numNumbersToShow;

    private void startGame() {
        level = 1;
        numNumbersToShow = 2;
        currentNumberIndex = 0;
        textView.setText("Level " + level);

        Collections.shuffle(numbers);
        showNumbers();
    }

    private void showNumbers() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (currentNumberIndex < numNumbersToShow) {
                    int number = numbers.get(currentNumberIndex);
                    textView.setText(String.valueOf(number));
                    currentNumberIndex++;
                    shownNumbers.add(number); // Add the shown number to shownNumbers
                    showNumbers();
                } else {
                    textView.setText(resources.getString(R.string.type_beginnig_to_end));
                }
            }
        }, 1500);
    }

    public void onNumberButtonClick(View view) {
        Button button = (Button) view;
        int number = Integer.parseInt(button.getText().toString());

        // Check if the clicked number matches the current expected number in the sequence
        if (number == shownNumbers.get(n) && n < numNumbersToShow) {
            n++; // Move to the next expected number

            // Check if the user has clicked all numbers in the sequence
            button.setBackgroundColor(Color.GREEN);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    button.setBackgroundColor(R.drawable.button_background);
                   // button.setBackgroundResource(R.drawable.right_answer_bg);

                }
            }, 1000);
            if (n == numNumbersToShow) {
                // Proceed to the next level
                level++;
                updateHighestScore(level);
                numNumbersToShow++;
                n = 0; // Reset n
                textView.setText("Level " + level);
                Collections.shuffle(numbers);
                shownNumbers.clear(); // Clear shownNumbers for the next level
                currentNumberIndex = 0;
                showNumbers();
            }
        } else {
            // Change the button color to red for 1 second
            button.setBackgroundColor(Color.RED);
            //button.setBackgroundResource(R.drawable.wrong_answer);;

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                   // button.setBackgroundColor(Color.TRANSPARENT);
                }
            }, 1000);

            handleWrongButtonClick();
            }
    }

    private void handleWrongButtonClick() {
        Intent intent = new Intent(MainActivity123Play.this, ResultActivity123.class);
        intent.putExtra("userScore", level); // Pass the user's score to the result activity
        intent.putExtra("highestScore", highestScore); // Pass the highest score to the result activity
        startActivity(intent);
        finish();

    }

    private void updateHighestScore(int score) {
        // Update highest score if the current level is higher
        if (score > highestScore) {
            highestScore = score;
            highestScoreTextView.setText("Highest Score: " +highestScore);
        }
    }
}




