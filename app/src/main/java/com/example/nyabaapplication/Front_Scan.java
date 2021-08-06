package com.example.nyabaapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraX;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureConfig;
import androidx.camera.core.Preview;
import androidx.camera.core.PreviewConfig;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Rational;
import android.util.Size;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class Front_Scan extends AppCompatActivity {

    private int REQUEST_CODE_PERMISSIONS = 101;
    private String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.CAMERA",
            "android.permission.WRITE_EXTERNAL_STORAGE"};
    AutoFitTextureView textureView;
    String storeFrontScan="/Nyaba_document_front.jpg";
    android.widget.Button scanAgain;
    ImageView scannedImg;
    android.widget.Button ScanNow;
    android.widget.Button next;
    TextView frontText;
    ImageView backArrow;
    TextView frontScanText;
    ImageView idFront;
    android.widget.Button startScan;
    TextureView scanner;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_scan);
        textureView = findViewById(R.id.frontScanner);
        views();
        onCreateLayouts();
        if(allPermissionGranted()) {
            startCamera();
        }
        else{
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        }
    }

    private boolean allPermissionGranted() {
        System.out.println("allPermissionGranted: start");
        for(String permission : REQUIRED_PERMISSIONS){

            if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED){
                System.out.println("allPermissionGranted: false");
                return false;
            }
        }
        System.out.println("allPermissionGranted: true");
        return true;
    }

    private void startCamera() {
        System.out.print(">>>>>>>>>>>>>>>>>>>>>>START CAMERA<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        CameraX.unbindAll();
        System.out.print("................................unbind..........................");
        Rational aspectRatio = new Rational(textureView.getWidth(), textureView.getHeight());
        System.out.print("''''''''''''''''''''''''''Rational Ratio''''''''''''''''''''''''''''''");
        Size screen = new Size(textureView.getWidth(), textureView.getHeight());
        System.out.print("''''''''''''''''''''''''''Size Screen''''''''''''''''''''''''''''''");
        PreviewConfig pConfig = new PreviewConfig.Builder().setTargetAspectRatio(aspectRatio).setTargetResolution(screen).build();
        System.out.print("*******************************Preview config*********************************");
        Preview preview = new Preview(pConfig);
        System.out.print("&&&&&&&&&&&&&&&&&&&&&& NEW PREVIEW &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
        preview.setOnPreviewOutputUpdateListener(
                new Preview.OnPreviewOutputUpdateListener() {
                    @Override
                    public void onUpdated(Preview.PreviewOutput output) {
                        System.out.print("&&&&&&&&&&&&&&&&&&&&&& onUpdate &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
                        ViewGroup parent = (ViewGroup) textureView.getParent();
                        System.out.print("&&&&&&&&&&&&&&&&&&&&&& View Group &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
                        parent.removeView(textureView);
                        System.out.print("&&&&&&&&&&&&&&&&&&&&&& Remove VIEW &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
                        parent.addView(textureView, 0);
                        System.out.print("&&&&&&&&&&&&&&&&&&&&&& ADD VIEW &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");

                        textureView.setSurfaceTexture(output.getSurfaceTexture());
                    }
                });
        ImageCaptureConfig imageCaptureConfig = new ImageCaptureConfig.Builder().setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY)
                .setTargetRotation(getWindowManager().getDefaultDisplay().getRotation()).build();
        final ImageCapture imgCap = new ImageCapture(imageCaptureConfig);
        System.out.print("&&&&&&&&&&&&&&&&&&&&&& Image Capture &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
        findViewById(R.id.imgCapture_front).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(getFilesDir()+storeFrontScan);
                System.out.print("&&&&&&&&&&&&&&&&&&&&&& File Created &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
                System.out.print("&&&&&&&&&&&&&&&&&&&&&& File Name----"+getFilesDir()+storeFrontScan);

                imgCap.takePicture(file, new ImageCapture.OnImageSavedListener() {
                    @Override
                    public void onImageSaved(@NonNull File file) {
                        String msg = "Pic captured at " + file.getAbsolutePath();
                        System.out.print("&&&&&&&&&&&&&&&&&&&&&& Captured at-----"+file.getAbsolutePath());
                        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();
                        layoutChangesAfterScanning();
                        System.out.print("&&&&&&&&&&&&&&&&&&&&&& Layout Changed &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
                        readImgFromFile(v);
                        System.out.print("&&&&&&&&&&&&&&&&&&&&&& read method called &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
                    }


                    @Override
                    public void onError(@NonNull ImageCapture.UseCaseError useCaseError, @NonNull String message, Throwable cause) {
                        String msg = "Pic capture failed : " + message;
                        Toast.makeText(getBaseContext(), msg,Toast.LENGTH_LONG).show();
                        if(cause != null){
                            cause.printStackTrace();
                        }
                    }
               });
            }
        });
        CameraX.bindToLifecycle((LifecycleOwner) this, preview, imgCap);
    }


    public void readImgFromFile(View view)
    {
        scannedImg.setVisibility(view.VISIBLE);

        File imgFile = new  File(getFilesDir()+storeFrontScan);
        if(imgFile.exists())
        {
            scannedImg.setImageURI(Uri.fromFile(imgFile));
            System.out.print("&&&&&&&&&&&&&&&&&&&&&& image read----"+getFilesDir()+storeFrontScan);

        }
    }

    void clearMyFiles() {
        File imgFile = new File(getFilesDir() + storeFrontScan);
        Log.d("clearing: ", "start");
        if(imgFile!=null) {
            Log.d("clearing: ", "does exist? " + imgFile.exists());
            Log.d("clearing: ", "does exist? " + imgFile.getAbsolutePath());
            boolean b = imgFile.delete();
            Log.d("clearing: ", "" + b);
            Log.d("clearing: ", "does exist? " + imgFile.exists());
            Log.d("clearing: ", "does exist? " + imgFile.getAbsolutePath());
            System.out.print("+++++++++++++++++DELETE+++++++++++++++++++++++++");
        }
        }

        public void next(View view) {
        Intent myIntent = new Intent(Front_Scan.this,back_scan.class);
        Front_Scan.this.startActivity(myIntent);
        finish();
    }
    public void arrow(View view) {
        Intent myIntent = new Intent(Front_Scan.this, DocVerify.class);
        Front_Scan.this.startActivity(myIntent);
        finish();
    }

    public void startScan(View view) {
        scanner.setVisibility(View.VISIBLE);
        ScanNow.setVisibility(View.VISIBLE);

        startScan.setVisibility(View.INVISIBLE);
        frontText.setVisibility(View.INVISIBLE);
        frontScanText.setVisibility(View.INVISIBLE);
        idFront.setVisibility(View.INVISIBLE);

    }

    public void onCreateLayouts()
    {
        scanner.setVisibility(View.INVISIBLE);
        scannedImg.setVisibility(View.INVISIBLE);
        scanAgain.setVisibility(View.INVISIBLE);
        next.setVisibility(View.INVISIBLE);
        ScanNow.setVisibility(View.INVISIBLE);
    }

    public void layoutAfterScanAgain()
    {
       scanner.setVisibility(View.VISIBLE);
       ScanNow.setVisibility(View.VISIBLE);
    }

    public void layoutChangesAfterScanning()
    {
       scanner.setVisibility(View.INVISIBLE);
       scanAgain.setVisibility(View.VISIBLE);
       scannedImg.setVisibility(View.VISIBLE);
       ScanNow.setVisibility(View.INVISIBLE);
       next.setVisibility(View.VISIBLE);
       idFront.setVisibility(View.INVISIBLE);
       backArrow.setVisibility(View.VISIBLE);
    }

    public void views()
    {
        scanAgain=findViewById(R.id.imgCapture_again);
        scannedImg= findViewById(R.id.scannedFrontImage);
        ScanNow =findViewById(R.id.imgCapture_front);
        next=findViewById(R.id.next);
        frontText=findViewById(R.id.frontScanText);
        backArrow=findViewById(R.id.imageView_back_arrow);
        frontScanText=findViewById(R.id.frontSide);
        idFront=findViewById(R.id.idFront);
        startScan=findViewById(R.id.startScan);
        scanner= findViewById(R.id.frontScanner);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionGranted()) {
                startCamera();
            } else {
                Toast.makeText(this, "Permissions not granted by the user.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    public void scanAgainFront(View view) {
        System.out.print("______________________________________DELETE___________________________________");
        clearMyFiles();
        System.out.print("$$$$$$$$$$$$$$$$$$$$$$$$$$$DELETE$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        onCreateLayouts();
        layoutAfterScanAgain();
    }
}