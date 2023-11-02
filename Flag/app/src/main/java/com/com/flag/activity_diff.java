package com.com.flag;

import android.app.Activity;
import android.content.Intent;
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
                Intent intent = new Intent(activity_diff.this, activity_gameplay_2.class);
                startActivity(intent);
            }
        });
        Hard = (Button)findViewById(R.id.ButtonDiffHard);
        Hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_diff.this, activity_gameplay_hard_1.class);
                startActivity(intent);
            }
        });
        Expert = (Button) findViewById(R.id.ButtonDiffExpert);
        Expert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_diff.this, activity_gameplay_expert_1.class);
                startActivity(intent);
            }
        });
    }
}