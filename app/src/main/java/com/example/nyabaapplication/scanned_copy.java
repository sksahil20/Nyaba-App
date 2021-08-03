package com.example.nyabaapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

public class scanned_copy extends AppCompatActivity {

    Button finishBtn;
    String storeFrontScan="Nyaba.document.front.jpg";
    String storeBackScan="Nyaba.document.back.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanned_copy);
        readFrontImgFromFile();
        readBackImgFromFile();

         finishBtn =findViewById(R.id.finishBtn);
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(scanned_copy.this, WellDone.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void arrow(View view) {
        Intent myIntent = new Intent(scanned_copy.this, back_scan.class);
        scanned_copy.this.startActivity(myIntent);
        finish();
    }

    public void readFrontImgFromFile()
    {
        ImageView scannedImg= findViewById(R.id.imageView3);
        scannedImg.setVisibility(View.VISIBLE);
        File imgFile = new  File(getFilesDir()+storeFrontScan);
        if(imgFile.exists())
        {
            scannedImg.setImageURI(Uri.fromFile(imgFile));

        }
    }

    public void readBackImgFromFile()
    {
        ImageView scannedImg= findViewById(R.id.imageView4);
        scannedImg.setVisibility(View.VISIBLE);
        File imgFile = new  File(getFilesDir()+storeBackScan);
        if(imgFile.exists())
        {
            scannedImg.setImageURI(Uri.fromFile(imgFile));

        }
    }
  }
