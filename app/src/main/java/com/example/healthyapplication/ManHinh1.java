package com.example.healthyapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ManHinh1 extends AppCompatActivity {
    EditText edtinName;
    Button btnBatDau;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh1);
        edtinName =findViewById(R.id.inputHoTen);
        btnBatDau   =   findViewById(R.id.batdaubtn);





        btnBatDau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textEntered = edtinName.getText().toString();
                Intent i  =   new Intent(ManHinh1.this, ManHinhXinChao.class);
                i.putExtra("textFromActivity1", String.valueOf(textEntered));
                startActivity(i);
            }
        });
    }
}