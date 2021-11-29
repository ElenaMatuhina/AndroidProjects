package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.braintrainer.databinding.ActivityFirstBinding;

public class FirstActivity extends AppCompatActivity {

    ActivityFirstBinding binding;
    Button buttonLevelOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityFirstBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        buttonLevelOne = binding.buttonLevelOne;
        Log.d("My test", "onCreate");
    }



    public void onClickStartOne(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("My test", "onResume");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("My test", "onStop");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("My test", "onDestroy");

    }
}