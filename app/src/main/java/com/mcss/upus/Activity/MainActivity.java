package com.mcss.upus.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.mcss.upus.Core.DBHandler;
import com.mcss.upus.Core.PackageReceiverSec;
import com.mcss.upus.Fragment.LatticeSelectionFragment;
import com.mcss.upus.Fragment.MainFragment;
import com.mcss.upus.R;
import com.mcss.upus.Util.BackgroundWorks;
import com.mcss.upus.Util.CommonActivityStyle;
import com.mcss.upus.Util.TranslatorUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

    PackageReceiverSec packageReceiverSec;
    Button closeBtn, loginButton, languageBtnAZ, languageBtnEN, languageBtnRU;
    TextView errorMessageText, date, time;
    EditText userAccNumber, userPassword;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    TranslatorUtils translatorUtils;
    ImageView settingsButton, mapLogoButton;
    DBHandler dbHandler;
    Dialog dialog;
    private Handler handler;
    private Runnable updateTimeRunnable;

    private final long TOUCH_TIMEOUT = 120000; // 120 seconds in milliseconds
    private Handler timeoutHandler;
    public  ConstraintLayout mainLayout;
    private Runnable timeoutRunnable;
    private String TAG = "tagim";

    public void startTimeout() {
        timeoutHandler = new Handler();
        timeoutRunnable = () -> {
            // Launch the custom activity after timeout
            Intent intent = new Intent(MainActivity.this, SlideShow.class);
            startActivity(intent);
        };

        timeoutHandler.postDelayed(timeoutRunnable, TOUCH_TIMEOUT);
    }

    public void resetTimeout() {
        timeoutHandler.removeCallbacks(timeoutRunnable);
        timeoutHandler.postDelayed(timeoutRunnable, TOUCH_TIMEOUT);
}

    @SuppressLint({"SetTextI18n", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        translatorUtils = new TranslatorUtils(this);

        languageBtnAZ = findViewById(R.id.languageBtnAZ);
        languageBtnEN = findViewById(R.id.languageBtnEN);
        languageBtnRU = findViewById(R.id.languageBtnRU);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        languageBtnAZ.setOnClickListener(view -> {
            Log.d(TAG, "onCreate: az");
            editor.putString("lg","AZ");
            editor.apply();
        });

        languageBtnEN.setOnClickListener(view -> {
            Log.d(TAG, "onCreate: en");
            editor.putString("lg","EN");
            editor.apply();
        });
        languageBtnRU.setOnClickListener(view -> {
            Log.d(TAG, "onCreate: ru");
            editor.putString("lg","RU");
            editor.apply();
        });


        // DB WORKS START



        dbHandler = new DBHandler(this);

        dbHandler.addDataToAZTable("countryName", "Azərbaycan");
        dbHandler.addDataToENTable("countryName", "Azerbaijan");
        dbHandler.addDataToRUTable("countryName", "Азербайджан");
        // daxil ol, hesab nömrəsi, şifrə, giriş et

        dbHandler.addDataToAZTable("dropOffBtn", "Yerləşdir");
        dbHandler.addDataToENTable("dropOffBtn", "Drop off");
        dbHandler.addDataToRUTable("dropOffBtn", "Класть");

        dbHandler.addDataToAZTable("pickUpBtn", "Təhvil al");
        dbHandler.addDataToENTable("pickUpBtn", "Pick-up");
        dbHandler.addDataToRUTable("pickUpBtn", "Подобрать");

        dbHandler.addDataToAZTable("helpBtn", "Kömək");
        dbHandler.addDataToENTable("helpBtn", "Help");
        dbHandler.addDataToRUTable("helpBtn", "Помощь");

        dbHandler.addDataToAZTable("latticeHeader", "Zəhmət olmasa rəf seçin");
        dbHandler.addDataToENTable("latticeHeader", "Please select a shelf");
        dbHandler.addDataToRUTable("latticeHeader", "Пожалуйста, выберите полку");

        dbHandler.addDataToAZTable("type", "Növ");
        dbHandler.addDataToENTable("type", "Type");
        dbHandler.addDataToRUTable("type", "Тип");

        dbHandler.addDataToAZTable("available", "Mövcud");
        dbHandler.addDataToENTable("available", "Available");
        dbHandler.addDataToRUTable("available", "Доступный");

        dbHandler.addDataToAZTable("330011", "Balaca qutu");
        dbHandler.addDataToENTable("330011", "Small box");
        dbHandler.addDataToRUTable("330011", "Маленькая коробка");

        dbHandler.addDataToAZTable("nextStepBtn", "Növbəti addım");
        dbHandler.addDataToENTable("nextStepBtn", "Next step");
        dbHandler.addDataToRUTable("nextStepBtn", "Следующий шаг");

        dbHandler.addDataToAZTable("pickUpScreenHeader", "Lütfən, götürmə üsulunu seçin");
        dbHandler.addDataToENTable("pickUpScreenHeader", "Please select the pickup method");
        dbHandler.addDataToRUTable("pickUpScreenHeader", "Пожалуйста, выберите способ получения");

        dbHandler.addDataToAZTable("verifyByCodeBtn", "Kod ilə götürmək");
        dbHandler.addDataToENTable("verifyByCodeBtn", "Pick up by code");
        dbHandler.addDataToRUTable("verifyByCodeBtn", "Забрать по коду");

        /*dbHandler.addDataToAZTable("login_dialog_header", "Giriş et");
        dbHandler.addDataToENTable("login_dialog_header", "Login");
        dbHandler.addDataToRUTable("login_dialog_header", "Войти");

        dbHandler.addDataToAZTable("login_dialog_acc_number", "Hesab nömrəsi");
        dbHandler.addDataToENTable("login_dialog_acc_number", "Account number");
        dbHandler.addDataToRUTable("login_dialog_acc_number", "Номер аккаунта");

        dbHandler.addDataToAZTable("login_dialog_password", "Şifrə");
        dbHandler.addDataToENTable("login_dialog_password", "Password");
        dbHandler.addDataToRUTable("login_dialog_password", "Пароль");

        dbHandler.addDataToAZTable("login_dialog_button", "Daxil ol");
        dbHandler.addDataToENTable("login_dialog_button", "Sign in");
        dbHandler.addDataToRUTable("login_dialog_button", "Вход");




        // please select pickup method, verify via pickup code



        // kodu daxil edin, clear, tesdiq et

        dbHandler.addDataToAZTable("fragment_pick_up_verification_code_header", "Kodu daxil edin");
        dbHandler.addDataToENTable("fragment_pick_up_verification_code_header", "Enter the code");
        dbHandler.addDataToRUTable("fragment_pick_up_verification_code_header", "Введите код");

        dbHandler.addDataToAZTable("fragment_pick_up_verification_code_1_button", "Təmizlə");
        dbHandler.addDataToENTable("fragment_pick_up_verification_code_1_button", "Clear");
        dbHandler.addDataToRUTable("fragment_pick_up_verification_code_1_button", "Очистить");

        dbHandler.addDataToAZTable("fragment_pick_up_verification_code_2_button", "Təsdiq et");
        dbHandler.addDataToENTable("fragment_pick_up_verification_code_2_button", "Submit");
        dbHandler.addDataToRUTable("fragment_pick_up_verification_code_2_button", "Подтвердить");

        // DB WORKS END*/



        mainLayout = findViewById(R.id.mainLayout);
        mainLayout.setOnTouchListener((view, motionEvent) -> {
            resetTimeout();
            return false;
        });
        // Start the timeout countdown
        startTimeout();

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
        dialog.setOnKeyListener((dialogInterface, i, keyEvent) -> {
            resetTimeout();
            return false;
        });

        loginButton = dialog.findViewById(R.id.loginBtn);
        userAccNumber = dialog.findViewById(R.id.userAccountNumberEditText);
        errorMessageText = dialog.findViewById(R.id.errorMsgText);
        userPassword = dialog.findViewById(R.id.userAccountPasswordEditText);
        closeBtn = dialog.findViewById(R.id.closeButton);


        loginButton.setOnClickListener((view) -> {
            if (userAccNumber.getText().length() == 0 || userPassword.getText().toString().length() == 0) {
                errorMessageText.setText("Məlumatları doldurun!");
                errorMessageText.setTextColor(Color.parseColor("#ee004e"));
                errorMessageText.setVisibility(View.VISIBLE);
            } else if (userAccNumber.getText().length() != 0 || userPassword.getText().toString().length() != 0) {
                replaceFragment(new LatticeSelectionFragment());
                userAccNumber.setText("");
                userPassword.setText("");

                dialog.dismiss();


            } else {
                errorMessageText.setVisibility(View.VISIBLE);
                errorMessageText.setTextColor(Color.parseColor("#ee004e"));
                errorMessageText.setText("Hesab nömrəsi və ya şifrə yalnışdır!");
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
        timeoutHandler.removeCallbacks(timeoutRunnable);
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

    /*private void convertAllText(String lang, AppCompatActivity activity){
        HashMap<String, String> data = dbHandler.readAllData(lang);
        Resources resources = activity.getResources();
        data.forEach((key, value) -> {
            int resourceId = resources.getIdentifier(key,"id",activity.getPackageName());
            if (resourceId != 0){
                View viewById = activity.findViewById(resourceId);
                if (viewById instanceof TextView) {
                    ((TextView) viewById).setText(value);
                }
                if (viewById instanceof Button) {
                    ((Button) viewById).setText(value);
                }
            }
        });
    }*/


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        translatorUtils.convertAllText(sharedPreferences.getString("lg",""), this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);
    }
}