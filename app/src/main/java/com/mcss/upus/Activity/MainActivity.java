package com.mcss.upus.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.mcss.upus.Core.PackageReceiverSec;
import com.mcss.upus.Fragment.LatticeSelectionFragment;
import com.mcss.upus.Fragment.MainFragment;
import com.mcss.upus.R;
import com.mcss.upus.Util.BackgroundWorks;
import com.mcss.upus.Util.CommonActivityStyle;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private FrameLayout frameLayout;
    PackageReceiverSec packageReceiverSec;
    Button closeBtn, loginButton, loginByOtherBtn;
    TextView registerText, errorMessageText, date, time;
    EditText userAccNumber, userPassword;
    ImageView settingsButton, mapLogoButton;
    Dialog dialog;
    private Handler handler;
    private Runnable updateTimeRunnable;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frameLayout = findViewById(R.id.frameLayout);

        settingsButton = (ImageView) findViewById(R.id.settingsBtn);
        mapLogoButton = (ImageView) findViewById(R.id.mapLogoBtn);

        date = (TextView) findViewById(R.id.date);
        time = (TextView) findViewById(R.id.time);

        handler = new Handler();
        updateTimeRunnable = new Runnable() {
            @Override
            public void run() {
                updateCurrentTime();
                // Schedule the Runnable to run after 1 second
                handler.postDelayed(this, 1000);
            }
        };

        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.login_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        loginButton = dialog.findViewById(R.id.loginBtn);
        loginByOtherBtn = dialog.findViewById(R.id.registerOtherWay);
        registerText = dialog.findViewById(R.id.registerTxt);
        userAccNumber = dialog.findViewById(R.id.userAccountNumberEditText);
        errorMessageText = dialog.findViewById(R.id.errorMsgText);
        userPassword = dialog.findViewById(R.id.userAccountPasswordEditText);
        closeBtn = dialog.findViewById(R.id.closeButton);


        loginButton.setOnClickListener((view) -> {
            if (userAccNumber.getText().length() == 0 || userPassword.getText().toString().length() == 0) {
                errorMessageText.setText("All fields must be filled!");
                errorMessageText.setTextColor(Color.parseColor("#ee004e"));
                errorMessageText.setVisibility(View.VISIBLE);
            } else if(userAccNumber.getText().length() != 0 || userPassword.getText().toString().length() != 0){
                replaceFragment(new LatticeSelectionFragment());
                userAccNumber.setText("");
                userPassword.setText("");
                dialog.dismiss();


            }else{
                errorMessageText.setVisibility(View.VISIBLE);
                errorMessageText.setTextColor(Color.parseColor("#ee004e"));
                errorMessageText.setText("Username or password incorrect!");
            }
        });

        dialog.setOnCancelListener(l -> {
            userAccNumber.setText("");
            userPassword.setText("");
            errorMessageText.setVisibility(View.GONE);
        });

        closeBtn.setOnClickListener((view) -> {
            dialog.dismiss();
        });

        CommonActivityStyle.set(this);

        replaceFragment(new MainFragment());

        packageReceiverSec = new PackageReceiverSec();

        BackgroundWorks.set(packageReceiverSec, this);

        View decorView = getWindow().getDecorView();
        int flags = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE;
        decorView.setSystemUiVisibility(flags);


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }


    @Override
    public void onResume() {
        super.onResume();
        // Start updating the time when the fragment is resumed
        startUpdatingTime();
    }

    @Override
    public void onPause() {
        super.onPause();
        // Stop updating the time when the fragment is paused
        stopUpdatingTime();
    }

    private void startUpdatingTime() {
        // Start the periodic update of time
        handler.post(updateTimeRunnable);
    }

    private void stopUpdatingTime() {
        // Stop the periodic update of time
        handler.removeCallbacks(updateTimeRunnable);
    }

    private void updateCurrentTime() {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat timeSdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        SimpleDateFormat dateSdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentTime = timeSdf.format(calendar.getTime());
        String currentDate = dateSdf.format(calendar.getTime());

        date.setText(currentDate);
        time.setText(currentTime);
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(packageReceiverSec);
        super.onDestroy();
    }

    public void openLoginDialog() {
        errorMessageText.setVisibility(View.GONE);
        dialog.show();
    }

    public void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commit();
    }
}