package com.example.nyabaapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class scanned_copy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanned_copy);


    }

    public void finish(View view) {
        Intent myIntent = new Intent(scanned_copy.this, WellDone.class);
        scanned_copy.this.startActivity(myIntent);
        finish();
    }

    public void arrow(View view) {
        Intent myIntent = new Intent(scanned_copy.this, back_scan.class);
        scanned_copy.this.startActivity(myIntent);
        finish();
    }

    public void fscan_again(View view) {
        Intent myIntent = new Intent(scanned_copy.this, Front_Scan.class);
        scanned_copy.this.startActivity(myIntent);
        finish();
    }

    public void bscan_again(View view) {
        Intent myIntent = new Intent(scanned_copy.this, back_scan.class);
        scanned_copy.this.startActivity(myIntent);
        finish();


    }

  }
