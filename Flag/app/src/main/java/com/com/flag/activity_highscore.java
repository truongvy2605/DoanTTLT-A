package com.com.flag;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class activity_highscore extends Activity {
    TextView Txt1;
    Button PlayGame, Exit;
    int HighScore;
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
        Txt1 = (TextView)findViewById(R.id.TextHSScore);
        LoadHighScore();
        Txt1.setText(""+ HighScore);
        PlayGame = (Button)findViewById(R.id.ButtonHSPG);
        Exit= (Button)findViewById(R.id.ButtonHSExit);
        Intent callerIntent=getIntent();
        Bundle packageFromCaller= callerIntent.getBundleExtra("MyPackage");
        PlayGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent
                        (activity_highscore.this,  activity_diff.class);
                startActivity(intent);
            }
        });
        Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent
                        (activity_highscore.this,  MainActivity.class);
                startActivity(intent);
            }
        });
    }
    void LoadHighScore(){
        SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        if (sharedPreferences !=null){
            HighScore = sharedPreferences.getInt("H",0);
        }
    }
}