package com.example.nyabaapplication;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraX;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureConfig;
import androidx.camera.core.Preview;
import androidx.camera.core.PreviewConfig;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.lifecycle.LifecycleOwner;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Rational;
import android.util.Size;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class Front_Scan extends AppCompatActivity {

    ActivityResultLauncher<Intent> activityResultLauncher;

    private static final int PERMISSION_CODE = 101;
    private static final int IMAGE_CAPTURE_CODE = 102;

    private static final int MY_PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE = 101;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
    String currentPhotoPath;

    Button frontImgCap;
    Button frontImgCapAgain;
    Button frontNext;

    ImageView frontImg;

    Uri image_uri;

    private int REQUEST_CODE_PERMISSIONS = 101;
    private String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.CAMERA",
            "android.permission.WRITE_EXTERNAL_STORAGE"};
    AutoFitTextureView textureView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_scan);
        frontImg = findViewById(R.id.Image_id_front);
        frontImgCap = findViewById(R.id.imgCapture_front);
        frontImgCap.setVisibility(VISIBLE);
        frontImgCapAgain = findViewById(R.id.Front_imgCapture_again);
        frontImgCapAgain.setVisibility(GONE);
        frontNext = findViewById(R.id.front_next);
        frontNext.setVisibility(INVISIBLE);

        ImageCaptureConfig imageCaptureConfig = new ImageCaptureConfig.Builder().setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY)
                .setTargetRotation(getWindowManager().getDefaultDisplay().getRotation()).build();
        final ImageCapture imgCap = new ImageCapture(imageCaptureConfig);

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
                , new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            File f = new File(currentPhotoPath);
                            frontImgCapAgain.setVisibility(VISIBLE);
                            frontImgCap.setVisibility(INVISIBLE);
                            frontNext.setVisibility(VISIBLE);
                            frontImg.setImageURI(Uri.fromFile(f));
                        }
                    }
                });

        frontImgCap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if system os is >= marshmallow, request runtime permission

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) ==
                            PackageManager.PERMISSION_DENIED ||
                            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                                    PackageManager.PERMISSION_DENIED) {
                        //permission not enable, request it
                        String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        //show popup to request permissions
                        requestPermissions(permission, PERMISSION_CODE);
                    } else {
                        //permission already given
                        dispatchTakePictureIntent();
                    }
                } else {
                    //system os < marshmallow
                    dispatchTakePictureIntent();

                }

            }
        });

    }




   /* private void openCamera() {

        File file = new File(getFilesDir() + "/Nyaba.documnet.front.jpg");

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the camera");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        //Camera intent
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
       // startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);
        activityResultLauncher.launch(cameraIntent);

    }*/

    public void next(View view) {
        Intent myIntent = new Intent(Front_Scan.this, back_scan.class);
        Front_Scan.this.startActivity(myIntent);
        finish();
    }

    public void scanAgain(View view) {
        clearMyFiles();
        onCreateLayouts();
    }

    void clearMyFiles() {
        File imgFile = new File(getFilesDir() + "/Nyaba.documnet.front.jpg");
        if (imgFile != null) {
            imgFile.delete();
        }
    }

    public void onCreateLayouts() {
        frontImgCap.setVisibility(VISIBLE);

        frontImgCapAgain.setVisibility(GONE);

        frontNext.setVisibility(INVISIBLE);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {

            case PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    dispatchTakePictureIntent();
                } else {
                    Toast.makeText(this, "permission denied...", Toast.LENGTH_SHORT).show();
                }
                return;

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @NonNull Intent data) {
        //called when image was captured from camera
        super.onActivityResult(requestCode, resultCode, data);


    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "FRONT_ID_JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.nyabaapplication.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                activityResultLauncher.launch(takePictureIntent);
            }
        }
    }

}
