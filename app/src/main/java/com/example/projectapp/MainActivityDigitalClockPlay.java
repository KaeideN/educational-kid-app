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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class MainActivityDigitalClockPlay extends AppCompatActivity {

    private ImageView imageClock;
    private Context context;
    private Resources resources;
    private Button buttonOption1, buttonOption2, buttonOption3, buttonBack;
    private TextView textViewScore, textViewBest, textViewQuestionCount;
    private List<Integer> clockImages = Arrays.asList(
            R.drawable.dclock1, R.drawable.dclock2, R.drawable.dclock3, R.drawable.dclock4, R.drawable.dclock5,
            R.drawable.dclock6, R.drawable.dclock7, R.drawable.dclock8, R.drawable.dclock9, R.drawable.dclock10,
            R.drawable.dclock11, R.drawable.dclock12, R.drawable.dclock13, R.drawable.dclock14, R.drawable.dclock15,
            R.drawable.dclock16, R.drawable.dclock17, R.drawable.dclock18, R.drawable.dclock19, R.drawable.dclock20,
            R.drawable.dclock21, R.drawable.dclock22, R.drawable.dclock23, R.drawable.dclock24
    );

    private List<String> times24Hour = Arrays.asList(
            "01:23", "23:35", "12:45", "18:30", "02:10", "03:59", "04:00", "05:15",
            "06:25", "07:40", "08:22", "09:35", "10:30", "11:11", "12:48", "13:04",
            "14:45", "15:10", "16:34", "17:00", "18:25", "20:20", "21:30", "23:59"
    );

    private int currentIndex = -1;
    private int currentScore = 0;
    private int playerBest = 0;
    private int questionCount = 1;
    private int totalQuestions = 8; // Total number of questions
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_digital_clock_play);

        SharedPreferences sharedPreferences = getSharedPreferences("Settings", MODE_PRIVATE);
        String language = LocaleHelper.getLanguage(this);
        context = LocaleHelper.setLocale(MainActivityDigitalClockPlay.this, language);
        resources = context.getResources();

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        playerBest = sharedPreferences.getInt("bestScore", 0);

        imageClock = findViewById(R.id.imageClock);
        buttonOption1 = findViewById(R.id.buttonOption1);
        buttonOption2 = findViewById(R.id.buttonOption2);
        buttonOption3 = findViewById(R.id.buttonOption3);
        buttonBack = findViewById(R.id.backToDigitalClock);
        textViewScore = findViewById(R.id.textViewScore);
        textViewBest = findViewById(R.id.textViewBest);
        textViewQuestionCount = findViewById(R.id.textViewQuestionCount);

        textViewBest.setText(resources.getString(R.string.personal_best)+": " + playerBest);
        textViewScore.setText(resources.getString(R.string.current_score)+": "+currentScore);
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

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivityDigitalClockPlay.this, MainActivityDigitalClock.class);
                startActivity(intent);
                finish();
            }
        });

        displayNextClock();
    }

    private void updateQuestionCount() {
        textViewQuestionCount.setText(questionCount + "/" + totalQuestions);
    }

    private void displayNextClock() {
        if (questionCount > totalQuestions) {
            // End of questions
            Toast.makeText(this, "End of questions! Your final score is " + currentScore, Toast.LENGTH_LONG).show();
            return;
        }

        Random random = new Random();
        int nextIndex;
        do {
            nextIndex = random.nextInt(clockImages.size());
        } while (nextIndex == currentIndex);
        currentIndex = nextIndex;

        imageClock.setImageResource(clockImages.get(currentIndex));

        String correctTime24Hour = times24Hour.get(currentIndex);
        String correctTime12Hour = convertTo12HourFormat(correctTime24Hour);
        List<String> options = generateOptions(correctTime12Hour);
        Collections.shuffle(options);
        buttonOption1.setText(options.get(0));
        buttonOption2.setText(options.get(1));
        buttonOption3.setText(options.get(2));

        updateQuestionCount();
        questionCount++;
    }

    private String convertTo12HourFormat(String time24Hour) {
        try {
            SimpleDateFormat sdf24Hour = new SimpleDateFormat("HH:mm", Locale.getDefault());
            Date date = sdf24Hour.parse(time24Hour);
            SimpleDateFormat sdf12Hour;
            if (time24Hour.startsWith("10") || time24Hour.startsWith("11") || time24Hour.startsWith("12")) {
                sdf12Hour = new SimpleDateFormat("hh:mm a", Locale.getDefault());
            } else {
                sdf12Hour = new SimpleDateFormat("h:mm a", Locale.getDefault());
            }
            return sdf12Hour.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    private List<String> generateOptions(String correctTime) {
        List<String> options = new ArrayList<>();
        options.add(correctTime);

        Random random = new Random();
        String wrongTime1 = generateWrongTime(correctTime, random);
        options.add(wrongTime1);

        String reverseTime = generateReverseTime(correctTime);
        options.add(reverseTime);

        return options;
    }

    private String generateWrongTime(String correctTime, Random random) {
        String[] parts = correctTime.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1].split("\\s+")[0]);
        String am_pm = parts[1].split("\\s+")[1];

        int offsetHours = random.nextInt(6) - 3;
        int offsetMinutes = random.nextInt(60) - 30;

        hours += offsetHours;
        minutes += offsetMinutes;

        if (minutes < 0) {
            minutes += 60;
            hours--;
        }
        minutes %= 60;

        if (hours < 0) {
            hours += 12;
        } else if (hours > 12) {
            hours -= 12;
        }

        String wrongTime = String.format("%d:%02d %s", hours, minutes, am_pm);

        return wrongTime;
    }

    private String generateReverseTime(String time) {
        String[] parts = time.split("\\s+");
        String[] timeParts = parts[0].split(":");
        int hours = Integer.parseInt(timeParts[0]);
        int minutes = Integer.parseInt(timeParts[1]);
        String am_pm = parts[1];

        if (am_pm.equalsIgnoreCase("AM")) {
            am_pm = "PM";
        } else {
            am_pm = "AM";
        }

        return String.format("%d:%02d %s", hours, minutes, am_pm);
    }

    private void checkAnswer(String selectedOption) {
        String correctTime = times24Hour.get(currentIndex);

        if (selectedOption.equals(correctTime) || selectedOption.equals(convertTo12HourFormat(correctTime))) {
            currentScore += 2;
            Toast.makeText(this, "Correct! Score: " + currentScore, Toast.LENGTH_SHORT).show();
        } else {
            currentScore = Math.max(0, currentScore - 1);
            Toast.makeText(this, "Incorrect! The correct time is " + convertTo12HourFormat(correctTime) + ". Score: " + currentScore, Toast.LENGTH_SHORT).show();
        }

        // Update the score text view
        textViewScore.setText(resources.getString(R.string.current_score) +" "+ currentScore);

        if (questionCount > totalQuestions) {
            // End of questions
            if (currentScore > playerBest) {
                playerBest = currentScore;
                textViewBest.setText(resources.getString(R.string.personal_best) +" "+ playerBest);
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
            // Make back button visible
            buttonBack.setText(resources.getString(R.string.back));
            buttonBack.setVisibility(View.VISIBLE);
            return;
        }

        displayNextClock();
    }
}
