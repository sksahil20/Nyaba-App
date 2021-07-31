package com.example.nyabaapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginMain extends AppCompatActivity {

    TextView forgetPassword;
    TextView singUp;
    Button LoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);

        forgetPassword = findViewById(R.id.openForgetPass);
        singUp = findViewById(R.id.openSingUp);
        LoginBtn = findViewById(R.id.log_main_button);

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginMain.this,ForgotPassword.class);
                startActivity(intent);
            }
        });

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginMain.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }
}