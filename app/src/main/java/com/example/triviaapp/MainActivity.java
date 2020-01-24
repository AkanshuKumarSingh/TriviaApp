package com.example.triviaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.toolbox.Volley;
import com.example.triviaapp.data.AnswerListAsyncResponse;
import com.example.triviaapp.data.QuestionBank;
import com.example.triviaapp.model.Question;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new QuestionBank().getQuestion(new AnswerListAsyncResponse() {
            @Override
            public void processFinished(ArrayList<Question> questionArrayList) {
                Log.d("finished", "processFinished: " + questionArrayList);
            }
        });
    }

}
