package com.example.triviaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

    private TextView counter;
    private TextView quesTextView;
    private Button trueButton;
    private Button falseButton;
    private ImageButton prevButton;
    private ImageButton nextButton;
    private int currentIndex = 0;
    private List<Question> questionList;
    private boolean answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nextButton = findViewById(R.id.nextButton);
        prevButton = findViewById(R.id.prevButton);
        trueButton = findViewById(R.id.trueButton);
        falseButton = findViewById(R.id.falseButton);
        quesTextView = findViewById(R.id.quesTextView);
        counter = findViewById(R.id.counter);

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
                updateQuestion(prevButton);
                break;
            case R.id.nextButton:
                updateQuestion(nextButton);
                break;
            case R.id.trueButton:
                if (answer == true) {
                    Toast.makeText(MainActivity.this, "Correct Answer :) ", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(MainActivity.this, "InCorrect Answer :( ", Toast.LENGTH_SHORT).show();
                break;
            case R.id.falseButton:
                if (answer == false) {
                    Toast.makeText(MainActivity.this, "Correct Answer :) ", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(MainActivity.this, "InCorrect Answer :( ", Toast.LENGTH_SHORT).show();
                break;
        }

    }
}