package com.example.projectapp;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity123_play);

        textView = findViewById(R.id.textView);
        gridLayout = findViewById(R.id.gridLayout);

        highestScore = 0;
        numbers = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            numbers.add(i);
        }

        startGame();
    }

    private int numNumbersToShow; // Her seviyede gösterilecek rakam sayısı

    private void startGame() {
        level = 1;
        numNumbersToShow = 2; // İlk seviyede 2 rakam gösterilecek
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
                    showNumbers();
                    shownNumbers = new ArrayList<>();
                    shownNumbers.add(number);

                } else {
                    textView.setText("Type the digits from beginning to end.");
                }
            }
        }, 1500);
    }
    /*
    public void onNumberButtonClick(View view) {
        Button button = (Button) view;
        int number = Integer.parseInt(button.getText().toString());

        if (number == numbers.get(currentNumberIndex - 1)) {
            if (currentNumberIndex == numNumbersToShow) { // Tüm rakamlar doğru basıldıysa
                level++;
                numNumbersToShow++; // Her seviyede bir ekstra rakam göster
                currentNumberIndex = 0;
                textView.setText("Level " + level);
                Collections.shuffle(numbers);
                showNumbers();
            }
        } else {
            Toast.makeText(this, "Wrong number! Try again.", Toast.LENGTH_SHORT).show();
        }
    }
     */

    public void onNumberButtonClick(View view) {
        Button button = (Button) view;
        int number = Integer.parseInt(button.getText().toString());
        int n = 0;
        // Check if the clicked number matches the current expected number in the sequence
        if (number == shownNumbers.get(n)  && n<numNumbersToShow) {
            n++; // Move to the next expected number

            // Check if the user has clicked all numbers in the sequence
            if (n == numNumbersToShow) {
                // Proceed to the next level
                level++;
                numNumbersToShow++; // Increase the number of numbers to show in the next level
                n = 0; // Reset the expected number index
                textView.setText("Level " + level);
                Collections.shuffle(numbers); // Shuffle the numbers for the next level
                showNumbers(); // Show the numbers for the next level
            }
        } else {
            // Wrong button clicked, display a message or take appropriate action
            Toast.makeText(this, "Wrong button! Try again.", Toast.LENGTH_SHORT).show();
        }
    }


    private void updateHighestScore() {
        // Update highest score if the current level is higher
        if (level > highestScore) {
            highestScore = level;
            highestScoreTextView.setText("Highest Score: " + highestScore);
        }
    }

/*
    private void updateHighestScore(int score) {
        // Eğer yeni skor, mevcut en yüksek skordan büyükse, Highest Score'u güncelle
        if (score > highestScore) {
            highestScore = score;
            highestScoreTextView.setText("Highest Score: " + highestScore);
        }
    }

 */

}



