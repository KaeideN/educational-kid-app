package com.example.projectapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivityMultiplicationPlay extends AppCompatActivity {

    private TextView questionTextView;
    private Button option1Button, option2Button, option3Button, option4Button,tryAgainButton,homeButton;

    private int correctAnswer;
    private int questionCount = 0;
    private int score = 0;
    private Context context;
    private Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_multiplication_play);

        SharedPreferences sharedPreferences = getSharedPreferences("Settings", MODE_PRIVATE);
        String language = LocaleHelper.getLanguage(this);
        context = LocaleHelper.setLocale(MainActivityMultiplicationPlay.this, language);
        resources = context.getResources();


        questionTextView = findViewById(R.id.questionTextView);
        option1Button = findViewById(R.id.option1Button);
        option2Button = findViewById(R.id.option2Button);
        option3Button = findViewById(R.id.option3Button);
        option4Button = findViewById(R.id.option4Button);
        tryAgainButton = findViewById(R.id.tryAgainMultip);
        homeButton = findViewById(R.id.homeMultip);

        tryAgainButton.setText(resources.getString(R.string.try_again_button));
        homeButton.setText(resources.getString(R.string.home_button));

        tryAgainButton.setVisibility(View.INVISIBLE);
        homeButton.setVisibility(View.INVISIBLE);

        setNewQuestion();

        tryAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivityMultiplicationPlay.this,MainActivityMultiplicationPlay.class);
                startActivity(intent);
                finish();
            }
        });
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivityMultiplicationPlay.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        View.OnClickListener answerButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button clickedButton = (Button) view;
                int chosenAnswer = Integer.parseInt(clickedButton.getText().toString());
                checkAnswer(chosenAnswer);
                questionCount++;
                if (questionCount < 8) {
                    setNewQuestion();
                } else {
                    endQuiz();
                }
            }
        };

        option1Button.setOnClickListener(answerButtonClickListener);
        option2Button.setOnClickListener(answerButtonClickListener);
        option3Button.setOnClickListener(answerButtonClickListener);
        option4Button.setOnClickListener(answerButtonClickListener);
    }

    private void setNewQuestion() {
        Random random = new Random();
        int factor1 = random.nextInt(10) + 1;
        int factor2 = random.nextInt(10) + 1;
        correctAnswer = factor1 * factor2;

        questionTextView.setText(factor1 + " x " + factor2 + " = ?");

        List<Integer> answers = new ArrayList<>();
        answers.add(correctAnswer);
        while (answers.size() < 4) {
            int wrongAnswer = random.nextInt(100) + 1;
            if (!answers.contains(wrongAnswer)) {
                answers.add(wrongAnswer);
            }
        }
        Collections.shuffle(answers);

        option1Button.setText(String.valueOf(answers.get(0)));
        option2Button.setText(String.valueOf(answers.get(1)));
        option3Button.setText(String.valueOf(answers.get(2)));
        option4Button.setText(String.valueOf(answers.get(3)));
    }

    private void checkAnswer(int chosenAnswer) {
        if (chosenAnswer == correctAnswer) {
            score++;
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Incorrect. The correct answer was " + correctAnswer, Toast.LENGTH_SHORT).show();
        }
    }

    private void endQuiz() {
        option1Button.setVisibility(View.INVISIBLE);
        option2Button.setVisibility(View.INVISIBLE);
        option3Button.setVisibility(View.INVISIBLE);
        option4Button.setVisibility(View.INVISIBLE);

        tryAgainButton.setVisibility(View.VISIBLE);
        homeButton.setVisibility(View.VISIBLE);

        String resultMessage = "Quiz over! Your score: " + score + "/8";
        Toast.makeText(this, resultMessage, Toast.LENGTH_LONG).show();
    }
}
