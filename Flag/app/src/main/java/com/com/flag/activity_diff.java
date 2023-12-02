package com.com.flag;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class activity_diff extends Activity {
    Button Easy, Hard, Expert;
    String style;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            style = intent.getStringExtra("style");
        }
        else {
            style = "minitest";
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diff);
        Easy = (Button)findViewById(R.id.ButtonDiffEasy);
        Easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_diff.this, activity_gameplay.class);
                intent.putExtra("diff", "easy");
                intent.putExtra("style", style);
                startActivity(intent);
            }
        });
        Hard = (Button)findViewById(R.id.ButtonDiffHard);
        Hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_diff.this, activity_gameplay.class);
                intent.putExtra("diff", "hard");
                intent.putExtra("style", style);
                startActivity(intent);
            }
        });
        Expert = (Button) findViewById(R.id.ButtonDiffExpert);
        Expert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_diff.this, activity_gameplay.class);
                intent.putExtra("diff", "expert");
                intent.putExtra("style", style);
                startActivity(intent);
            }
        });
    }
}