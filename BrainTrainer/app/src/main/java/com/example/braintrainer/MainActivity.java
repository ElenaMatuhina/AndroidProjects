package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.braintrainer.databinding.ActivityMainBinding;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Locale;

import static android.util.Log.println;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private TextView textViewScore;
    private TextView textViewTimer;
    private TextView textViewQues;
    private TextView textViewOpinion0;
    private TextView textViewOpinion1;
    private TextView textViewOpinion2;
    private TextView textViewOpinion3;
    private ArrayList<TextView> options = new ArrayList<>();

    private String question;
    private int rigthAnswer;
    private int rigthAnswerPosition;
    private boolean isPositive;
    private int min = 5;
    private int max = 30;
    private int countOfQuestions = 0;
    private int countOfAnswers = 0;
    private Boolean gameOver = false;
    private Toast toastTrue;
    private Toast toastFalse;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        textViewScore = binding.textViewScore;
        textViewTimer = binding.textViewTimer;
        textViewQues = binding.textViewQues;
        textViewOpinion0 = binding.textViewOpinion0;
        textViewOpinion1 = binding.textViewOpinion1;
        textViewOpinion2 = binding.textViewOpinion2;
        textViewOpinion3 = binding.textViewOpinion3;
        toastTrue = Toast.makeText(MainActivity.this, "Верно", Toast.LENGTH_SHORT);
        toastFalse = Toast.makeText(MainActivity.this, "Неверно", Toast.LENGTH_SHORT);
        options.add(textViewOpinion0);
        options.add(textViewOpinion1);
        options.add(textViewOpinion2);
        options.add(textViewOpinion3);
        playNext();
        Log.d("My test", "onCreateMain");
        CountDownTimer timer = new CountDownTimer(35000, 1000) {
            @Override
            public void onTick(long l) {
                textViewTimer.setText(getTime(l));
                if(l < 10000) {
                    textViewTimer.setTextColor(Color.RED);
                }

            }


            @Override
            public void onFinish() {
                gameOver = true;
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                int max = preferences.getInt("max", 0);
                if (countOfAnswers >= max) {
                    preferences.edit().putInt("max", countOfAnswers).apply();

                }
                Intent intent = new Intent(MainActivity.this, ScoreActivity.class);
                intent.putExtra("scope", countOfAnswers);
                startActivity(intent);
                toastFalse.cancel();
                toastTrue.cancel();
                Log.d("My test", "onFinish");
            }
        };
        timer.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("My test", "onResumeMain");
    }


    @SuppressLint("SetTextI18n")
    private void playNext() {
        generateQuestion();
        for(int i = 0; i < options.size(); i++) {
            if (i == rigthAnswerPosition) {
                options.get(i).setText(Integer.toString(rigthAnswer));
            } else {
                options.get(i).setText(Integer.toString(generateWrongAnswer()));
            }
        }
        String score = String.format("%s / %s", countOfAnswers, countOfQuestions);
        textViewScore.setText(score);
    }

    private void generateQuestion() {
        int a = (int) (Math.random()*(max-min + 1)+min);
        int b = (int) (Math.random()*(max-min + 1)+min);
        int mark = (int) (Math.random()*2);
        isPositive = mark ==1;
        if (isPositive) {
            rigthAnswer = a + b;
            question = String.format("%s + %s", a, b);
        } else {
            rigthAnswer = a - b;
            question = String.format("%s - %s", a, b);
        }
        textViewQues.setText(question);
        rigthAnswerPosition = (int) (Math.random()*4); //индекс правильного ответа

    }

    private int generateWrongAnswer() {
        int result;
        do {
            result = (int) (Math.random()*max * 2 +1) - (max - min);
        } while (result == rigthAnswer);
        return result;
    }

    private String getTime(long millis) {
        int seconds = (int) (millis / 1000);
        int minutes = seconds / 60;
        seconds = seconds % 60;
        return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

    }

    public void onClickAnswer(View view) {
        if (!gameOver) {
            TextView textView = (TextView) view;
            String answer = textView.getText().toString();
            int choseanswer = Integer.parseInt(answer);
            if (choseanswer == rigthAnswer) {
                countOfAnswers++;
                toastTrue.show();
            } else {
                toastFalse.show();
            }
            countOfQuestions++;
            playNext();


        }
    }


}