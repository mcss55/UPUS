package com.mcss.upus.CustomSlider;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.mcss.upus.R;

import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class CustomImageSlider extends FrameLayout {

    private ViewPager viewPager;
    private Timer timer;
    Activity activity;

    public CustomImageSlider(Context context, Activity activity) {
        super(context);
        this.activity = activity;
        init();
    }

    public CustomImageSlider(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomImageSlider(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.custom_image_slider, this, true);
        viewPager = findViewById(R.id.viewPager);

        // Disable user swiping on the ViewPager
        viewPager.setOnTouchListener((v, event) -> true);
    }

    public void setSlides(List<SlideModel> slideModelList) {
        SlidePagerAdapter adapter = new SlidePagerAdapter(slideModelList);
        viewPager.setAdapter(adapter);

        // Start the auto-scroll timer
        startAutoScroll();
    }

    public void startAutoScroll() {
        if (timer == null) {
            timer = new Timer();
            timer.scheduleAtFixedRate(new AutoScrollTask(activity), 2000, 3000);
        }
    }

    public void stopAutoScroll() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private static class SlidePagerAdapter extends PagerAdapter {
        private final List<SlideModel> slideModelList;
        private Activity activity;

        public SlidePagerAdapter(List<SlideModel> slideModelList, @NonNull Activity activity) {
            this.slideModelList = slideModelList;
            this.activity = activity;
        }


        public SlidePagerAdapter(List<SlideModel> slideModelList) {
            this.slideModelList = slideModelList;
        }

        @Override
        public int getCount() {
            return slideModelList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView imageView = slideModelList.get(position).getImageView();

            activity.runOnUiThread(() -> container.addView(imageView));

            return imageView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }

    private class AutoScrollTask extends TimerTask {
        private Activity activity;

        public AutoScrollTask(@NonNull Activity activity) {
            this.activity = activity;
        }
        @Override
        public void run() {
            int currentItem = viewPager.getCurrentItem();
            int nextItem = (currentItem + 1) % viewPager.getAdapter().getCount();

            activity.runOnUiThread(() -> viewPager.setCurrentItem(nextItem, true));
        }
    }
}