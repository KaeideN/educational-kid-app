package com.example.projectapp;

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

        TextView quizText,aans,bans,cans,dans;
        List<QuesitionsItem> quesitionsItems;
        int currentQuestions =0 ,wrong=0,correct=0;
        Intent intent;

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

            quizText=findViewById(R.id.quizText);
            aans=findViewById(R.id.aanswer);
            bans=findViewById(R.id.banswer);
            cans=findViewById(R.id.canswer);
            dans=findViewById(R.id.danswer);

            loadAllQuestions();
            Collections.shuffle(quesitionsItems);
            setQuestionScreen(currentQuestions);

            aans.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(quesitionsItems.get(currentQuestions).getAnswer1().equals(quesitionsItems.get(currentQuestions).getCorrect())){
                        correct++;
                        aans.setBackgroundResource(R.drawable.right_answer_bg);
                        aans.setTextColor(getResources().getColor(R.color.white));
                    }else {
                        wrong++;
                        aans.setBackgroundResource(R.drawable.wrong_answer);
                        aans.setTextColor(getResources().getColor(R.color.white));
                    }

                    if(currentQuestions<quesitionsItems.size()-1){
                        Handler handler = new  Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                currentQuestions++;
                                setQuestionScreen(currentQuestions);
                                aans.setBackgroundResource(R.drawable.answers_background);
                                aans.setTextColor(getResources().getColor(R.color.white));
                            }
                        },700);
                    }else {
                        Intent intent = new Intent(MainActivitySeasonsPlay.this,ResultActivitySeasons.class);
                        intent.putExtra("correct",correct);
                        intent.putExtra("wrong",wrong);
                        startActivity(intent);
                        finish();
                    }
                }
            });


            bans.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(quesitionsItems.get(currentQuestions).getAnswer2().equals(quesitionsItems.get(currentQuestions).getCorrect())){
                        correct++;
                        bans.setBackgroundResource(R.drawable.right_answer_bg);
                        //bans.setTextColor(getResources().getColor(R.color.white));
                    }else {
                        wrong++;
                        bans.setBackgroundResource(R.drawable.wrong_answer);
                        bans.setTextColor(getResources().getColor(R.color.white));
                    }

                    if(currentQuestions<quesitionsItems.size()-1){
                        Handler handler = new  Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                currentQuestions++;
                                setQuestionScreen(currentQuestions);
                                bans.setBackgroundResource(R.drawable.answers_background);
                                bans.setTextColor(getResources().getColor(R.color.white));
                            }
                        },700);
                    }else {
                        Intent intent = new Intent(MainActivitySeasonsPlay.this,ResultActivitySeasons.class);
                        intent.putExtra("correct",correct);
                        intent.putExtra("wrong",wrong);
                        startActivity(intent);
                        finish();
                    }
                }
            });
            cans.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(quesitionsItems.get(currentQuestions).getAnswer3().equals(quesitionsItems.get(currentQuestions).getCorrect())){
                        correct++;
                        cans.setBackgroundResource(R.drawable.right_answer_bg);
                        //cans.setTextColor(getResources().getColor(R.color.white));
                    }else {
                        wrong++;
                        cans.setBackgroundResource(R.drawable.wrong_answer);
                        cans.setTextColor(getResources().getColor(R.color.white));
                    }

                    if(currentQuestions<quesitionsItems.size()-1){
                        Handler handler = new  Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                currentQuestions++;
                                setQuestionScreen(currentQuestions);
                                cans.setBackgroundResource(R.drawable.answers_background);;
                                cans.setTextColor(getResources().getColor(R.color.white));
                            }
                        },700);
                    }else {
                        Intent intent = new Intent(MainActivitySeasonsPlay.this,ResultActivitySeasons.class);
                        intent.putExtra("correct",correct);
                        intent.putExtra("wrong",wrong);
                        startActivity(intent);
                        finish();
                    }
                }
            });
            dans.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(quesitionsItems.get(currentQuestions).getAnswer4().equals(quesitionsItems.get(currentQuestions).getCorrect())){
                        correct++;
                        dans.setBackgroundResource(R.drawable.right_answer_bg);
                        dans.setTextColor(getResources().getColor(R.color.white));
                    }else {
                        wrong++;
                        dans.setBackgroundResource(R.drawable.wrong_answer);
                        dans.setTextColor(getResources().getColor(R.color.white));
                    }

                    if(currentQuestions<quesitionsItems.size()-1){
                        Handler handler = new  Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                currentQuestions++;
                                setQuestionScreen(currentQuestions);
                                dans.setBackgroundResource(R.drawable.answers_background);;
                                dans.setTextColor(getResources().getColor(R.color.white));
                            }
                        },700);
                    }else {
                        Intent intent = new Intent(MainActivitySeasonsPlay.this,ResultActivitySeasons.class);
                        intent.putExtra("correct",correct);
                        intent.putExtra("wrong",wrong);
                        startActivity(intent);
                        finish();
                    }
                }
            });
        }
        private void setQuestionScreen(int currentQuestions) {
            quizText.setText(quesitionsItems.get(currentQuestions).getQuestions());
            aans.setText(quesitionsItems.get(currentQuestions).getAnswer1());
            aans.setTextColor(getResources().getColor(R.color.white));
            bans.setText(quesitionsItems.get(currentQuestions).getAnswer2());
            bans.setTextColor(getResources().getColor(R.color.white));
            cans.setText(quesitionsItems.get(currentQuestions).getAnswer3());
            cans.setTextColor(getResources().getColor(R.color.white));
            dans.setText(quesitionsItems.get(currentQuestions).getAnswer4());
            dans.setTextColor(getResources().getColor(R.color.white));
        }

        private void loadAllQuestions() {
            quesitionsItems = new ArrayList<>();
            String jsonquiz = loadJsonFromAsset("questionsSeasons.json");
            try{
                JSONObject jsonObject =  new JSONObject(jsonquiz);
                JSONArray questions = jsonObject.getJSONArray("questions");
                for(int i=0;i<questions.length();i++){
                    JSONObject question = questions.getJSONObject(i);

                    String questionsString = question.getString("question");
                    String answer1String = question.getString("answer1");
                    String answer2String = question.getString("answer2");
                    String answer3String = question.getString("answer3");
                    String answer4String = question.getString("answer4");
                    String correctString = question.getString("correct");

                    quesitionsItems.add(new QuesitionsItem(questionsString,answer1String,answer2String,answer3String,answer4String,correctString));
                }
            }catch(JSONException e){
                e.printStackTrace();
            }
        }

        private String loadJsonFromAsset(String s) {
            String json = "";
            try {
                InputStream inputStream = getAssets().open(s);
                int size = inputStream.available();
                byte[] buffer = new byte[size];
                inputStream.read(buffer);
                inputStream.close();
                json = new String(buffer,"UTF-8");
            }catch (IOException e){
                e.printStackTrace();
            }
            return json;
        }

}
