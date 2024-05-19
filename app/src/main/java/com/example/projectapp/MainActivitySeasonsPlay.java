package com.example.projectapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.content.Intent;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivitySeasonsPlay extends AppCompatActivity {

    TextView quizText, aans, bans, cans, dans;
    List<QuesitionsItem> quesitionsItems;
    int currentQuestions = 0, wrong = 0, correct = 0;
    Intent intent;
    private Context context;
    private Resources resources;
    private String language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_seasons_play);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SharedPreferences sharedPreferences = getSharedPreferences("Settings", MODE_PRIVATE);
        language = LocaleHelper.getLanguage(this);
        context = LocaleHelper.setLocale(MainActivitySeasonsPlay.this, language);
        resources = context.getResources();

        quizText = findViewById(R.id.quizText);
        aans = findViewById(R.id.aanswer);
        bans = findViewById(R.id.banswer);
        cans = findViewById(R.id.canswer);
        dans = findViewById(R.id.danswer);

        loadAllQuestions();
        Collections.shuffle(quesitionsItems);
        setQuestionScreen(currentQuestions);

        aans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAnswerClick(aans, quesitionsItems.get(currentQuestions).getAnswer1());
            }
        });

        bans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAnswerClick(bans, quesitionsItems.get(currentQuestions).getAnswer2());
            }
        });

        cans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAnswerClick(cans, quesitionsItems.get(currentQuestions).getAnswer3());
            }
        });

        dans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAnswerClick(dans, quesitionsItems.get(currentQuestions).getAnswer4());
            }
        });
    }

    private void handleAnswerClick(TextView answerView, String selectedAnswer) {
        if (selectedAnswer.equals(quesitionsItems.get(currentQuestions).getCorrect())) {
            correct++;
            answerView.setBackgroundResource(R.drawable.right_answer_bg);
        } else {
            wrong++;
            answerView.setBackgroundResource(R.drawable.wrong_answer);
        }
        //answerView.setTextColor(getResources().getColor(R.color.white));

        if (currentQuestions < quesitionsItems.size() - 1) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    currentQuestions++;
                    setQuestionScreen(currentQuestions);
                    resetAnswerView(answerView);
                }
            }, 700);
        } else {
            Intent intent = new Intent(MainActivitySeasonsPlay.this, ResultActivitySeasons.class);
            intent.putExtra("correct", correct);
            intent.putExtra("wrong", wrong);
            startActivity(intent);
            finish();
        }
    }

    private void resetAnswerView(TextView answerView) {
        answerView.setBackgroundResource(R.drawable.answers_background);
        //answerView.setTextColor(getResources().getColor(R.color.white));
    }

    private void setQuestionScreen(int currentQuestions) {
        quizText.setText(quesitionsItems.get(currentQuestions).getQuestions());
        aans.setText(quesitionsItems.get(currentQuestions).getAnswer1());
        bans.setText(quesitionsItems.get(currentQuestions).getAnswer2());
        cans.setText(quesitionsItems.get(currentQuestions).getAnswer3());
        dans.setText(quesitionsItems.get(currentQuestions).getAnswer4());
    }

    private void loadAllQuestions() {
        quesitionsItems = new ArrayList<>();
        String jsonFileName = language.equals("tr") ? "questionsSeasons_tr.json" : "questionsSeasons_en.json";
        String jsonquiz = loadJsonFromAsset(jsonFileName);
        try {
            JSONObject jsonObject = new JSONObject(jsonquiz);
            JSONArray questions = jsonObject.getJSONArray("questions");
            for (int i = 0; i < questions.length(); i++) {
                JSONObject question = questions.getJSONObject(i);

                String questionsString = question.getString("question");
                String answer1String = question.getString("answer1");
                String answer2String = question.getString("answer2");
                String answer3String = question.getString("answer3");
                String answer4String = question.getString("answer4");
                String correctString = question.getString("correct");

                quesitionsItems.add(new QuesitionsItem(questionsString, answer1String, answer2String, answer3String, answer4String, correctString));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String loadJsonFromAsset(String fileName) {
        String json = "";
        try {
            InputStream inputStream = getAssets().open(fileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }
}
