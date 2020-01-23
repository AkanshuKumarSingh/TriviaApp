package com.example.triviaapp.data;

import android.app.DownloadManager;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.triviaapp.controller.AppController;
import com.example.triviaapp.model.Question;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import static com.example.triviaapp.controller.AppController.TAG;

public class QuestionBank {

    private String url = "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";
    private ArrayList<Question> questionArrayList = new ArrayList<>();

    public List<Question> getQuestion(){
        Log.d("Reach", "getQuestion: ");
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Game", "onResponse: " + response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        AppController.getInstance().addToRequestQueue(jsonArrayRequest);
        Log.d("gotit", "getQuestion: " + jsonArrayRequest.getUrl());
        return null;
    }

}
