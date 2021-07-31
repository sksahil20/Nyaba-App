package com.example.nyabaapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class DocVerify extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_verify);

    }
        public void nationalID(View view) {
            Intent myIntent = new Intent(DocVerify.this, Front_Scan.class);
            DocVerify.this.startActivity(myIntent);
            finish();
        }

        public void passport(View view) {
            Intent myIntent = new Intent(DocVerify.this, Front_Scan.class);
            DocVerify.this.startActivity(myIntent);
            finish();
        }

        public void driverLicence(View view) {
            Intent myIntent = new Intent(DocVerify.this, Front_Scan.class);
            DocVerify.this.startActivity(myIntent);
            finish();
        }

        public void finish(View view) {
        }

    }