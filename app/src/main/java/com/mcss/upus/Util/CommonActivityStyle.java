package com.mcss.upus.Util;

import android.graphics.Color;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

public final class CommonActivityStyle {

    public static void set(AppCompatActivity activity){

        if (activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().hide();
        }

        Window window = activity.getWindow();
        window.setStatusBarColor(Color.WHITE);

        View decor = activity.getWindow().getDecorView();
        decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }
}
