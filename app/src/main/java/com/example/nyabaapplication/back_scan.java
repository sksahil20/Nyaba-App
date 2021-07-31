package com.example.nyabaapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.os.Bundle;
import android.util.Rational;
import android.util.Size;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.File;

public class back_scan extends AppCompatActivity {

    private int REQUEST_CODE_PERMISSIONS = 101;
    private String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.CAMERA",
            "android.permission.WRITE_EXTERNAL_STORAGE"};
    AutoFitTextureView textureView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_scan);

        textureView = findViewById(R.id.view_finder);
        if(allPermissionGranted()) {
            startCamera();
        }
        else{
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        }
    }

    public void scan(View view) {
        Intent myIntent = new Intent(back_scan.this,scanned_copy.class);
        back_scan.this.startActivity(myIntent);
        finish();
    }

    public void arrow(View view) {
        Intent myIntent = new Intent(back_scan.this,Front_Scan.class);
        back_scan.this.startActivity(myIntent);
        finish();
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
        CameraX.unbindAll();
        Rational aspectRatio = new Rational(textureView.getWidth(), textureView.getHeight());
        Size screen = new Size(textureView.getWidth(), textureView.getHeight());
        PreviewConfig pConfig = new PreviewConfig.Builder().setTargetAspectRatio(aspectRatio).setTargetResolution(screen).build();
        Preview preview = new Preview(pConfig);
        preview.setOnPreviewOutputUpdateListener(
                new Preview.OnPreviewOutputUpdateListener() {
                    @Override
                    public void onUpdated(Preview.PreviewOutput output) {
                        ViewGroup parent = (ViewGroup) textureView.getParent();
                        parent.removeView(textureView);
                        parent.addView(textureView, 0);

                        textureView.setSurfaceTexture(output.getSurfaceTexture());
                    }
                });
        ImageCaptureConfig imageCaptureConfig = new ImageCaptureConfig.Builder().setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY)
                .setTargetRotation(getWindowManager().getDefaultDisplay().getRotation()).build();
        final ImageCapture imgCap = new ImageCapture(imageCaptureConfig);
//        findViewById(R.id.imgCapture_back).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                File file = new File(/*Environment.getExternalStorageDirectory()*/ "/sdcard/photos/DCIM(0)/Camera/CameraX_" + System.currentTimeMillis() + ".jpg");
//                imgCap.takePicture(file, new ImageCapture.OnImageSavedListener() {
//                    @Override
//                    public void onImageSaved(@NonNull File file) {
//                        String msg = "Pic captured at " + file.getAbsolutePath();
//                        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();
//                    }
//
//                    @Override
//                    public void onError(@NonNull ImageCapture.UseCaseError useCaseError, @NonNull String message, @Nullable Throwable cause) {
//                        String msg = "Pic capture failed : " + message;
//                        Toast.makeText(getBaseContext(), msg,Toast.LENGTH_LONG).show();
//                        if(cause != null){
//                            cause.printStackTrace();
//                        }
//                    }
//                });
//            }
//        });
        CameraX.bindToLifecycle((LifecycleOwner) this, preview, imgCap);
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
}