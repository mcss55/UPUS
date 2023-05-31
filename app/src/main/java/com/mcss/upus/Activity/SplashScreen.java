package com.mcss.upus.Activity;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.mcss.upus.Core.PackageReceiverSec;
import com.mcss.upus.R;
import com.mcss.upus.Util.BackgroundWorks;
import com.mcss.upus.Util.CommonActivityStyle;

@SuppressLint("CustomSplashScreen")
public class SplashScreen extends AppCompatActivity {

    PackageReceiverSec packageReceiverSec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        CommonActivityStyle.set(this);

        LottieAnimationView lottieAnimationView = (LottieAnimationView) findViewById(R.id.my_anim_splash);


        lottieAnimationView.setScaleType(LottieAnimationView.ScaleType.CENTER_CROP);

        View decorView = getWindow().getDecorView();
        int flags = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE;
        decorView.setSystemUiVisibility(flags);


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        packageReceiverSec = new PackageReceiverSec();

        BackgroundWorks.set(packageReceiverSec, this);



        lottieAnimationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                startActivity(new Intent(SplashScreen.this,MainActivity.class));
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

    }
    @Override
    protected void onDestroy() {
        unregisterReceiver(packageReceiverSec);
        super.onDestroy();
    }
}