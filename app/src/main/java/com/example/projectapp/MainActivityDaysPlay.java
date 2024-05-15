package com.example.projectapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivityDaysPlay extends AppCompatActivity implements View.OnClickListener {

    private int currentPosition = 0; // To keep track of the current position

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

        // Set OnClickListener for each ImageView
        sundayImageView.setOnClickListener(this);
        mondayImageView.setOnClickListener(this);
        tuesdayImageView.setOnClickListener(this);
        wednesdayImageView.setOnClickListener(this);
        thursdayImageView.setOnClickListener(this);
        fridayImageView.setOnClickListener(this);
        saturdayImageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        // Teleport the clicked ImageView to predefined drop target positions
        int x, y;
        switch (currentPosition) {
            case 0:
                x = findViewById(R.id.drop_target_sunday).getLeft();
                y = findViewById(R.id.drop_target_sunday).getTop();
                break;
            case 1:
                x = findViewById(R.id.drop_target_monday).getLeft();
                y = findViewById(R.id.drop_target_monday).getTop();
                break;
            case 2:
                x = findViewById(R.id.drop_target_tuesday).getLeft();
                y = findViewById(R.id.drop_target_tuesday).getTop();
                break;
            case 3:
                x = findViewById(R.id.drop_target_wednesday).getLeft();
                y = findViewById(R.id.drop_target_wednesday).getTop();
                break;
            case 4:
                x = findViewById(R.id.drop_target_thursday).getLeft();
                y = findViewById(R.id.drop_target_thursday).getTop();
                break;
            case 5:
                x = findViewById(R.id.drop_target_friday).getLeft();
                y = findViewById(R.id.drop_target_friday).getTop();
                break;
            case 6:
                x = findViewById(R.id.drop_target_saturday).getLeft();
                y = findViewById(R.id.drop_target_saturday).getTop();
                break;
            default:
                return;
        }
        moveImageView(view, x, y);
        currentPosition++; // Increment current position
    }

    private void moveImageView(View view, int x, int y) {
        // Set the position of the clicked ImageView
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
        layoutParams.leftMargin = x;
        layoutParams.topMargin = y;
        view.setLayoutParams(layoutParams);
    }
}
