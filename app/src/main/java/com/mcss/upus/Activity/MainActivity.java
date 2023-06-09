package com.mcss.upus.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mcss.upus.Core.DBHandler;
import com.mcss.upus.Core.PackageReceiverSec;
import com.mcss.upus.Fragment.LatticeSelectionFragment;
import com.mcss.upus.Fragment.MainFragment;
import com.mcss.upus.R;
import com.mcss.upus.Repository.SearchRepo;
import com.mcss.upus.Repository.SendBundleInfo;
import com.mcss.upus.Util.BackgroundWorks;
import com.mcss.upus.Util.CommonActivityStyle;
import com.mcss.upus.Util.TranslatorUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    PackageReceiverSec packageReceiverSec;
    List<String> data;
    Button closeBtn, loginButton, languageBtnAZ, languageBtnEN, languageBtnRU, searchButton, submitButtonSearch, closeButtonSearch;
    TextView errorMessageText, date, time, loginHeader, searchHeader;
    EditText userAccNumber, userPassword, inventoryCodeEditText, phoneNumberEditText;
    SharedPreferences preferences;
    private static final int APP_PERMISSION_REQUEST = 456;
    SearchRepo searchRepo;
    SharedPreferences.Editor editor;
    TranslatorUtils translatorUtils;
    private final String BASE_URL = "https://flysistem.flyex.az/api/";
    ImageView settingsButton, mapLogoButton;
    DBHandler dbHandler;
    Dialog dialog, searchDialog;
    private Handler handler;
    private Runnable updateTimeRunnable;
    private final long TOUCH_TIMEOUT = 120000; // 120 seconds in milliseconds
    private Handler timeoutHandler;
    public ConstraintLayout mainLayout;
    private Runnable timeoutRunnable;
    SendBundleInfo sendBundleInfo;
    private String TAG = "tagim", phoneNumber;
    private Retrofit retrofit;

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

        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        searchRepo = retrofit.create(SearchRepo.class);

        translatorUtils = new TranslatorUtils(this);

        languageBtnAZ = findViewById(R.id.languageBtnAZ);
        languageBtnEN = findViewById(R.id.languageBtnEN);
        languageBtnRU = findViewById(R.id.languageBtnRU);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        editor.putString("lg", "AZ");
        editor.apply();

        languageBtnAZ.setOnClickListener(view -> {
            editor.putString("lg", "AZ");
            editor.apply();
        });
        languageBtnEN.setOnClickListener(view -> {
            editor.putString("lg", "EN");
            editor.apply();
        });
        languageBtnRU.setOnClickListener(view -> {
            editor.putString("lg", "RU");
            editor.apply();
        });


        // DB WORKS START


        dbHandler = new DBHandler(this);

        dbHandler.addDataToAZTable("countryName", "Azərbaycan");
        dbHandler.addDataToENTable("countryName", "Azerbaijan");
        dbHandler.addDataToRUTable("countryName", "Азербайджан");

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

        dbHandler.addDataToAZTable("boxTxtSmall", "Balaca qutu");
        dbHandler.addDataToENTable("boxTxtSmall", "Small box");
        dbHandler.addDataToRUTable("boxTxtSmall", "Маленькая коробка");

        dbHandler.addDataToAZTable("boxTxtMedium", "Medium qutu");
        dbHandler.addDataToENTable("boxTxtMedium", "Medium box");
        dbHandler.addDataToRUTable("boxTxtMedium", "Средняя коробка");

        dbHandler.addDataToAZTable("boxTxtLarge", "Böyük qutu");
        dbHandler.addDataToENTable("boxTxtLarge", "Large box");
        dbHandler.addDataToRUTable("boxTxtLarge", "Большая коробка");

        dbHandler.addDataToAZTable("nextStepBtn", "Növbəti addım");
        dbHandler.addDataToENTable("nextStepBtn", "Next step");
        dbHandler.addDataToRUTable("nextStepBtn", "Следующий шаг");

        dbHandler.addDataToAZTable("pickUpScreenHeader", "Lütfən, götürmə üsulunu seçin");
        dbHandler.addDataToENTable("pickUpScreenHeader", "Please select the pickup method");
        dbHandler.addDataToRUTable("pickUpScreenHeader", "Пожалуйста, выберите способ получения");

        dbHandler.addDataToAZTable("verifyByCodeBtn", "Kod ilə götürmək");
        dbHandler.addDataToENTable("verifyByCodeBtn", "Pick up by code");
        dbHandler.addDataToRUTable("verifyByCodeBtn", "Забрать по коду");

        dbHandler.addDataToAZTable("verifictaionFrHeader", "Kodu daxil edin");
        dbHandler.addDataToENTable("verifictaionFrHeader", "Enter the code");
        dbHandler.addDataToRUTable("verifictaionFrHeader", "Введите код");

        dbHandler.addDataToAZTable("numpadButtonDelete", "Təmizlə");
        dbHandler.addDataToENTable("numpadButtonDelete", "Clear");
        dbHandler.addDataToRUTable("numpadButtonDelete", "Очистить");

        dbHandler.addDataToAZTable("numpadButtonSubmit", "Təsdiq et");
        dbHandler.addDataToENTable("numpadButtonSubmit", "Submit");
        dbHandler.addDataToRUTable("numpadButtonSubmit", "Подтвердить");

        dbHandler.addDataToAZTable("loginHeader", "Giriş et");
        dbHandler.addDataToENTable("loginHeader", "Login");
        dbHandler.addDataToRUTable("loginHeader", "Войти");

        dbHandler.addDataToAZTable("userAccountNumberEditText", "Hesab nömrəsi");
        dbHandler.addDataToENTable("userAccountNumberEditText", "Account number");
        dbHandler.addDataToRUTable("userAccountNumberEditText", "Номер аккаунта");

        dbHandler.addDataToAZTable("userAccountPasswordEditText", "Şifrə");
        dbHandler.addDataToENTable("userAccountPasswordEditText", "Password");
        dbHandler.addDataToRUTable("userAccountPasswordEditText", "Пароль");

        dbHandler.addDataToAZTable("inventoryCodeEditText", "İzləmə nömrəsi");
        dbHandler.addDataToENTable("inventoryCodeEditText", "Tracking number");
        dbHandler.addDataToRUTable("inventoryCodeEditText", "Идентификационный номер");

        dbHandler.addDataToAZTable("phoneNumberEditText", "Telefon nömrəsi");
        dbHandler.addDataToENTable("phoneNumberEditText", "Phone number");
        dbHandler.addDataToRUTable("phoneNumberEditText", "Номер телефона");


        dbHandler.addDataToAZTable("errorMsgText", "Məlumatları doldurun!");
        dbHandler.addDataToENTable("errorMsgText", "Fill in the information!");
        dbHandler.addDataToRUTable("errorMsgText", "Заполните информацию!");


        dbHandler.addDataToAZTable("errorMsgText", "Hesab nömrəsi və ya şifrə yalnışdır!");
        dbHandler.addDataToENTable("errorMsgText", "The account number or password is incorrect!");
        dbHandler.addDataToRUTable("errorMsgText", "Номер счета или пароль неверный!");


        dbHandler.addDataToAZTable("loginBtn", "Daxil ol");
        dbHandler.addDataToENTable("loginBtn", "Sign in");
        dbHandler.addDataToRUTable("loginBtn", "Вход");

        dbHandler.addDataToAZTable("deliverHeader", "Yerləşdirmək üçün tamamlayın");
        dbHandler.addDataToENTable("deliverHeader", "Finish up to drop off");
        dbHandler.addDataToRUTable("deliverHeader", "Завершить, чтобы класть");

        dbHandler.addDataToAZTable("cabinetNumber", "Kabinet nömrəsi");
        dbHandler.addDataToENTable("cabinetNumber", "Cabinet number");
        dbHandler.addDataToRUTable("cabinetNumber", "Номер кабинета");

        dbHandler.addDataToAZTable("trackingNumber", "İzləmə nömrəsi");
        dbHandler.addDataToENTable("trackingNumber", "Tracking number");
        dbHandler.addDataToRUTable("trackingNumber", "Идентификационный номер");

        dbHandler.addDataToAZTable("cellPhoneNumber", "Telefon nömrəsi");
        dbHandler.addDataToENTable("cellPhoneNumber", "Cell phone number");
        dbHandler.addDataToRUTable("cellPhoneNumber", "Номер телефона");

        dbHandler.addDataToRUTable("searchHeader", "Məlumatları daxil et");
        dbHandler.addDataToRUTable("searchHeader", "Enter the information");
        dbHandler.addDataToRUTable("searchHeader", "Введите информацию");

        dbHandler.addDataToRUTable("inventoryCodeEditText", "İzləmə nömrəsi");
        dbHandler.addDataToRUTable("inventoryCodeEditText", "Tracking number");
        dbHandler.addDataToRUTable("inventoryCodeEditText", "Идентификационный номер");

        dbHandler.addDataToRUTable("phoneNumberEditText", "Telefon nömrəsi");
        dbHandler.addDataToRUTable("phoneNumberEditText", "Phone number");
        dbHandler.addDataToRUTable("phoneNumberEditText", "Номер телефона");

        dbHandler.addDataToRUTable("submitButton", "Təsdiq et");
        dbHandler.addDataToRUTable("submitButton", "Submit");
        dbHandler.addDataToRUTable("submitButton", "Подтверждать");

        // DB WORKS END





        mainLayout = findViewById(R.id.mainLayout);
        mainLayout.setOnTouchListener((view, motionEvent) -> {
            resetTimeout();
            return false;
        });

        // Start the timeout countdown
        startTimeout();

        settingsButton = findViewById(R.id.settingsBtn);
        mapLogoButton = findViewById(R.id.mapLogoBtn);

        date = findViewById(R.id.date);
        time = findViewById(R.id.time);

        handler = new Handler();
        updateTimeRunnable = new Runnable() {
            @Override
            public void run() {
                updateCurrentTime();
                // Schedule the Runnable to run after 1 second
                handler.postDelayed(this, 1000);
            }
        };

        // login dialog

        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.login_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.setOnKeyListener((dialogInterface, i, keyEvent) -> {
            resetTimeout();
            return false;
        });

        loginHeader = dialog.findViewById(R.id.loginHeader);
        loginButton = dialog.findViewById(R.id.loginBtn);
        userAccNumber = dialog.findViewById(R.id.userAccountNumberEditText);
        errorMessageText = dialog.findViewById(R.id.errorMsgText);
        userPassword = dialog.findViewById(R.id.userAccountPasswordEditText);
        closeBtn = dialog.findViewById(R.id.closeButton);

        // search dialog

        searchDialog = new Dialog(MainActivity.this);
        searchDialog.setContentView(R.layout.search_dialog);
        searchDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        searchDialog.setCancelable(false);
        searchDialog.setOnKeyListener((dialogInterface, i, keyEvent) -> {
            resetTimeout();
            return false;
        });

        searchHeader = searchDialog.findViewById(R.id.searchHeader);
        submitButtonSearch = searchDialog.findViewById(R.id.submitButtonSearch);
        inventoryCodeEditText = searchDialog.findViewById(R.id.inventoryCodeEditText);
        phoneNumberEditText = searchDialog.findViewById(R.id.phoneNumberEditText);
        closeButtonSearch = searchDialog.findViewById(R.id.closeButtonSearch);
        searchButton = searchDialog.findViewById(R.id.searchButton);


        // login dialog ops

        loginButton.setOnClickListener((view) -> {
            String userAccNumber = this.userAccNumber.getText().toString();
            String userPassword = this.userPassword.getText().toString();
            if (userAccNumber.length() == 0 || userPassword.length() == 0) {

                switch (preferences.getString("lg", "")) {
                    case "AZ":
                        errorMessageText.setText("Məlumatları doldurun!");
                        break;
                    case "EN":
                        errorMessageText.setText("Fill in the information!");
                        break;
                    case "RU":
                        errorMessageText.setText("Заполните информацию!");
                }
                errorMessageText.setTextColor(Color.parseColor("#ee004e"));
                errorMessageText.setVisibility(View.VISIBLE);
            } else {
                if (userAccNumber.equals("user1") && userPassword.equals("123")) {
                    replaceFragment(new LatticeSelectionFragment());
                    this.userAccNumber.setText("");
                    this.userPassword.setText("");
                    dialog.dismiss();
                } else {
                    errorMessageText.setVisibility(View.VISIBLE);
                    errorMessageText.setTextColor(Color.parseColor("#ee004e"));
                    switch (preferences.getString("lg", "")) {
                        case "AZ":
                            errorMessageText.setText("Hesab nömrəsi və ya şifrə yalnışdır!");
                            break;
                        case "EN":
                            errorMessageText.setText("The account number or password is incorrect!");
                            break;
                        case "RU":
                            errorMessageText.setText("Номер счета или пароль неверный!");
                    }

                }
            }


        });

        // search dialog ops

        submitButtonSearch.setOnClickListener(view -> {

            String inventoryCode = this.inventoryCodeEditText.getText().toString();

            phoneNumber = this.phoneNumberEditText.getText().toString();
            if (inventoryCode.length() != 0 && phoneNumber.length() == 0) {
                fetchData(inventoryCode);
            }
                /*Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, APP_PERMISSION_REQUEST);
            Intent front_translucent = new Intent(getApplication()
                    .getApplicationContext(), CameraService.class);
            front_translucent.putExtra("Front_Request", true);
            front_translucent.putExtra("Quality_Mode",
                    1);
            getApplication().getApplicationContext().startService(
                    front_translucent);*/
        });

        dialog.setOnCancelListener(l -> {
            userAccNumber.setText("");
            userPassword.setText("");
            errorMessageText.setVisibility(View.GONE);
        });

        searchDialog.setOnCancelListener(l -> {
            inventoryCodeEditText.setText("");
            phoneNumberEditText.setText("");
        });

        closeBtn.setOnClickListener((view) -> dialog.dismiss());
        closeButtonSearch.setOnClickListener((view) -> searchDialog.dismiss());
        translatorUtils.converDialogText(preferences.getString("lg", ""), dialog);
        translatorUtils.convertAllText(preferences.getString("lg", ""), MainActivity.this);

        CommonActivityStyle.set(this);

        replaceFragment(new MainFragment());

        packageReceiverSec = new PackageReceiverSec();

        BackgroundWorks.set(packageReceiverSec, this);

        View decorView = getWindow().getDecorView();
        int flags = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE;
        decorView.setSystemUiVisibility(flags);


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


    }

    private void fetchData(String inventoryCode) {

        Call<String> call = searchRepo.getPhoneNumber(inventoryCode);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String inventoryCode = response.body();
                    try {
                        if (inventoryCode != null) {
                            processWithData(inventoryCode);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();

                    }
                } else {
                    // Handle the error
                    Log.d(TAG, "API call failed");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void processWithData(String inventoryCode
    ) throws IOException {
        if (!inventoryCode.equalsIgnoreCase("Bundle Not Fount")){
            phoneNumberEditText.setText(inventoryCode);

        }else{
            switch (preferences.getString("lg", "")) {
                case "AZ":
                    Toast.makeText(this, "Bağlama tapılmadı", Toast.LENGTH_SHORT).show();
                    break;
                case "EN":
                    Toast.makeText(this, "Bundle not found", Toast.LENGTH_SHORT).show();
                    break;
                case "RU":
                    Toast.makeText(this, "Пакет не найден", Toast.LENGTH_SHORT).show();
            }
        }

        sendBundleInfo = retrofit.create(SendBundleInfo.class);

        Log.d(TAG, "processWithData: retrieved data: "+"1"+ " "+"1"+ " "+data.get(2)+ " "+data.get(1)+ " "+inventoryCode+ " "+null+ " "+data.get(0)+ " test");
        Call<Void> call = sendBundleInfo.insertData("1", "1", data.get(2), data.get(1), inventoryCode, null, data.get(0), "test");

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    Log.d(TAG, "processWithData: sendpost"+ response.code()+" "+response.isSuccessful());
                }else{
                    Log.d(TAG, "onResponse: not success"+response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });



        // TODO: send retrieved data from delivery cabinet num, trck num, phone num
    }

    public void openSearchDialog(List<String> data) {
        this.data = new ArrayList<>(data);
        searchDialog.show();

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


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        translatorUtils.convertAllText(sharedPreferences.getString("lg", ""), this);
        translatorUtils.converDialogText(sharedPreferences.getString("lg", ""), dialog);
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