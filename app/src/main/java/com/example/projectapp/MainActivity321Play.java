package com.example.projectapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity321Play extends AppCompatActivity {

    private ArrayList<Integer> numbers;
    private ArrayList<Integer> shownNumbers;
    private int currentNumberIndex;
    private GridLayout gridLayout;
    private int highestScore;
    private int level;
    private int numNumbersToShow;
    private int n;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity321_play);

        textView = findViewById(R.id.textView);
        gridLayout = findViewById(R.id.gridLayout);

        shownNumbers = new ArrayList<>();
        numbers = new ArrayList<>();

        for (int i = 1; i <= 9; i++) {
            numbers.add(i);
        }

        startGame();
    }

    private void startGame() {
        level++;
        currentNumberIndex = 0;
        numNumbersToShow++;
        textView.setText("Level " + level);

        Collections.shuffle(numbers);
        shownNumbers.clear();
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
                    shownNumbers.add(number);
                    showNumbers();
                } else {
                    textView.setText("Type the digits from end to beginning.");
                    n = shownNumbers.size() - 1; // Initialize n to the last index of shownNumbers
                }
            }
        }, 1500);
    }

    public void onNumberButtonClick(View view) {
        Button button = (Button) view;
        int number = Integer.parseInt(button.getText().toString());

        if (n >= 0 && number == shownNumbers.get(n)) {
            button.setBackgroundColor(Color.GREEN);
            n--; // Decrement n after a correct button press
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    button.setBackgroundResource(R.drawable.button_background);
                }
            }, 1000);

            if (n < 0) { // All numbers have been correctly pressed in reverse order
                level++;
                numNumbersToShow++;
                startGame(); // Start next level
            }
        } else {
            button.setBackgroundColor(Color.RED);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    button.setBackgroundResource(R.drawable.button_background);
                }
            }, 1000);

            handleWrongButtonClick();
        }
    }

    private void handleWrongButtonClick() {
        Intent intent = new Intent(MainActivity321Play.this, ResultActivity321.class);
        intent.putExtra("userScore", level); // Pass the user's score to the result activity
        startActivity(intent);
        finish();

    }
}
