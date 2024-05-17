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

public class MainActivityMonthsPlay extends AppCompatActivity implements View.OnClickListener {

    private int currentPosition = 0; // To keep track of the current position

    // Store original positions
    private int[] originalX = new int[12];
    private int[] originalY = new int[12];
    private boolean[] isMoved = new boolean[12];
    public int[] currentMonthPosition = new int[12]; // To track the current drop positions

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
        setContentView(R.layout.activity_main_months_play);

        // Find ImageView elements
        ImageView januaryImageView = findViewById(R.id.january);
        ImageView februaryImageView = findViewById(R.id.february);
        ImageView marchImageView = findViewById(R.id.march);
        ImageView aprilImageView = findViewById(R.id.april);
        ImageView mayImageView = findViewById(R.id.may);
        ImageView juneImageView = findViewById(R.id.june);
        ImageView julyImageView = findViewById(R.id.july);
        ImageView augustImageView = findViewById(R.id.august);
        ImageView septemberImageView = findViewById(R.id.september);
        ImageView octoberImageView = findViewById(R.id.october);
        ImageView novemberImageView = findViewById(R.id.november);
        ImageView decemberImageView = findViewById(R.id.december);

        // Set OnClickListener for each ImageView
        januaryImageView.setOnClickListener(this);
        februaryImageView.setOnClickListener(this);
        marchImageView.setOnClickListener(this);
        aprilImageView.setOnClickListener(this);
        mayImageView.setOnClickListener(this);
        juneImageView.setOnClickListener(this);
        julyImageView.setOnClickListener(this);
        augustImageView.setOnClickListener(this);
        septemberImageView.setOnClickListener(this);
        octoberImageView.setOnClickListener(this);
        novemberImageView.setOnClickListener(this);
        novemberImageView.setOnClickListener(this);
        decemberImageView.setOnClickListener(this);

        // Timer TextView
        timerTextView = findViewById(R.id.timerTextView);

        // Use ViewTreeObserver to get the initial positions after layout is drawn
        ViewTreeObserver vto = januaryImageView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // Remove the listener to avoid multiple calls
                januaryImageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                // Initialize original positions
                originalX[0] = januaryImageView.getLeft();
                originalY[0] = januaryImageView.getTop();
                originalX[1] = februaryImageView.getLeft();
                originalY[1] = februaryImageView.getTop();
                originalX[2] = marchImageView.getLeft();
                originalY[2] = marchImageView.getTop();
                originalX[3] = aprilImageView.getLeft();
                originalY[3] = aprilImageView.getTop();
                originalX[4] = mayImageView.getLeft();
                originalY[4] = mayImageView.getTop();
                originalX[5] = juneImageView.getLeft();
                originalY[5] = juneImageView.getTop();
                originalX[6] = julyImageView.getLeft();
                originalY[6] = julyImageView.getTop();
                originalX[7] = augustImageView.getLeft();
                originalY[7] = augustImageView.getTop();
                originalX[8] = septemberImageView.getLeft();
                originalY[8] = septemberImageView.getTop();
                originalX[9] = octoberImageView.getLeft();
                originalY[9] = octoberImageView.getTop();
                originalX[10] = novemberImageView.getLeft();
                originalY[10] = novemberImageView.getTop();
                originalX[11] = decemberImageView.getLeft();
                originalY[11] = decemberImageView.getTop();

                // Start the timer
                startTime = System.currentTimeMillis();
                timerHandler.postDelayed(timerRunnable, 0);
            }
        });
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        int index = -1;

        if (viewId == R.id.january) {
            index = 0;
        } else if (viewId == R.id.february) {
            index = 1;
        } else if (viewId == R.id.march) {
            index = 2;
        } else if (viewId == R.id.april) {
            index = 3;
        } else if (viewId == R.id.may) {
            index = 4;
        } else if (viewId == R.id.june) {
            index = 5;
        } else if (viewId == R.id.july) {
            index = 6;
        }
        else if (viewId == R.id.august) {
            index = 7;
        } else if (viewId == R.id.september) {
            index = 8;
        }  else if (viewId == R.id.october) {
            index = 9;
        }  else if (viewId == R.id.november) {
            index = 10;
        }  else if (viewId == R.id.december) {
            index = 11;
        }

        if (index != -1) {
            if (isMoved[index]) {
                // Move back to original position
                moveImageView(view, originalX[index], originalY[index]);
                isMoved[index] = false;
                currentMonthPosition[index] = -1; // Reset current position
                adjustCurrentPosition(); // Adjust currentPosition after moving back
            } else {
                // Teleport the clicked ImageView to predefined drop target positions
                int x = 0, y = 0;
                switch (currentPosition) {
                    case 0:
                        x = findViewById(R.id.drop_target_january).getLeft();
                        y = findViewById(R.id.drop_target_january).getTop();
                        currentMonthPosition[index] = 0;
                        break;
                    case 1:
                        x = findViewById(R.id.drop_target_february).getLeft();
                        y = findViewById(R.id.drop_target_february).getTop();
                        currentMonthPosition[index] = 1;
                        break;
                    case 2:
                        x = findViewById(R.id.drop_target_march).getLeft();
                        y = findViewById(R.id.drop_target_march).getTop();
                        currentMonthPosition[index] = 2;
                        break;
                    case 3:
                        x = findViewById(R.id.drop_target_april).getLeft();
                        y = findViewById(R.id.drop_target_april).getTop();
                        currentMonthPosition[index] = 3;
                        break;
                    case 4:
                        x = findViewById(R.id.drop_target_may).getLeft();
                        y = findViewById(R.id.drop_target_may).getTop();
                        currentMonthPosition[index] = 4;
                        break;
                    case 5:
                        x = findViewById(R.id.drop_target_june).getLeft();
                        y = findViewById(R.id.drop_target_june).getTop();
                        currentMonthPosition[index] = 5;
                        break;
                    case 6:
                        x = findViewById(R.id.drop_target_july).getLeft();
                        y = findViewById(R.id.drop_target_july).getTop();
                        currentMonthPosition[index] = 6;
                        break;
                    case 7:
                        x = findViewById(R.id.drop_target_august).getLeft();
                        y = findViewById(R.id.drop_target_august).getTop();
                        currentMonthPosition[index] = 7;
                        break;
                    case 8:
                        x = findViewById(R.id.drop_target_september).getLeft();
                        y = findViewById(R.id.drop_target_september).getTop();
                        currentMonthPosition[index] = 8;
                        break;
                    case 9:
                        x = findViewById(R.id.drop_target_october).getLeft();
                        y = findViewById(R.id.drop_target_october).getTop();
                        currentMonthPosition[index] = 9;
                        break;
                    case 10:
                        x = findViewById(R.id.drop_target_november).getLeft();
                        y = findViewById(R.id.drop_target_november).getTop();
                        currentMonthPosition[index] = 10;
                        break;
                    case 11:
                        x = findViewById(R.id.drop_target_december).getLeft();
                        y = findViewById(R.id.drop_target_december).getTop();
                        currentMonthPosition[index] = 11;
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
        return currentMonthPosition[0] == 0 && currentMonthPosition[1] == 1 &&
                currentMonthPosition[2] == 2 && currentMonthPosition[3] == 3 &&
                currentMonthPosition[4] == 4 && currentMonthPosition[5] == 5 &&
                currentMonthPosition[6] == 6 && currentMonthPosition[7] == 7 &&
                currentMonthPosition[8] == 8 && currentMonthPosition[9] == 9 &&
                currentMonthPosition[10] == 10 && currentMonthPosition[11] == 11;
    }
}
