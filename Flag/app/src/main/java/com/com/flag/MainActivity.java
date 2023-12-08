package com.com.flag;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    static MediaPlayer BackgroundMusic;
    Button PlayGame;
    EditText Name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BackgroundMusic = MediaPlayer.create(this, R.raw.backgroundmusic);
        Name = findViewById(R.id.EditMainName);
        /* CHỨC NĂNG PLAY GAME */
        PlayGame = findViewById(R.id.ButtonMainPlay);
        PlayGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Khởi tạo Intent, bắt đầu activity tiếp theo */
                Intent intent = new Intent(MainActivity.this, activity_entry.class);
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
