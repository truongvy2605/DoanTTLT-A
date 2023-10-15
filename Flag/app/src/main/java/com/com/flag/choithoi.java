package com.com.flag;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

public class choithoi extends Activity {
    Button Choi,Diemcao,Thoat;
    @Override
    public void onCreate (@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choithoi);

        Choi = (Button) findViewById(R.id.BtnChoi);
        Diemcao = (Button) findViewById(R.id.BtnDiemcao);
        Thoat = (Button) findViewById(R.id.BtnThoat);
        Thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(choithoi.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
