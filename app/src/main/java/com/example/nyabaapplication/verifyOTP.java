package com.example.nyabaapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class verifyOTP extends AppCompatActivity {

    Button resetOTPBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);

        resetOTPBtn = findViewById(R.id.pass_reset_otp_button);
        resetOTPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(verifyOTP.this,SetNewPassword.class);
                startActivity(intent);
                finish();
            }
        });
    }
}