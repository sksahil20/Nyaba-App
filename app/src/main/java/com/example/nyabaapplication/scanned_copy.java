package com.example.nyabaapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class scanned_copy extends AppCompatActivity {

    Button finishBtn;

//    ImageView scanIDBack;
//
//    ImageView scanIDFront;

    String storeFrontScan;
    String storeBackScan="BACK_ID_JPEG.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanned_copy);

        readFrontImgFromFile();
        readBackImgFromFile();


        finishBtn = findViewById(R.id.ScanfinishBtn);
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(scanned_copy.this, WellDone.class);
                startActivity(intent);
                finish();
            }
        });


    }

    public void readFrontImgFromFile()
    {
        Bundle b = new Bundle();
        b = getIntent().getExtras();
        String name = b.getString("frontScan");

        ImageView scanIDFront = findViewById(R.id.Scan_id_front);
      File imgFileFront = new File(name);
        if(imgFileFront.exists())
        {
            scanIDFront.setImageURI(Uri.fromFile(imgFileFront));

        }
    }

    public void readBackImgFromFile()
    {
        Bundle b = new Bundle();
        b = getIntent().getExtras();
        String name = b.getString("backScan");

        ImageView scanIDBack = findViewById(R.id.Scan_id_back);
        File imgFileBack = new  File(name);
        if(imgFileBack.exists())
        {
            scanIDBack.setImageURI(Uri.fromFile(imgFileBack));

        }
    }


}
