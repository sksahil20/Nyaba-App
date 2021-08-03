package com.example.nyabaapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

public class MainActivity extends AppCompatActivity {

    NumberPicker picker1;
    String[] pickerVals;
    Button bookNowBtn;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bookNowBtn = findViewById(R.id.book_now_btn);
        bookNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,PersonalInfo.class);
                startActivity(intent);
            }
        });

        NumberPicker picker = findViewById(R.id.numberpicker_main_picker);
        picker.setMaxValue(20);
        picker.setMinValue(1);
    }
}