package com.com.flag.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.com.flag.R;

import java.util.Objects;

public class activityDifficulty extends Activity {
    Button Easy, Hard, Expert;
    String style;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent previousintent = getIntent();
        if (previousintent != null) {
            style = previousintent.getStringExtra("style");
        }
        else {
            style = "quizgame";
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty);
        Easy = (Button)findViewById(R.id.ButtonDiffEasy);
        Easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Objects.equals(style, "flashcard")) {
                    intent = new Intent(activityDifficulty.this, activityFlashCard.class);
                }
                else {
                    intent = new Intent(activityDifficulty.this, activityGamePlay.class);
                }
                intent.putExtra("path", "Data_Easy");
                startActivity(intent);
            }
        });
        Hard = (Button)findViewById(R.id.ButtonDiffHard);
        Hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Objects.equals(style, "flashcard")) {
                    intent = new Intent(activityDifficulty.this, activityFlashCard.class);
                }
                else {
                    intent = new Intent(activityDifficulty.this, activityGamePlay.class);
                }
                intent.putExtra("path", "Data_Hard");
                startActivity(intent);
            }
        });
        Expert = (Button) findViewById(R.id.ButtonDiffExpert);
        Expert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Objects.equals(style, "flashcard")) {
                    intent = new Intent(activityDifficulty.this, activityFlashCard.class);
                }
                else {
                    intent = new Intent(activityDifficulty.this, activityGamePlay.class);
                }
                intent.putExtra("path", "Data_Expert");
                startActivity(intent);
            }
        });
    }
}