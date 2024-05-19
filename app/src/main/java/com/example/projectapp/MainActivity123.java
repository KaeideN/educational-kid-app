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

public class MainActivity123 extends AppCompatActivity {
    Button buttonPlay;
    private Context context;
    private Resources resources;
    TextView header,textView2,textView3,textView4,textView5,textView7,textView9;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main123);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SharedPreferences sharedPreferences = getSharedPreferences("Settings", MODE_PRIVATE);
        String language = LocaleHelper.getLanguage(this);
        context = LocaleHelper.setLocale(MainActivity123.this, language);
        resources = context.getResources();

        header = findViewById(R.id.header);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        textView4 = findViewById(R.id.textView4);
        textView5 = findViewById(R.id.textView5);
        textView7 = findViewById(R.id.textView7);
        textView9 = findViewById(R.id.textView9);

        header.setText(resources.getString(R.string.TutorialHeader123));
        textView2.setText(resources.getString(R.string.sequence));
        textView3.setText(resources.getString(R.string.first_info123));
        textView4.setText(resources.getString(R.string.tap_the_buttons));
        textView5.setText(resources.getString(R.string.second_info123));
        textView7.setText(resources.getString(R.string.mistakes_happen));
        textView9.setText(resources.getString(R.string.third_info123));

        buttonPlay = findViewById(R.id.buttonPlay);
        buttonPlay.setText(resources.getString(R.string.play));
        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity123.this,MainActivity123Play.class);
                startActivity(intent);
            }
        });
    }
}