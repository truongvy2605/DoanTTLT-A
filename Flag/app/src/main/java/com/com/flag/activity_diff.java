package com.com.flag;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class activity_diff extends Activity {
    Button Easy, Hard, Expert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_gamediff);
        Easy = (Button)findViewById(R.id.ButtonDiffEasy);
        Easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Hard = (Button)findViewById(R.id.ButtonDiffHard);
        Hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Expert = (Button) findViewById(R.id.ButtonDiffExpert);
        Expert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}