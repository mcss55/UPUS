package com.mcss.upus.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ActionTypes;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.TouchListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.mcss.upus.R;
import com.mcss.upus.Util.CommonActivityStyle;

import java.util.ArrayList;

public class SlideShow extends AppCompatActivity {


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_show);

        CommonActivityStyle.set(this);
        ImageSlider imageSlider=findViewById(R.id.imageSlider);
        ArrayList<SlideModel> slideModelArrayList = new ArrayList<>();

        slideModelArrayList.add(new SlideModel(R.drawable.image_1, ScaleTypes.FIT));
        slideModelArrayList.add(new SlideModel(R.drawable.image_2, ScaleTypes.FIT));
        slideModelArrayList.add(new SlideModel(R.drawable.image_3, ScaleTypes.FIT));
        slideModelArrayList.add(new SlideModel(R.drawable.image_4, ScaleTypes.FIT));

        imageSlider.setImageList(slideModelArrayList);
        imageSlider.setTouchListener(actionTypes -> {
            finish();
        });


    }

    @Override
    public void onBackPressed() {
        // Disable the back button while in SlideShow
        // Uncomment the next line if you want to prevent going back to MainActivity using the back button
        // super.onBackPressed();
    }

}