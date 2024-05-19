package com.example.projectapp;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MainActivityDaysPlay extends AppCompatActivity implements View.OnClickListener {

    private int currentPosition = 0; // To keep track of the current position

    // Store original and shuffled positions
    private int[] originalX = new int[7];
    private int[] originalY = new int[7];
    private int[] shuffledX = new int[7];
    private int[] shuffledY = new int[7];
    private boolean[] isMoved = new boolean[7];
    private int[] currentDayPosition = new int[7]; // To track the current drop positions

    // Timer related variables
    private long startTime = 0L;
    private Handler timerHandler = new Handler();
    private TextView timerTextView;

    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            long millis = System.currentTimeMillis() - startTime;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;

            timerTextView.setText(String.format("%d:%02d", minutes, seconds));
            timerHandler.postDelayed(this, 500);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_days_play);

        // Find ImageView elements
        ImageView sundayImageView = findViewById(R.id.sunday);
        ImageView mondayImageView = findViewById(R.id.monday);
        ImageView tuesdayImageView = findViewById(R.id.tuesday);
        ImageView wednesdayImageView = findViewById(R.id.wednesday);
        ImageView thursdayImageView = findViewById(R.id.thursday);
        ImageView fridayImageView = findViewById(R.id.friday);
        ImageView saturdayImageView = findViewById(R.id.saturday);

        // Array of the ImageView elements
        ImageView[] dayImageViews = new ImageView[]{
                sundayImageView, mondayImageView, tuesdayImageView,
                wednesdayImageView, thursdayImageView, fridayImageView, saturdayImageView
        };

        // Set OnClickListener for each ImageView
        for (ImageView dayImageView : dayImageViews) {
            dayImageView.setOnClickListener(this);
        }

        // Timer TextView
        timerTextView = findViewById(R.id.timerTextView);

        // Use ViewTreeObserver to get the initial positions after layout is drawn
        ViewTreeObserver vto = sundayImageView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // Remove the listener to avoid multiple calls
                sundayImageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                // Initialize original positions
                for (int i = 0; i < dayImageViews.length; i++) {
                    originalX[i] = dayImageViews[i].getLeft();
                    originalY[i] = dayImageViews[i].getTop();
                }

                // Shuffle the initial positions and store them
                shuffleInitialPositions(dayImageViews);

                // Start the timer
                startTime = System.currentTimeMillis();
                timerHandler.postDelayed(timerRunnable, 0);
            }
        });
    }

    private void shuffleInitialPositions(ImageView[] dayImageViews) {
        List<ImageView> imageViewList = Arrays.asList(dayImageViews);
        Collections.shuffle(imageViewList);

        for (int i = 0; i < imageViewList.size(); i++) {
            ImageView dayImageView = imageViewList.get(i);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) dayImageView.getLayoutParams();
            shuffledX[i] = originalX[Arrays.asList(dayImageViews).indexOf(dayImageView)];
            shuffledY[i] = originalY[Arrays.asList(dayImageViews).indexOf(dayImageView)];
            layoutParams.leftMargin = shuffledX[i];
            layoutParams.topMargin = shuffledY[i];
            dayImageView.setLayoutParams(layoutParams);
        }

        // Copy shuffled positions back to originalX and originalY arrays
        System.arraycopy(shuffledX, 0, originalX, 0, shuffledX.length);
        System.arraycopy(shuffledY, 0, originalY, 0, shuffledY.length);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        int index = -1;

        if (viewId == R.id.sunday) {
            index = 0;
        } else if (viewId == R.id.monday) {
            index = 1;
        } else if (viewId == R.id.tuesday) {
            index = 2;
        } else if (viewId == R.id.wednesday) {
            index = 3;
        } else if (viewId == R.id.thursday) {
            index = 4;
        } else if (viewId == R.id.friday) {
            index = 5;
        } else if (viewId == R.id.saturday) {
            index = 6;
        }

        if (index != -1) {
            if (isMoved[index]) {
                // Move back to shuffled position
                moveImageView(view, shuffledX[index], shuffledY[index]);
                isMoved[index] = false;
                currentDayPosition[index] = -1; // Reset current position
                adjustCurrentPosition(); // Adjust currentPosition after moving back
            } else {
                // Teleport the clicked ImageView to predefined drop target positions
                int x = 0, y = 0;
                switch (currentPosition) {
                    case 0:
                        x = findViewById(R.id.drop_target_sunday).getLeft();
                        y = findViewById(R.id.drop_target_sunday).getTop();
                        currentDayPosition[index] = 0;
                        break;
                    case 1:
                        x = findViewById(R.id.drop_target_monday).getLeft();
                        y = findViewById(R.id.drop_target_monday).getTop();
                        currentDayPosition[index] = 1;
                        break;
                    case 2:
                        x = findViewById(R.id.drop_target_tuesday).getLeft();
                        y = findViewById(R.id.drop_target_tuesday).getTop();
                        currentDayPosition[index] = 2;
                        break;
                    case 3:
                        x = findViewById(R.id.drop_target_wednesday).getLeft();
                        y = findViewById(R.id.drop_target_wednesday).getTop();
                        currentDayPosition[index] = 3;
                        break;
                    case 4:
                        x = findViewById(R.id.drop_target_thursday).getLeft();
                        y = findViewById(R.id.drop_target_thursday).getTop();
                        currentDayPosition[index] = 4;
                        break;
                    case 5:
                        x = findViewById(R.id.drop_target_friday).getLeft();
                        y = findViewById(R.id.drop_target_friday).getTop();
                        currentDayPosition[index] = 5;
                        break;
                    case 6:
                        x = findViewById(R.id.drop_target_saturday).getLeft();
                        y = findViewById(R.id.drop_target_saturday).getTop();
                        currentDayPosition[index] = 6;
                        break;
                }
                moveImageView(view, x, y);
                isMoved[index] = true;
                currentPosition++; // Increment current position
            }

            if (checkIfAlignedCorrectly()) {
                timerHandler.removeCallbacks(timerRunnable); // Stop the timer
                long totalTime = System.currentTimeMillis() - startTime;
                int totalSeconds = (int) (totalTime / 1000);
                int minutes = totalSeconds / 60;
                int seconds = totalSeconds % 60;
                String message = String.format("Congratulations! You completed in %d:%02d", minutes, seconds);
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void moveImageView(View view, int x, int y) {
        // Set the position of the clicked ImageView
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
        layoutParams.leftMargin = x;
        layoutParams.topMargin = y;
        view.setLayoutParams(layoutParams);
    }

    private void adjustCurrentPosition() {
        currentPosition = 0;
        for (boolean moved : isMoved) {
            if (moved) {
                currentPosition++;
            } else {
                break;
            }
        }
    }

    private boolean checkIfAlignedCorrectly() {
        // Check if all days are in their correct positions
        return currentDayPosition[0] == 0 && currentDayPosition[1] == 1 &&
                currentDayPosition[2] == 2 && currentDayPosition[3] == 3 &&
                currentDayPosition[4] == 4 && currentDayPosition[5] == 5 &&
                currentDayPosition[6] == 6;
    }
}