package com.com.flag;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class activity_entry extends Activity {
    Button PlayGame, HighScore, Exit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_gameentry);

        /* CHỨC NĂNG PLAY GAME */
        PlayGame = findViewById(R.id.ButtonEntryPG);
        PlayGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Khởi tạo Intent, bắt đầu activity tiếp theo */
                Intent intent = new Intent(activity_entry.this, activity_diff.class);
                startActivity(intent);
            }
        });
        /* CHỨC NĂNG HIGH SCORE */
        HighScore = findViewById(R.id.ButtonEntryHS);
        HighScore.setOnClickListener(new View.OnClickListener() {
            /* Khởi tạo Intent, bắt đầu activity tiếp theo */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_entry.this, activity_highscore.class);
                startActivity(intent);
            }
        });
        /* CHỨC NĂNG EXIT */
        Exit = findViewById(R.id.ButtonEntryExit);
        Exit.setOnClickListener(new View.OnClickListener() {
            /* Finish() để quay lại activity trước */
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}