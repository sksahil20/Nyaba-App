package com.example.nyabaapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    TextView singUp;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.log_btn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, LoginMain.class);


                Pair[] pairs = new Pair[1];

                pairs[0] = new Pair<View,String>(login,"transition_login");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this,pairs);

                startActivity(intent,options.toBundle());
            }
        });

        singUp = findViewById(R.id.sing_up_btn);
        singUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Login.this, "Create account Toast", Toast.LENGTH_SHORT).show();
            }
        });

    }
}