package com.example.nyabaapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PersonalInfo extends AppCompatActivity {

    Button nextStepBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        nextStepBtn = findViewById(R.id.PI_next_btn);
        nextStepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalInfo.this,DocVerify.class);
                startActivity(intent);
            }
        });

    }
}