package com.example.walkmenu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import com.example.walkmenu.helper.SliderAdapter;

public class IntroActivity extends AppCompatActivity {

    private ViewPager mSlideViewPager;
    private ConstraintLayout mDotLayout;
    private TextView[] mDots;

    private Button next;
    private Button back;
    private Button getstarted;
    private Button skip;
    private int mCurrentPage;

    private SliderAdapter sliderAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        next = findViewById(R.id.nextButton);
        back = findViewById(R.id.backButton);
        getstarted = findViewById(R.id.getstarted);
        skip = findViewById(R.id.skipButton);

        mSlideViewPager = findViewById(R.id.SlideviewPager);

        sliderAdapter = new SliderAdapter(this);
        mSlideViewPager.setAdapter(sliderAdapter);
        mSlideViewPager.addOnPageChangeListener(viewListner);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSlideViewPager.setCurrentItem(mCurrentPage+1);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSlideViewPager.setCurrentItem(mCurrentPage-1);
            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getSharedPreferences("prefs",MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("firstStart",false);
                editor.apply();
                Intent intent = new Intent(IntroActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        getstarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getSharedPreferences("prefs",MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("firstStart",false);
                editor.apply();
                Intent intent = new Intent(IntroActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    ViewPager.OnPageChangeListener viewListner = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            mCurrentPage = position;
            if(position==0){
                next.setEnabled(true);
                back.setEnabled(false);
                back.setVisibility(View.INVISIBLE);
                getstarted.setVisibility(View.INVISIBLE);
                next.setText(R.string.next);
                back.setText("");
            } else if (position == 2){
                next.setEnabled(true);
                back.setEnabled(true);
                back.setVisibility(View.VISIBLE);
                getstarted.setVisibility(View.VISIBLE);
                next.setVisibility(View.INVISIBLE);
                back.setText(R.string.back);
            } else {
                next.setEnabled(true);
                back.setEnabled(true);
                back.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);
                getstarted.setVisibility(View.INVISIBLE);
                next.setText(R.string.next);
                back.setText(R.string.back);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

}
