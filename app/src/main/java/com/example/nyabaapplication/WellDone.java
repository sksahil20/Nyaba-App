package com.example.nyabaapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WellDone extends AppCompatActivity {

    Button backToDash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_well_done);

        backToDash = findViewById(R.id.back_to_dash_btn);
        backToDash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WellDone.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}