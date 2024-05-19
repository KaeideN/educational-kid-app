package com.example.projectapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MainActivityPicturesPlay extends AppCompatActivity {

    private ImageView imageQuestion;
    private ImageButton buttonOption1, buttonOption2, buttonOption3, buttonOption4;
    private Button tryAgain,home;
    private TextView textViewScore, textViewBest, textViewQuestionCount;
    private List<Integer> images = Arrays.asList(
            R.drawable.apple, R.drawable.astronaut, R.drawable.guitar, R.drawable.banana, R.drawable.cellphone, R.drawable.giraffe
    );
    private Map<Integer, List<Integer>> answersMap = new HashMap<>();
    private Map<Integer, Integer> correctAnswerMap = new HashMap<>();

    private int currentIndex = -1;
    private int currentScore = 0;
    private int playerBest = 0;
    private int questionCount = 1;
    private int totalQuestions = 5; // Total number of questions
    private SharedPreferences sharedPreferences, sharedPreferencesLang;
    private Context context;
    private Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_pictures_play); // Use the correct layout resource

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        playerBest = sharedPreferences.getInt("bestScore", 0);

        SharedPreferences sharedPreferencesLang = getSharedPreferences("Settings", MODE_PRIVATE);
        String language = LocaleHelper.getLanguage(this);
        context = LocaleHelper.setLocale(MainActivityPicturesPlay.this, language);
        resources = context.getResources();

        imageQuestion = findViewById(R.id.imageQuestion);
        buttonOption1 = findViewById(R.id.buttonOption1);
        buttonOption2 = findViewById(R.id.buttonOption2);
        buttonOption3 = findViewById(R.id.buttonOption3);
        buttonOption4 = findViewById(R.id.buttonOption4);
        textViewScore = findViewById(R.id.textViewScore);
        textViewBest = findViewById(R.id.textViewBest);
        textViewQuestionCount = findViewById(R.id.textViewQuestionCount);
        tryAgain = findViewById(R.id.tryAgain);
        home = findViewById(R.id.backToHome);

        tryAgain.setText(resources.getString(R.string.try_again_button));
        home.setText(resources.getString(R.string.home_button));

        textViewBest.setText(resources.getString(R.string.personal_best) + " " + playerBest);
        updateQuestionCount();

        buttonOption1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer((Integer) buttonOption1.getTag());
            }
        });

        buttonOption2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer((Integer) buttonOption2.getTag());
            }
        });

        buttonOption3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer((Integer) buttonOption3.getTag());
            }
        });

        buttonOption4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer((Integer) buttonOption4.getTag());
            }
        });

        // Initialize the answers map
        initializeAnswersMap();

        displayNextImage();
    }

    private void initializeAnswersMap() {
        // Initialize the map with question images and their respective answer options
        answersMap.put(R.drawable.apple, Arrays.asList(
                R.drawable.appleoption1, R.drawable.appleoption2, R.drawable.appleoption3, R.drawable.appleoption4));
        answersMap.put(R.drawable.astronaut, Arrays.asList(
                R.drawable.astronautoption1, R.drawable.astronautoption2, R.drawable.astronautoption3, R.drawable.astronautoption4));
        answersMap.put(R.drawable.guitar, Arrays.asList(
                R.drawable.guitaroption1, R.drawable.guitaroption2, R.drawable.guitaroption3, R.drawable.guitaroption4));
        answersMap.put(R.drawable.banana, Arrays.asList(
                R.drawable.bananaoption1, R.drawable.bananaoption2, R.drawable.bananaoption3, R.drawable.bananaoption4));
        answersMap.put(R.drawable.cellphone, Arrays.asList(
                R.drawable.cellphoneoption1, R.drawable.cellphoneoption2, R.drawable.cellphoneoption3, R.drawable.cellphoneoption4));
        answersMap.put(R.drawable.giraffe, Arrays.asList(
                R.drawable.giraffeoption1, R.drawable.giraffeoption2, R.drawable.giraffeoption3, R.drawable.giraffeoption4));
        answersMap.put(R.drawable.car, Arrays.asList(
                R.drawable.caroption1, R.drawable.caroption2, R.drawable.caroption3, R.drawable.caroption4));

        // Initialize the map with correct answers
        correctAnswerMap.put(R.drawable.apple, R.drawable.appleoption1);
        correctAnswerMap.put(R.drawable.astronaut, R.drawable.astronautoption1);
        correctAnswerMap.put(R.drawable.guitar, R.drawable.guitaroption1);
        correctAnswerMap.put(R.drawable.banana, R.drawable.bananaoption1);
        correctAnswerMap.put(R.drawable.cellphone, R.drawable.cellphoneoption1);
        correctAnswerMap.put(R.drawable.giraffe, R.drawable.giraffeoption1);
        correctAnswerMap.put(R.drawable.car, R.drawable.caroption1);
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

        // Get the correct answers for the current image
        List<Integer> options = new ArrayList<>(answersMap.get(currentImageResource));
        Collections.shuffle(options);

        // Set images to buttons
        buttonOption1.setImageResource(options.get(0));
        buttonOption1.setTag(options.get(0));

        buttonOption2.setImageResource(options.get(1));
        buttonOption2.setTag(options.get(1));

        buttonOption3.setImageResource(options.get(2));
        buttonOption3.setTag(options.get(2));

        buttonOption4.setImageResource(options.get(3));
        buttonOption4.setTag(options.get(3));

        updateQuestionCount();
        questionCount++;
    }

    private void checkAnswer(int selectedImageResource) {
        int correctImageResource = correctAnswerMap.get(images.get(currentIndex));

        if (selectedImageResource == correctImageResource) {
            currentScore += 2;
            Toast.makeText(this, "Correct! Score: " + currentScore, Toast.LENGTH_SHORT).show();
        } else {
            currentScore = Math.max(0, currentScore - 1);
            Toast.makeText(this, "Incorrect! The correct answer is " + resources.getResourceEntryName(correctImageResource) + ". Score: " + currentScore, Toast.LENGTH_SHORT).show();
        }

        // Update the score text view
        textViewScore.setText(resources.getString(R.string.current_score) + ": " + currentScore);

        if (questionCount > totalQuestions) {
            // End of questions
            if (currentScore > playerBest) {
                playerBest = currentScore;
                textViewBest.setText(resources.getString(R.string.personal_best) + ": " + playerBest);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("bestScore", playerBest);
                editor.apply();
                Toast.makeText(this, "End of questions! Your final score is " + currentScore + ". New Personal Best!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "End of questions! Your final score is " + currentScore, Toast.LENGTH_LONG).show();
            }
            // Hide options
            imageQuestion.setVisibility(View.INVISIBLE);
            buttonOption1.setVisibility(View.INVISIBLE);
            buttonOption2.setVisibility(View.INVISIBLE);
            buttonOption3.setVisibility(View.INVISIBLE);
            buttonOption4.setVisibility(View.INVISIBLE);



            tryAgain.setVisibility(View.VISIBLE);
            home.setVisibility(View.VISIBLE);

            tryAgain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivityPicturesPlay.this,MainActivityPicturesPlay.class);
                    startActivity(intent);
                    finish();
                }
            });

            home.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        Intent intent = new Intent(MainActivityPicturesPlay.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                }
            });

            return;
        }

        displayNextImage();
    }
}
