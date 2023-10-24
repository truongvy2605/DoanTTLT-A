package com.com.flag;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button BT;
    EditText Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BT = (Button) findViewById(R.id.ButtonMainPlay);
        Name = (EditText) findViewById(R.id.EditMainName);
        BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent
                        (MainActivity.this, activity_entry.class);
                String PlayerName = Name.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString("PlayerName", PlayerName);
                intent.putExtra("MyPackage", bundle);
                startActivity(intent);
            }
        });
    }
}