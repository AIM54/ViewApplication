package com.bian.viewapplication.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bian.viewapplication.R;

public class ViewPagerActivity extends AppCompatActivity {
    private ViewPager mainViewPager;
    private LinearLayout linearLayout;
    private PagerAdapter mPagerAdapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return 20;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = View.inflate(ViewPagerActivity.this, R.layout.item_my_card_layout, null);
            TextView tv = (TextView) view.findViewById(R.id.tv_content);
            tv.setText(Integer.toString(position));
            view.setTag(position);
            view.setOnClickListener(v -> {
              if (linearLayout.getVisibility()==View.VISIBLE){
                  linearLayout.setVisibility(View.GONE);
              }else{
                  linearLayout.setVisibility(View.VISIBLE);
              }
            });
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        initViewPager();
    }

    private void initViewPager() {
        mainViewPager = findViewById(R.id.vp_main);
        mainViewPager.setAdapter(mPagerAdapter);
        linearLayout=findViewById(R.id.ll_main);
    }

}
