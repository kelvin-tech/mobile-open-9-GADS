package com.example.myapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class VotingActivity extends AppCompatActivity {
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting);

       if (getIntent().hasExtra("email")){
           email = getIntent().getStringExtra("email");
        }

    }
}