package com.example.mygame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.randomButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectRandom(v);
            }
        });
        findViewById(R.id.snakeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectSnake(v);
            }
        });
    }

    public void selectRandom(View view) {
        setContentView(new RandomBallView(this));
    }

    public void selectSnake(View view) {
        setContentView(new SnakeView(this));
    }
}