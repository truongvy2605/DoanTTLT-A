package com.com.flag;

import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;

public class mucdo extends choithoi{

    Button De,Kho;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mucdo);

        De = (Button) findViewById(R.id.BtnDe);
        Kho = (Button) findViewById(R.id.BtnKho);
    }
}
