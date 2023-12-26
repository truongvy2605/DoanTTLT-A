package com.com.flag.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.com.flag.R;

public class activityPlayStyle extends Activity {
    Button Quiz, Survival, FCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playstyle);
        Quiz = (Button)findViewById(R.id.ButtonStyleQuiz);
        Quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activityPlayStyle.this, activityDifficulty.class);
                intent.putExtra("style", "quizgame");
                startActivity(intent);
            }
        });
        Survival = (Button)findViewById(R.id.ButtonStyleSurv);
        Survival.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activityPlayStyle.this, activityGamePlay.class);
                intent.putExtra("path", "Data_Survival");
                startActivity(intent);
            }
        });
        FCard = (Button) findViewById(R.id.ButtonStyleFCard);
        FCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activityPlayStyle.this, activityDifficulty.class);
                intent.putExtra("style", "flashcard");
                startActivity(intent);
            }
        });
    }
}