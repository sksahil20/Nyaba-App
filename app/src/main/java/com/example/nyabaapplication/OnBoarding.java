package com.example.nyabaapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OnBoarding extends AppCompatActivity {

    ViewPager viewPager;
    LinearLayout dots;

    SliderAdepter sliderAdepter;
    TextView[] sliderDots;
    Button ltsBtn;
    Animation animation;
    int currentPosition;
    Button skipBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_on_boarding);


        viewPager = findViewById(R.id.obBoardSlider);
        dots = findViewById(R.id.dots);


        sliderAdepter = new SliderAdepter(this);

        viewPager.setAdapter(sliderAdepter);

        ltsBtn = findViewById(R.id.let_btn);
        ltsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(currentPosition + 1);
            }
        });

        skipBtn = findViewById(R.id.skip_btn);


        addDots(0);
        viewPager.addOnPageChangeListener(changeListener);

    }

    public void skip(View view){
        startActivity(new Intent(this,Login.class));
        finish();
    }



    private void addDots(int position) {

        sliderDots = new TextView[3];
        dots.removeAllViews();

        for (int i = 0; i < sliderDots.length; i++) {
            sliderDots[i] = new TextView(this);
            sliderDots[i].setText(Html.fromHtml("&#8226;"));
            sliderDots[i].setTextSize(30);

            dots.addView(sliderDots[i]);
        }

        if (sliderDots.length > 0) {
            sliderDots[position].setTextColor(getResources().getColor(R.color.yellow));
        }

    }

    ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            addDots(position);
            currentPosition = position;

            if (position == 0) {
               ltsBtn.setText(R.string.next_btn);
                ltsBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(currentPosition + 1);
                    }
                });
                skipBtn.setVisibility(View.VISIBLE);
            } else if (position == 1) {
               ltsBtn.setText(R.string.next_btn);
                ltsBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(currentPosition + 1);
                    }
                });
                skipBtn.setVisibility(View.VISIBLE);
            } else {
                ltsBtn.setText(R.string.let_get_started);
                ltsBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(),Login.class);
                        startActivity(intent);
                        finish();
                    }
                });

                skipBtn.setVisibility(View.INVISIBLE);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

}