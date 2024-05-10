package com.example.projectapp;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
import com.example.projectapp.R;

public class MainActivitySeasonsPlay extends AppCompatActivity {

    private TextView textViewQuestion;
    private Button buttonOption1, buttonOption2, buttonOption3, buttonOption4;
    private int correctOption = 1; // Change this to the correct option number

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_seasons_play);

        textViewQuestion = findViewById(R.id.textViewQuestion);
        buttonOption1 = findViewById(R.id.buttonOption1);
        buttonOption2 = findViewById(R.id.buttonOption2);
        buttonOption3 = findViewById(R.id.buttonOption3);
        buttonOption4 = findViewById(R.id.buttonOption4);

        // Set question and options
        textViewQuestion.setText("What season comes after spring ?");
        buttonOption1.setText("A) Summer");
        buttonOption2.setText("B) Fall");
        buttonOption3.setText("C) Winter");
        buttonOption4.setText("D) Autumn");

        // Set click listeners for options
        buttonOption1.setOnClickListener(v -> checkAnswer(buttonOption1, 1));
        buttonOption2.setOnClickListener(v -> checkAnswer(buttonOption2, 2));
        buttonOption3.setOnClickListener(v -> checkAnswer(buttonOption3, 3));
        buttonOption4.setOnClickListener(v -> checkAnswer(buttonOption4, 4));
    }

    private void checkAnswer(Button selectedButton, int selectedOption) {
        if (selectedOption == correctOption) {
            // Correct answer
            selectedButton.setBackgroundColor(Color.GREEN);
            // You can perform additional actions here, such as updating score
        } else {
            // Incorrect answer
            selectedButton.setBackgroundColor(Color.RED);
            // Find the correct option button and highlight it
            switch (correctOption) {
                case 1:
                    buttonOption1.setBackgroundColor(Color.GREEN);
                    break;
                case 2:
                    buttonOption2.setBackgroundColor(Color.GREEN);
                    break;
                case 3:
                    buttonOption3.setBackgroundColor(Color.GREEN);
                    break;
                case 4:
                    buttonOption4.setBackgroundColor(Color.GREEN);
                    break;
            }
        }
        // Disable all buttons after user has answered
        buttonOption1.setEnabled(false);
        buttonOption2.setEnabled(false);
        buttonOption3.setEnabled(false);
        buttonOption4.setEnabled(false);
    }

}
