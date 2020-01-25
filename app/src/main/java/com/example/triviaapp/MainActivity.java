package com.example.triviaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.Volley;
import com.example.triviaapp.data.AnswerListAsyncResponse;
import com.example.triviaapp.data.QuestionBank;
import com.example.triviaapp.model.Question;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ValueAnimator anim;
    private TextView cs;
    private TextView counter;
    private TextView quesTextView;
    private Button trueButton;
    private Button falseButton;
    private ImageButton prevButton;
    private ImageButton nextButton;
    private int currentIndex = 0;
    private List<Question> questionList;
    private boolean answer;
    private CardView cardView;
    SharedPreferences sharedPreferences;
    int score = 0;
    int highest_score = 0;
    private TextView hs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = this.getSharedPreferences("com.example.triviaapp", Context.MODE_PRIVATE);
        nextButton = findViewById(R.id.nextButton);
        prevButton = findViewById(R.id.prevButton);
        trueButton = findViewById(R.id.trueButton);
        falseButton = findViewById(R.id.falseButton);
        quesTextView = findViewById(R.id.quesTextView);
        counter = findViewById(R.id.counter);
        cardView = findViewById(R.id.cardView);
        hs = findViewById(R.id.hs);
        cs = findViewById(R.id.cs);
        highest_score = sharedPreferences.getInt("no",0);
        hs.setText("Highest Score : " + highest_score);
        quesTextView.setBackgroundColor(getResources().getColor(R.color.back));
        questionList = new QuestionBank().getQuestion(new AnswerListAsyncResponse() {
            @Override
            public void processFinished(ArrayList<Question> questionArrayList) {
                Log.d("finished", "processFinished: " + questionArrayList);
                quesTextView.setText(questionArrayList.get(currentIndex).getAnswer());
                answer = questionArrayList.get(currentIndex).isAnswerTrue();
                currentIndex++;
                counter.setText(Integer.toString(currentIndex) + " out of 913");
            }
        });
        nextButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);
        trueButton.setOnClickListener(this);
        falseButton.setOnClickListener(this);

    }

    public void updateQuestion(View v) {
        if (v.getId() == prevButton.getId()) {
            if (currentIndex == -1) {
                currentIndex = 912;
            }
            quesTextView.setText(questionList.get(currentIndex).getAnswer());
            answer = questionList.get(currentIndex).isAnswerTrue();
            currentIndex--;
            counter.setText(Integer.toString(currentIndex + 1) + " out of 913");
            Log.d("Yo", "updateQuestion: " +
                    questionList.get(questionList.size() - 1) + " : " + questionList.size());
        } else {
            if (currentIndex == 913) {
                currentIndex = 0;
            }
            quesTextView.setText(questionList.get(currentIndex).getAnswer());
            answer = questionList.get(currentIndex).isAnswerTrue();
            currentIndex++;
            counter.setText(Integer.toString(currentIndex) + " out of 913");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.prevButton:
                anim.end();
                quesTextView.setBackgroundColor(getResources().getColor(R.color.back));
                updateQuestion(prevButton);
                break;
            case R.id.nextButton:
                anim.end();
                quesTextView.setBackgroundColor(getResources().getColor(R.color.back));
                updateQuestion(nextButton);
                break;
            case R.id.trueButton:
                if (answer == true) {
                    Toast.makeText(MainActivity.this, "Correct Answer :) ", Toast.LENGTH_SHORT).show();
                    shakeAnimation();
                    score++;
                    } else {
                    Toast.makeText(MainActivity.this, "InCorrect Answer :( ", Toast.LENGTH_SHORT).show();
                    shakeAnimation1();
                }
                increaseScore(1);
                break;
            case R.id.falseButton:
                if (answer == false) {
                    Toast.makeText(MainActivity.this, "Correct Answer :) ", Toast.LENGTH_SHORT).show();
                    shakeAnimation();
                    } else {
                    Toast.makeText(MainActivity.this, "InCorrect Answer :( ", Toast.LENGTH_SHORT).show();
                }
                increaseScore(-1);
                break;
        }
    }

    void increaseScore(int i){
        highest_score = sharedPreferences.getInt("no",0);
        if( i == -1) {
            score++;
            cs.setText("Current sc : " + Integer.toString(score));
            if(highest_score < score){
                sharedPreferences.edit().putInt("no",score).apply();
                hs.setText("Highest score : " + score);
            }
        }else{
            score--;
            cs.setText("Current sc : " + Integer.toString(score));
        }
    }

    void shakeAnimation(){
        anim = ValueAnimator.ofFloat(0, 3);
        anim.setDuration(500);

        final float[] hsv;
        final int[] runColor = new int[1];
        int hue = 0;
        hsv = new float[3]; // Transition color
        hsv[1] = 1;
        hsv[2] = 1;
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                hsv[0] = 360 * animation.getAnimatedFraction();

                runColor[0] = Color.HSVToColor(hsv);
                quesTextView.setBackgroundColor(runColor[0]);
            }
        });

        anim.setRepeatCount(30);
        anim.start();
    }

    void shakeAnimation1(){
        CardView cardView = findViewById(R.id.cardView);
        quesTextView.setBackgroundColor(getResources().getColor(R.color.wrong));
        cardView.animate().setDuration(700).rotationBy(360);
    }

}