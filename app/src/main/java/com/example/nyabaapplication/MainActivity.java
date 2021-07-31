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

        picker1 = findViewById(R.id.numberpicker_main_picker);
        picker1.setMaxValue(4);
        picker1.setMinValue(0);
        pickerVals = new String[]{"1", "2", "3", "4", "5"};
        picker1.setDisplayedValues(pickerVals);

        picker1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                int valuePicker1 = picker1.getValue();
                Log.d("picker value", pickerVals[valuePicker1]);
            }
        });

    }
}