package com.example.nyabaapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import androidx.viewpager2.adapter.FragmentStateAdapter;

import androidx.viewpager.widget.PagerAdapter;

public class SliderAdepter extends  PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdepter(Context context) {
        this.context=context;
    }

    int img[] = {
            R.drawable.log_mecca,
            R.drawable.walkthrough2,
            R.drawable.walkthrough3,

    };

    int headings[] = {
            R.string.first_slider_title,
            R.string.second_slider_title,
            R.string.third_slider_title,
    };

    int descriptions[] = {
            R.string.first_slider_desc,
            R.string.second_slider_desc,
            R.string.third_slider_desc,
    };

    @Override
    public int getCount() {
        return headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (ConstraintLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slides_layout,container,false);

        ImageView imageView = view.findViewById(R.id.slider_img);
        TextView heading = view.findViewById(R.id.slider_heding);
        TextView decs = view.findViewById(R.id.slider_desc);

        imageView.setImageResource(img[position]);
        heading.setText(headings[position]);
        decs.setText(descriptions[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);
    }
}
