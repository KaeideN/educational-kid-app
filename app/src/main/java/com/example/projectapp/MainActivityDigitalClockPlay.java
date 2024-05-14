package com.example.projectapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivityDigitalClockPlay extends AppCompatActivity {

    private ImageView imageClock;
    private Button buttonOption1, buttonOption2, buttonOption3;

    // List of digital clock images
    private List<Integer> clockImages = Arrays.asList(
            R.drawable.dclock1, R.drawable.dclock2, R.drawable.dclock3, R.drawable.dclock4,R.drawable.dclock5,
            R.drawable.dclock6, R.drawable.dclock7, R.drawable.dclock8, R.drawable.dclock9,R.drawable.dclock10,
            R.drawable.dclock11, R.drawable.dclock12, R.drawable.dclock13, R.drawable.dclock14,R.drawable.dclock15,
            R.drawable.dclock16, R.drawable.dclock17, R.drawable.dclock18, R.drawable.dclock19,R.drawable.dclock20,
            R.drawable.dclock21,R.drawable.dclock22,R.drawable.dclock23, R.drawable.dclock24
    );

    // List of corresponding times
    private List<String> times = Arrays.asList(
            "01:23", "23:35", "12:45", "18:30", "02:10", "03:59", "04:00", "05:15",
            "06:25", "07:40", "08:22", "09:35", "10:30", "11:11", "12:48", "13:04",
            "14:45", "15:10", "16:34", "17:00", "18:25", "20:20", "21:30", "23:59"
    );

    // Index of the current displayed clock image
    private int currentIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_digital_clock_play);

        imageClock = findViewById(R.id.imageClock);
        buttonOption1 = findViewById(R.id.buttonOption1);
        buttonOption2 = findViewById(R.id.buttonOption2);
        buttonOption3 = findViewById(R.id.buttonOption3);

        // Set click listeners for options buttons
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

        // Display a random clock image and its corresponding options
        displayNextClock();
    }

    // Method to display the next clock image and its corresponding options
    private void displayNextClock() {
        Random random = new Random();

        // Get the next random index for the clock image
        int nextIndex;
        do {
            nextIndex = random.nextInt(clockImages.size());
        } while (nextIndex == currentIndex); // Ensure the next image is different from the current one

        currentIndex = nextIndex; // Update the current index

        // Set random clock image
        imageClock.setImageResource(clockImages.get(currentIndex));

        // Get the corresponding time
        String correctTime = times.get(currentIndex);

        // Generate options for the correct time
        List<String> options = generateOptions(correctTime);

        // Shuffle options
        Collections.shuffle(options);

        // Set options buttons text
        buttonOption1.setText(options.get(0));
        buttonOption2.setText(options.get(1));
        buttonOption3.setText(options.get(2));
    }

    // Method to generate options for the correct time
    private List<String> generateOptions(String correctTime) {
        List<String> options = new ArrayList<>();
        options.add(correctTime);

        Random random = new Random();

        // Generate two wrong options
        while (options.size() < 3) {
            String wrongTime = generateWrongTime(correctTime, random);
            if (!options.contains(wrongTime)) {
                options.add(wrongTime);
            }
        }

        return options;
    }

    // Method to generate a wrong time option
    private String generateWrongTime(String correctTime, Random random) {
        // Split the correct time into hours and minutes
        String[] parts = correctTime.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);

        // Generate a random offset for hours and minutes
        int offsetHours = random.nextInt(6) - 3; // Random offset between -3 and +3 hours
        int offsetMinutes = random.nextInt(60) - 30; // Random offset between -30 and +30 minutes

        // Add the offsets to the correct time
        hours += offsetHours;
        minutes += offsetMinutes;

        // Handle overflow/underflow for hours and minutes
        if (hours < 0) {
            hours += 24; // Wrap around to the next day
        }
        hours %= 24; // Ensure hours are within 0-23 range

        if (minutes < 0) {
            minutes += 60; // Wrap around to the previous hour
            hours--; // Adjust hours accordingly
        }
        minutes %= 60; // Ensure minutes are within 0-59 range

        // Format hours and minutes as a string in 12-hour format (with AM/PM)
        String wrongTime = String.format("%d:%02d %s", (hours % 12 == 0) ? 12 : hours % 12, minutes, (hours >= 12) ? "PM" : "AM");

        return wrongTime;
    }

    // Method to check the selected answer
    private void checkAnswer(String selectedOption) {
        String correctTime = times.get(currentIndex);

        if (selectedOption.equals(correctTime)) {
            // Correct answer
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
        } else {
            // Incorrect answer
            Toast.makeText(this, "Incorrect! The correct time is " + correctTime, Toast.LENGTH_SHORT).show();
        }

        // Display the next clock image with its corresponding options
        displayNextClock();
    }
}
