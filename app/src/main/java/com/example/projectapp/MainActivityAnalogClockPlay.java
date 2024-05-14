package com.example.projectapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivityAnalogClockPlay extends AppCompatActivity {

    private AnalogClockView analogClockView;
    private EditText timeInputEditText;
    private Button submitButton;
    private Button backToAnalogClock;
    private TextView questionCountTextView;
    private TextView personalBestTextView;
    private TextView currentScoreTextView;
    private int personalBest;
    private int currentScore;
    private int questionCount;

    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "MyPrefs";
    private static final String PERSONAL_BEST_KEY = "personalBest";
    private static final String CURRENT_SCORE_KEY = "currentScore";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_analog_clock_play);

        // Find views by their IDs
        analogClockView = findViewById(R.id.analogClockView);
        timeInputEditText = findViewById(R.id.timeInputEditText);
        submitButton = findViewById(R.id.submitButton);
        backToAnalogClock=findViewById(R.id.backToAnalogClock);
        questionCountTextView = findViewById(R.id.questionCountTextView);
        personalBestTextView = findViewById(R.id.personalBestTextView);
        currentScoreTextView = findViewById(R.id.currentScoreTextView);

        // Load personal best and current score from SharedPreferences
        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        personalBest = sharedPreferences.getInt(PERSONAL_BEST_KEY, 0);
        currentScore = 0; // Reset current score
        questionCount = 1; // Reset question count

        // Display personal best and current score
        updateScoreDisplay();
        updateQuestionCount();

        // Set click listener for the submit button
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the user input from the EditText
                String userInput = timeInputEditText.getText().toString().trim();

                // Get the correct time from the AnalogClockView
                int hour = analogClockView.getHour();
                int minute = analogClockView.getMinute();

                // Check if the user input matches the correct time
                if (isValidTime(userInput, hour, minute)) {
                    // Display a success message
                    Toast.makeText(MainActivityAnalogClockPlay.this, "Correct!", Toast.LENGTH_SHORT).show();

                    // Increase current score by 2
                    currentScore += 2;
                } else {
                    // Display an error message
                    Toast.makeText(MainActivityAnalogClockPlay.this, "Incorrect. Try again!", Toast.LENGTH_SHORT).show();

                    // Decrease current score by 1
                    currentScore -= 1;
                }

                // Increase question count
                questionCount++;

                // Check if 15 questions have been asked
                if (questionCount > 15) {
                    // Show end-game message
                    showEndGameMessage();
                } else {
                    // Generate a new random time
                    generateRandomTime();

                    // Clear the EditText
                    timeInputEditText.getText().clear();

                    // Update question count display
                    updateQuestionCount();

                    // Update score display
                    updateScoreDisplay();
                }
            }
        });
    }

    // Method to validate the user input against the correct time
    private boolean isValidTime(String userInput, int hour, int minute) {
        if (userInput.isEmpty()) {
            return false;
        }

        String[] parts = userInput.split(":");
        if (parts.length != 2) {
            return false;
        }

        try {
            int inputHour = Integer.parseInt(parts[0]);
            int inputMinute = Integer.parseInt(parts[1]);

            return inputHour == hour && inputMinute == minute;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Method to generate a new random time and update the AnalogClockView
    private void generateRandomTime() {
        // Generate random hour (1-12) and minute (0-55, multiple of 5)
        int randomHour = (int) (Math.random() * 12) + 1;
        int randomMinute = (int) (Math.random() * 12) * 5;

        // Update the AnalogClockView with the new random time
        analogClockView.setTime(randomHour, randomMinute);
    }

    // Method to save the personal best and current score to SharedPreferences
    private void saveScores() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(PERSONAL_BEST_KEY, personalBest);
        editor.putInt(CURRENT_SCORE_KEY, currentScore);
        editor.apply();
    }

    // Method to update the score display
    private void updateScoreDisplay() {
        // Update personal best and current score TextViews
        personalBestTextView.setText("Personal Best: " + personalBest);
        currentScoreTextView.setText("Current Score: " + currentScore);
    }

    // Method to update the question count display
    private void updateQuestionCount() {
        // Update question count TextView
        questionCountTextView.setText("Question: " + questionCount + " / 15");
    }

    // Method to show end-game message
    private void showEndGameMessage() {
        // Update personal best if current score exceeds it
        if (currentScore > personalBest) {
            personalBest = currentScore;
        }

        // Save scores
        saveScores();

        // Show end-game message with current score and personal best
        String message = "Game Over!\nYour Score: " + currentScore + "\nPersonal Best: " + personalBest;
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        submitButton.setVisibility(View.INVISIBLE);
        timeInputEditText.setVisibility(View.INVISIBLE);
        backToAnalogClock.setVisibility(View.VISIBLE);
        // Reset current score
        currentScore = 0;
        backToAnalogClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivityAnalogClockPlay.this,MainActivityAnalogClock.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Save scores when activity is paused
        saveScores();
    }
}
