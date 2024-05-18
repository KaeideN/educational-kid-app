package com.example.projectapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button button1, button2, button3, button4, button5, button6, button7, button8, button9, button10, button11, button12;
    TextView dialog_langueage;
    RelativeLayout show_lan_dialog;
    Context context;
    Resources resources;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferred language
        sharedPreferences = getSharedPreferences("Settings", MODE_PRIVATE);
        String language = LocaleHelper.getLanguage(this);
        context = LocaleHelper.setLocale(MainActivity.this, language);
        resources = context.getResources();

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        show_lan_dialog = findViewById(R.id.showlangdialog);
        dialog_langueage = findViewById(R.id.dialog_language);

        show_lan_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] Language = {"EN", "TR"};

                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setTitle("SELECT A LANGUAGE")
                        .setSingleChoiceItems(Language, language.equals("en") ? 0 : 1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String selectedLanguage = Language[i];
                                dialog_langueage.setText(selectedLanguage);

                                // Save selected language
                                LocaleHelper.saveLanguage(MainActivity.this, selectedLanguage.equals("EN") ? "en" : "tr");

                                // Update language in the app
                                context = LocaleHelper.setLocale(MainActivity.this, selectedLanguage.equals("EN") ? "en" : "tr");
                                resources = context.getResources();

                                updateTexts();
                                dialogInterface.dismiss();
                            }
                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                dialogInterface.dismiss();
                            }
                        });
                builder.create().show();
            }
        });

        // Find buttons
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);
        button7 = findViewById(R.id.button7);
        button8 = findViewById(R.id.button8);
        button9 = findViewById(R.id.button9);
        button10 = findViewById(R.id.button10);
        button11 = findViewById(R.id.button11);
        button12 = findViewById(R.id.button12);

        // Set button click listeners
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityWithLanguage(MainActivityAnalogClock.class);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityWithLanguage(MainActivityDigitalClock.class);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityWithLanguage(MainActivityDays.class);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityWithLanguage(MainActivityMonths.class);
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityWithLanguage(MainActivity123.class);
            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityWithLanguage(MainActivity321.class);
            }
        });
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityWithLanguage(MainActivityABC.class);
            }
        });
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityWithLanguage(MainActivityDirections.class);
            }
        });
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityWithLanguage(MainActivityMultiplication.class);
            }
        });
        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityWithLanguage(MainActivityPictures.class);
            }
        });
        button11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityWithLanguage(RedBall.class);
            }
        });
        button12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityWithLanguage(MainActivitySeasons.class);
            }
        });
        // Update texts based on the current language
        updateTexts();
    }

    private void updateTexts() {
        button1.setText(resources.getString(R.string.analog_clock));
        button2.setText(resources.getString(R.string.digital_clock));
        button3.setText(resources.getString(R.string.days));
        button4.setText(resources.getString(R.string.months));
        button7.setText(resources.getString(R.string.pronunciation));
        button8.setText(resources.getString(R.string.directions));
        button9.setText(resources.getString(R.string.multiplication));
        button10.setText(resources.getString(R.string.finding_similar_images));
        button11.setText(resources.getString(R.string.track_the_ball));
        button12.setText(resources.getString(R.string.seasons));
    }

    private void startActivityWithLanguage(Class<?> cls) {
        Intent intent = new Intent(MainActivity.this, cls);
        intent.putExtra("lang_selected", LocaleHelper.getLanguage(this));
        startActivity(intent);
    }
}
