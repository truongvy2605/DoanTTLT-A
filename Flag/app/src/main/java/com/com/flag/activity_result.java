package com.com.flag;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
public class activity_result extends Activity {
    Button TryAgain, TapOut;
    TextView Score;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_gameresult);
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
                        (activity_result.this,  activity_diff.class);
                startActivity(intent);
            }
        });
        TapOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent
                        (activity_result.this,  MainActivity.class);
                startActivity(intent);
            }
        });
    }
}