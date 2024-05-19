package com.example.projectapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivityABCPlay extends AppCompatActivity {

    private ImageView imageQuestion;
    private Button buttonOption1, buttonOption2, buttonOption3, buttonOption4, buttonTryAgain,buttonHome;
    private TextView textViewScore, textViewBest, textViewQuestionCount;
    private List<Integer> images = Arrays.asList(
            R.drawable.apple, R.drawable.banana, R.drawable.pineapple, R.drawable.astronaut,
            R.drawable.backpack, R.drawable.computer, R.drawable.cellphone, R.drawable.television, R.drawable.library,R.drawable.apartment,R.drawable.rabbit,
            R.drawable.horse, R.drawable.giraffe, R.drawable.guitar
    );
    private List<String> imagesCorrectAnswer = Arrays.asList(
            "apple", "banana", "pineapple", "astronaut",  "backpack", "computer", "cellphone",
            "television", "library", "apartment", "rabbit", "horse", "giraffe", "guitar"
    );

    private int currentIndex = -1;
    private int currentScore = 0;
    private int playerBest = 0;
    private int questionCount = 1;
    private int totalQuestions = 8; // Total number of questions
    private SharedPreferences sharedPreferences,sharedPreferencesLang;
    private Context context;
    private Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_abcplay);

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        playerBest = sharedPreferences.getInt("bestScore", 0);

        SharedPreferences sharedPreferencesLang = getSharedPreferences("Settings", MODE_PRIVATE);
        String language = LocaleHelper.getLanguage(this);
        context = LocaleHelper.setLocale(MainActivityABCPlay.this, language);
        resources = context.getResources();

        imageQuestion = findViewById(R.id.imageQuestion);
        buttonOption1 = findViewById(R.id.buttonOption1);
        buttonOption2 = findViewById(R.id.buttonOption2);
        buttonOption3 = findViewById(R.id.buttonOption3);
        buttonOption4 = findViewById(R.id.buttonOption4);
        buttonTryAgain = findViewById(R.id.tryAgain);
        buttonHome    = findViewById(R.id.backToHome);
        textViewScore = findViewById(R.id.textViewScore);
        textViewBest = findViewById(R.id.textViewBest);
        textViewQuestionCount = findViewById(R.id.textViewQuestionCount);

        textViewBest.setText(resources.getString(R.string.personal_best)+ " " + playerBest);
        updateQuestionCount();


        buttonOption1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(buttonOption1.getText().toString());
            }
        });

        buttonOption2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(buttonOption2.getText().toString());
            }
        });

        buttonOption3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(buttonOption3.getText().toString());
            }
        });
        buttonOption4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(buttonOption4.getText().toString());
            }
        });




        buttonTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivityABCPlay.this, MainActivityABCPlay.class);
                startActivity(intent);
                finish();
            }
        });
        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivityABCPlay.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        displayNextImage();
    }

    private void updateQuestionCount() {
        textViewQuestionCount.setText(questionCount + "/" + totalQuestions);
    }
private void displayNextImage() {
    if (questionCount > totalQuestions) {
        // End of questions
        Toast.makeText(this, "End of questions! Your final score is " + currentScore, Toast.LENGTH_LONG).show();
        return;
    }

    Random random = new Random();
    int nextIndex;
    do {
        nextIndex = random.nextInt(images.size());
    } while (nextIndex == currentIndex);
    currentIndex = nextIndex;

    int currentImageResource = images.get(currentIndex);

    // Set the image to display
    imageQuestion.setImageResource(currentImageResource);

    // Find the correct answer for the current image
    String correctAnswer = imagesCorrectAnswer.get(currentIndex);

    // Generate options and shuffle them
    List<String> options = generateOptions(correctAnswer);
    Collections.shuffle(options);
    buttonOption1.setText(options.get(0));
    buttonOption2.setText(options.get(1));
    buttonOption3.setText(options.get(2));
    buttonOption4.setText(options.get(3));

    updateQuestionCount();
    questionCount++;
}



    private List<String> generateOptions(String correctAnswer) {
        List<String> options = new ArrayList<>();
        options.add(correctAnswer);

        Random random = new Random();

        // Generate three wrong options
        while (options.size() < 4) {
            String wrongOption = generateWrongOption(correctAnswer, random);
            if (!options.contains(wrongOption)) {
                options.add(wrongOption);
            }
        }

        Collections.shuffle(options);
        return options;
    }

    private String generateWrongOption(String correctAnswer, Random random) {
        // Example logic: replace a random character in the correct answer
        StringBuilder sb = new StringBuilder(correctAnswer);
        int index = random.nextInt(sb.length());
        char randomChar = (char) ('a' + random.nextInt(26)); // Generate a random character
        sb.setCharAt(index, randomChar);
        return sb.toString();
    }




    private void checkAnswer(String selectedOption) {
        String correctAnswer = imagesCorrectAnswer.get(currentIndex);

        if (selectedOption.equals(correctAnswer)) {
            currentScore += 2;
            Toast.makeText(this, "Correct! Score: " + currentScore, Toast.LENGTH_SHORT).show();
        } else {
            currentScore = Math.max(0, currentScore - 1);
            Toast.makeText(this, "Incorrect! The correct pronunciation is " + imagesCorrectAnswer.get(currentIndex) + ". Score: " + currentScore, Toast.LENGTH_SHORT).show();
        }

        // Update the score text view
        textViewScore.setText(resources.getString(R.string.current_score)+": " + currentScore);

        if (questionCount > totalQuestions) {
            // End of questions
            if (currentScore > playerBest) {
                playerBest = currentScore;
                textViewBest.setText(resources.getString(R.string.personal_best)+": " + playerBest);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("bestScore", playerBest);
                editor.apply();
                Toast.makeText(this, "End of questions! Your final score is " + currentScore + ". New Personal Best!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "End of questions! Your final score is " + currentScore, Toast.LENGTH_LONG).show();
            }
            // Hide options
            buttonOption1.setVisibility(View.INVISIBLE);
            buttonOption2.setVisibility(View.INVISIBLE);
            buttonOption3.setVisibility(View.INVISIBLE);
            buttonOption4.setVisibility(View.INVISIBLE);
            // Make back button visible
            buttonTryAgain.setText(resources.getString(R.string.try_again_button));
            buttonHome.setText(resources.getString(R.string.home_button));
            buttonTryAgain.setVisibility(View.VISIBLE);
            buttonHome.setVisibility(View.VISIBLE);
            return;
        }

        displayNextImage();
    }
}
