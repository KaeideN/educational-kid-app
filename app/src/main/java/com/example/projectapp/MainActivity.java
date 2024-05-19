package com.example.projectapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    ImageButton button1, button2, button3, button4, button5, button6, button7, button8, button9, button10, button11, button12;
    TextView dialog_language;
    RelativeLayout show_lang_dialog;
    ImageView languageFlag;
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

        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        show_lang_dialog = findViewById(R.id.showlangdialog);
        dialog_language = findViewById(R.id.dialog_language);
        languageFlag = findViewById(R.id.language_flag);
        dialog_language.setText(language.equals("en") ? "EN" : "TR");

        // Set the initial flag
        languageFlag.setImageResource(language.equals("en") ? R.drawable.english_flag : R.drawable.turkish_flag);

        show_lang_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] Language = {"English", "Türkçe"};

                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setTitle("SELECT A LANGUAGE")
                        .setSingleChoiceItems(Language, language.equals("en") ? 0 : 1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String selectedLanguage = Language[i];
                                dialog_language.setText(selectedLanguage);

                                // Save selected language
                                LocaleHelper.saveLanguage(MainActivity.this, selectedLanguage.equals("English") ? "en" : "tr");

                                // Restart activity to apply changes
                                restartActivity();
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

        // Update texts and button images based on the current language
        updateButtonImages(language);
    }

    private void updateButtonImages(String language) {
        if (language.equals("en")) {
            button1.setBackgroundResource(R.drawable.analogbutton_en);
            button2.setBackgroundResource(R.drawable.digitalbutton_en);
            button3.setBackgroundResource(R.drawable.daysbutton_en);
            button4.setBackgroundResource(R.drawable.monthsbutton_en);
            button5.setBackgroundResource(R.drawable.numbersfun_en);
            button6.setBackgroundResource(R.drawable.reverseit_en);
            button7.setBackgroundResource(R.drawable.pronounce_en);
            button8.setBackgroundResource(R.drawable.directions_en);
            button9.setBackgroundResource(R.drawable.multiplefun_en);
            button10.setBackgroundResource(R.drawable.findtheimage_en);
            button11.setBackgroundResource(R.drawable.tracktheball_en);
            button12.setBackgroundResource(R.drawable.seasons_en);
        } else if (language.equals("tr")) {
            button1.setBackgroundResource(R.drawable.analogbutton_tr);
            button2.setBackgroundResource(R.drawable.digitalbutton_tr);
            button3.setBackgroundResource(R.drawable.daysbutton_tr);
            button4.setBackgroundResource(R.drawable.monthsbutton_tr);
            button5.setBackgroundResource(R.drawable.numbersfun_tr);
            button6.setBackgroundResource(R.drawable.reverseit_tr);
            button7.setBackgroundResource(R.drawable.pronounce_tr);
            button8.setBackgroundResource(R.drawable.directions_tr);
            button9.setBackgroundResource(R.drawable.multiplefun_tr);
            button10.setBackgroundResource(R.drawable.findtheimage_tr);
            button11.setBackgroundResource(R.drawable.tracktheball_tr);
            button12.setBackgroundResource(R.drawable.seasons_tr);
        }
    }

    private void restartActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    private void startActivityWithLanguage(Class<?> cls) {
        Intent intent = new Intent(MainActivity.this, cls);
        intent.putExtra("lang_selected", LocaleHelper.getLanguage(this));
        startActivity(intent);
    }
}
