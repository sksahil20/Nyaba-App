package com.example.nyabaapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NewPasswordSuccessMessage extends AppCompatActivity {

    Button resetPassSuccessBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password_success_message);

        resetPassSuccessBtn = findViewById(R.id.pass_reset_success_back_btn);
        resetPassSuccessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewPasswordSuccessMessage.this,LoginMain.class);
                startActivity(intent);
                finish();
            }
        });

    }
}