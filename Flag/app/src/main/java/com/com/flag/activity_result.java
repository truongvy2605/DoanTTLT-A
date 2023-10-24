package com.com.flag;

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
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_gameresult);
        TryAgain = (Button)findViewById(R.id.ButtonResultTA);
        TapOut = (Button)findViewById(R.id.ButtonResultTO);
        Score = (TextView) findViewById(R.id.TextResultScore);
        Intent callerIntent=getIntent();
        Bundle packageFromCaller= callerIntent.getBundleExtra("MyPackage");
        Score.setText(packageFromCaller.getInt("KQ")+"/"+ packageFromCaller.getInt("Socau"));
        TryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TapOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}