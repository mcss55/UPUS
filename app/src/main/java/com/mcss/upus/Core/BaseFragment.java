package com.mcss.upus.Core;

import android.content.Intent;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.mcss.upus.Activity.SlideShow;


public abstract class BaseFragment extends Fragment {

    private Handler handler;
    private Runnable runnable;
    private boolean isTouched;

    @Override
    public void onResume() {
        super.onResume();
        startTimer();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopTimer();
    }

    private void startTimer() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (!isTouched) {
                    // Start the SlideShow activity
                    Intent intent = new Intent(getActivity(), SlideShow.class);
                    startActivity(intent);
                } else {
                    isTouched = false; // Reset the touch flag
                    startTimer(); // Restart the timer
                }
            }
        };
        handler.postDelayed(runnable, 3000); // Delay for 3 seconds
    }

    private void stopTimer() {
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }

    public boolean onTouch(View v, MotionEvent event) {
        isTouched = true; // Set the touch flag
        return false;
    }

}