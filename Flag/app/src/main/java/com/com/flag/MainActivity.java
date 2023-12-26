package com.com.flag;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import com.com.flag.activity.activityEntryPoint;
import com.com.flag.entity.SoundBackground;

public class MainActivity extends AppCompatActivity {
    public static SoundBackground soundBackground = new SoundBackground();
    public static String PlayerName = "Anonymous";
    Button PlayGame, MuteSound;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /* BACKGROUND MUCSIC SETUP */
        soundBackground.GenerateBackgroundMusic(this);
        soundBackground.Mute();

        /* FUNCTION PLAY GAME */
        PlayGame = findViewById(R.id.ButtonMainPlay);
        PlayGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayerName = ((EditText) findViewById(R.id.EditMainName)).getText().toString();
                /* Khởi tạo Intent, bắt đầu activity tiếp theo */
                Intent intent = new Intent(MainActivity.this, activityEntryPoint.class);
                startActivity(intent);
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
