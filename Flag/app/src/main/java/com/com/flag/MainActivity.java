package com.com.flag;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import com.com.flag.activity.activityEntryPoint;

public class MainActivity extends AppCompatActivity {
    public static MediaPlayer BackgroundMusic;
    public static String PlayerName;
    Button PlayGame;
    EditText Name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /* BACKGROUND MUCSIC SETUP */
        BackgroundMusic = MediaPlayer.create(this, R.raw.backgroundmusic);
        BackgroundMusic.setLooping(true);
        Name = findViewById(R.id.EditMainName);
        /* FUNCTION PLAY GAME */
        PlayGame = findViewById(R.id.ButtonMainPlay);
        PlayGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Khởi tạo Intent, bắt đầu activity tiếp theo */
                Intent intent = new Intent(MainActivity.this, activityEntryPoint.class);
                String PlayerName = Name.getText().toString();
                /* Khởi tạo Bundle, lưu trữ thông tin người chơi */
                Bundle bundle = new Bundle();
                bundle.putString("PlayerName", PlayerName);
                intent.putExtra("MyPackage", bundle);
                BackgroundMusic.start();
                startActivity(intent);
            }
        });
    }
}
