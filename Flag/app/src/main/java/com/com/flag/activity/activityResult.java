package com.com.flag.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.com.flag.MainActivity;
import com.com.flag.R;

public class activityResult extends Activity {
    Button TryAgain, TapOut;
    TextView Score;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        TryAgain = (Button)findViewById(R.id.ButtonResultTA);
        TapOut = (Button)findViewById(R.id.ButtonResultTO);
        Score = (TextView) findViewById(R.id.TextResultScore);
        Intent callerIntent=getIntent();
        Bundle packageFromCaller= callerIntent.getBundleExtra("MyPackage");
        assert packageFromCaller != null;
        Score.setText("" + packageFromCaller.getInt("Progress"));
        TryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent
                        (activityResult.this,  activityDifficulty.class);
                startActivity(intent);
            }
        });
        TapOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent
                        (activityResult.this,  MainActivity.class);
                startActivity(intent);
            }
        });
    }
}