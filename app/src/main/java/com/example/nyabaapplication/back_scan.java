package com.example.nyabaapplication;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import androidx.core.content.FileProvider;
import androidx.lifecycle.LifecycleOwner;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class back_scan extends AppCompatActivity {

    ActivityResultLauncher<Intent> activityResultLauncher1;

    private static final int PERMISSION_CODE = 101;

    public String currentPhotoPath1;

    Button backImgCap;

    Button backImgCapAgain;

    Button backNext;

    ImageView backImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_scan);

        backImg = findViewById(R.id.Image_id_back);

        backImgCap = findViewById(R.id.imgCapture_back);
        backImgCap.setVisibility(VISIBLE);

        backImgCapAgain = findViewById(R.id.Back_imgCapture_again);
        backImgCapAgain.setVisibility(GONE);

        backNext = findViewById(R.id.back_next);
        backNext.setVisibility(INVISIBLE);

        activityResultLauncher1 = registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
                , new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            File f1 = new File(currentPhotoPath1);
                            backImgCapAgain.setVisibility(VISIBLE);
                            backImgCap.setVisibility(INVISIBLE);
                            backNext.setVisibility(VISIBLE);
                            backImg.setImageURI(Uri.fromFile(f1));
                        }
                    }
                });

        backImgCap.setOnClickListener(new View.OnClickListener() {
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
                        dispatchTakePictureIntent1();
                    }
                } else {
                    //system os < marshmallow
                    dispatchTakePictureIntent1();
                }
            }
        });
    }

    public void goToScannedCopy()
    {
        Intent intent=new Intent(back_scan.this,scanned_copy.class);
        intent.putExtra("backScan", currentPhotoPath1);
        startActivity(intent);
    }

    public void next1(View view) {
        goToScannedCopy();
        Intent myIntent = new Intent(back_scan.this, scanned_copy.class);
        back_scan.this.startActivity(myIntent);
        finish();
    }

    public void scanAgainBack(View view) {
        clearMyFiles1();
        onCreateLayouts1();
        backImg.setImageDrawable(getResources().getDrawable(R.drawable.id_back));
    }

    void clearMyFiles1() {
        File imgFile1 = new File(currentPhotoPath1);
        if (imgFile1 != null) {
            imgFile1.delete();
        }
    }

    public void onCreateLayouts1() {
        backImgCap.setVisibility(VISIBLE);

        backImgCapAgain.setVisibility(GONE);

        backNext.setVisibility(INVISIBLE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {

            case PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    dispatchTakePictureIntent1();
                } else {
                    Toast.makeText(this, "permission denied...", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }

    private File createImageFile1() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "BACK_ID_JPEG_" + timeStamp;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath1 = image.getAbsolutePath();
        return image;
    }


    private void dispatchTakePictureIntent1() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile1();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.nyabaapplication.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                activityResultLauncher1.launch(takePictureIntent);
            }
        }
    }
}