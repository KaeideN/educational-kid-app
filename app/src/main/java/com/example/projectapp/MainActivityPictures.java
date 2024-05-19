package com.example.projectapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivityPictures extends AppCompatActivity {
    Button buttonPlay;
    TextView header,textView2,textView3,textView4,textView5,textView7,textView9;
    private Context context;
    private Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_pictures);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SharedPreferences sharedPreferences = getSharedPreferences("Settings", MODE_PRIVATE);
        String language = LocaleHelper.getLanguage(this);
        context = LocaleHelper.setLocale(MainActivityPictures.this, language);
        resources = context.getResources();

        header = findViewById(R.id.header);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        textView4 = findViewById(R.id.textView4);
        textView5 = findViewById(R.id.textView5);
        textView7 = findViewById(R.id.textView7);
        textView9 = findViewById(R.id.textView9);

        header.setText(resources.getString(R.string.TutorialHeaderPictures));
        textView2.setText(resources.getString(R.string.startTheGame));
        textView3.setText(resources.getString(R.string.first_infoPictures));
        textView4.setText(resources.getString(R.string.lookAtThePicture));
        textView5.setText(resources.getString(R.string.second_infoPictures));
        textView7.setText(resources.getString(R.string.chooseTheSimilar));
        textView9.setText(resources.getString(R.string.third_infoPictures));

        buttonPlay = findViewById(R.id.buttonPlay);
        buttonPlay.setText(resources.getString(R.string.play));
    buttonPlay.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivityPictures.this, MainActivityPicturesPlay.class);
            startActivity(intent);
        }
    });
    }
}