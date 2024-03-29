package com.example.walkmenu.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.example.walkmenu.R;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public int[] slide_images = {
            R.drawable.ic_chef_1,
            R.drawable.chef2,
            R.drawable.ic_icecream
    };

    public String[] slide_headings = {
            "Book at your Comfort",
            "Best Service",
            "Skip Hassle"
    };

    public String[] slide_descriptions = {
            "all menus of " +
                    "food items in " +
                    "one single click",
            "dine in, take away, home delivery options",
            "choose from handcarts, sweet corners, bakery and restaurants"
    };

    public SliderAdapter(Context context){
        this.context = context;
    }
    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (ConstraintLayout)object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout,container,false);
        ImageView slideImageView = (ImageView) view.findViewById(R.id.slide_image);
        TextView slideDescription = (TextView) view.findViewById(R.id.slide_desc);

        slideImageView.setImageResource(slide_images[position]);
        slideDescription.setText(slide_descriptions[position]);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);

    }
}
