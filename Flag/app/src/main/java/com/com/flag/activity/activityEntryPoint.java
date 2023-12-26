package com.com.flag.activity;

import static com.com.flag.MainActivity.soundBackground;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.com.flag.R;

public class activityEntryPoint extends Activity {
    Button PlayGame, HighScore, Exit, MuteSound;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrypoint);

        /* CHỨC NĂNG PLAY GAME */
        PlayGame = findViewById(R.id.ButtonEntryPG);
        PlayGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Khởi tạo Intent, bắt đầu activity tiếp theo */
                Intent intent = new Intent(activityEntryPoint.this, activityPlayStyle.class);
                startActivity(intent);
            }
        });
        /* CHỨC NĂNG HIGH SCORE */
        HighScore = findViewById(R.id.ButtonEntryHS);
        HighScore.setOnClickListener(new View.OnClickListener() {
            /* Khởi tạo Intent, bắt đầu activity tiếp theo */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activityEntryPoint.this, activityHighScore.class);
                startActivity(intent);
            }
        });
        /* CHỨC NĂNG HƯỚNG DẪN CHƠI */
        Exit = findViewById(R.id.ButtonEntryIns);
        Exit.setOnClickListener(new View.OnClickListener() {
            /* Finish() để quay lại activity trước */
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        MuteSound = findViewById(R.id.ButtonStopMusic);
        MuteSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundBackground.Mute();
            }
        });
    }
}