package com.example.nyabaapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIMER = 2000;
    ImageView backgroundImage;

    Animation slidAnimation;

    SharedPreferences onBoardingScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);


        backgroundImage = findViewById(R.id.splash_bg);

        slidAnimation = AnimationUtils.loadAnimation(this,R.anim.side_anim);

        backgroundImage.setAnimation(slidAnimation);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                onBoardingScreen = getSharedPreferences("onBoardingScreen",MODE_PRIVATE);

                boolean isFirstTime = onBoardingScreen.getBoolean("firstTime",true);

                if (isFirstTime){

                    SharedPreferences.Editor editor = onBoardingScreen.edit();
                    editor.putBoolean("firstTime",false);
                    editor.commit();

                    Intent intent = new Intent(SplashScreen.this, OnBoarding.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(SplashScreen.this, Login.class);
                    startActivity(intent);
                    finish();
                }

            }
        },SPLASH_TIMER);

    }
}