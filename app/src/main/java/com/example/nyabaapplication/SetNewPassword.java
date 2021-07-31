package com.example.nyabaapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SetNewPassword extends AppCompatActivity {

    Button setNewPassBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_new_password);

        setNewPassBtn = findViewById(R.id.set_new_pass_button);
        setNewPassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SetNewPassword.this,NewPasswordSuccessMessage.class);
                startActivity(intent);
                finish();
            }
        });

    }
}