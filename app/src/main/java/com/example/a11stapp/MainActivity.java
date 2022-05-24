package com.example.a11stapp;

import static java.sql.DriverManager.println;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Movie;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class giMainActivity extends AppCompatActivity {
    EditText requestText;
    TextView responseText;

    static RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestText = findViewById(R.id.edit_text);
        responseText = findViewById(R.id.reponse_text);

        ArrayList<Movie> items = new ArrayList<Movie>();

        Button requestBtn = findViewById(R.id.request_btn);
        requestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeRequest();
            }
        });

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
    }

    public void makeRequest() {
        String url = requestText.getText().toString();

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        println("응답 -> " + response);
                        processResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        println("에러 -> " + error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);
        println("요청 보냄");
    }
    public void println(String data){
        responseText.append(data + "\n");

    }
    public void processResponse(String response)
    {
        Gson gson = new Gson();
        MovieList movieList = gson.fromJson(response,MovieList.class);
        println("영화 정보의 수 : " + movieList.boxOfficeResult.dailyBoxOfficeList.size());
    }
}