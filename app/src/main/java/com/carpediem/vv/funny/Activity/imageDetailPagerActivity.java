package com.carpediem.vv.funny.Activity;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.carpediem.vv.funny.R;
import com.carpediem.vv.funny.weight.PinchImageView;

import java.util.ArrayList;

public class imageDetailPagerActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private MyAdapter myAdapter;
    ArrayList<View> viewList = new ArrayList<View>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_deatil_pager);
        ArrayList<String> gameLink = getIntent().getStringArrayListExtra("gameLink");
        for (int i = 0; i < gameLink.size(); i++) {
            PinchImageView imageView = new PinchImageView(this);
            Glide.with(this).load(gameLink.get(i)).into(imageView);
            viewList.add(imageView);
        }
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        myAdapter = new MyAdapter();
        viewPager.setAdapter(myAdapter);
    }

    private class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return viewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(viewList.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewList.get(position));
            return viewList.get(position);
        }
    }
}
