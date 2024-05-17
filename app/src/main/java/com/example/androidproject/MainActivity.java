package com.example.androidproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
 private Button productBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        productBtn = findViewById(R.id.goToProducts);
        productBtn.setOnClickListener(v -> {
            Intent intent =new Intent(this, LoginActivity.class);
            startActivity(intent);
        });


    }
}