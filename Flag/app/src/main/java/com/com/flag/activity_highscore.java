package com.com.flag;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class activity_highscore extends Activity {
    TextView Txt1;
    int HighScore;
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.test_gamehighscore);
        Txt1 = (TextView)findViewById(R.id.TextHSScore);
        LoadHighScore();
        Txt1.setText(""+ HighScore);
    }
    void LoadHighScore(){
        HighScore = 80;
        }
}