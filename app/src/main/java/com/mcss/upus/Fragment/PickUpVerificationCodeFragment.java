package com.mcss.upus.Fragment;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import com.mcss.upus.Activity.MainActivity;
import com.mcss.upus.R;
import com.mcss.upus.Repository.CargomatUpdate;
import com.mcss.upus.Repository.PickUpRepository;
import com.mcss.upus.Util.TranslatorUtils;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PickUpVerificationCodeFragment extends Fragment implements View.OnClickListener, SharedPreferences.OnSharedPreferenceChangeListener {

    private EditText[] boxes;
    private int currentBoxIndex;
    private Retrofit retrofit;
    CargomatUpdate cargomatUpdate;
    final String TAG = "verify code";
    PickUpRepository pickUpRepository;
    SharedPreferences sharedPreferences;
    TranslatorUtils translatorUtils;
    final String BASE_URL = "https://flysistem.flyex.az/api/";

    public PickUpVerificationCodeFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        pickUpRepository = retrofit.create(PickUpRepository.class);

        View view = inflater.inflate(R.layout.fragment_pick_up_verification_code, container, false);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        translatorUtils = new TranslatorUtils(getActivity());
        translatorUtils.convertAllText(sharedPreferences.getString("lg",""), PickUpVerificationCodeFragment.this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get references to the EditText boxes
        boxes = new EditText[6];
        boxes[0] = view.findViewById(R.id.box1);
        boxes[1] = view.findViewById(R.id.box2);
        boxes[2] = view.findViewById(R.id.box3);
        boxes[3] = view.findViewById(R.id.box4);
        boxes[4] = view.findViewById(R.id.box5);
        boxes[5] = view.findViewById(R.id.box6);

        boxes[0].setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
        boxes[1].setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
        boxes[2].setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
        boxes[3].setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
        boxes[4].setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
        boxes[5].setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});

        /*for (EditText box : boxes) {
            box.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    // Not used
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    // Not used
                }

                @Override
                public void afterTextChanged(Editable s) {
                    // Move cursor to the next box when a number is entered
                    if (s.length() == 1 && currentBoxIndex < boxes.length - 1) {
                        currentBoxIndex++;
                        boxes[currentBoxIndex].requestFocus();
                    }
                }
            });
        }*/

        Button button1 = view.findViewById(R.id.numpadButton1);
        Button button2 = view.findViewById(R.id.numpadButton2);
        Button button3 = view.findViewById(R.id.numpadButton3);
        Button button4 = view.findViewById(R.id.numpadButton4);
        Button button5 = view.findViewById(R.id.numpadButton5);
        Button button6 = view.findViewById(R.id.numpadButton6);
        Button button7 = view.findViewById(R.id.numpadButton7);
        Button button8 = view.findViewById(R.id.numpadButton8);
        Button button9 = view.findViewById(R.id.numpadButton9);
        Button button0 = view.findViewById(R.id.numpadButton0);
        Button clearButton = view.findViewById(R.id.numpadButtonDelete);
        Button submitButton = view.findViewById(R.id.numpadButtonSubmit);
        ImageView closeBtn = view.findViewById(R.id.closeButtonPickUpVfCode);

        button1.setOnClickListener(this::onNumpadButtonClick);
        button2.setOnClickListener(this::onNumpadButtonClick);
        button3.setOnClickListener(this::onNumpadButtonClick);
        button4.setOnClickListener(this::onNumpadButtonClick);
        button5.setOnClickListener(this::onNumpadButtonClick);
        button6.setOnClickListener(this::onNumpadButtonClick);
        button7.setOnClickListener(this::onNumpadButtonClick);
        button8.setOnClickListener(this::onNumpadButtonClick);
        button9.setOnClickListener(this::onNumpadButtonClick);
        button0.setOnClickListener(this::onNumpadButtonClick);
        submitButton.setOnClickListener(this);
        clearButton.setOnClickListener(this::onClearButtonClick);
        closeBtn.setOnClickListener(this);
        cargomatUpdate = retrofit.create(CargomatUpdate.class);
    }



    public void onNumpadButtonClick(View view) {
        Objects.requireNonNull((MainActivity) getActivity()).resetTimeout();
        Button button = (Button) view;
        String buttonText = button.getText().toString();

        EditText currentBox = boxes[currentBoxIndex];
        currentBox.append(buttonText);

        if (currentBoxIndex < boxes.length - 1) {
            currentBoxIndex++;
            EditText nextBox = boxes[currentBoxIndex];
            nextBox.requestFocus();
        } else {
            // Last box reached
            // Check if all EditText fields are filled
            boolean allFieldsFilled = true;
            for (EditText box : boxes) {
                if (box.getText().toString().isEmpty()) {
                    allFieldsFilled = false;
                    break;
                }
            }

            if (allFieldsFilled) {
                StringBuilder s = new StringBuilder("");
                for (EditText box : boxes) {
                    s.append(box.getText().toString());
                }
            }
        }
    }


    @Override
    public void onClick(View view) {
        Objects.requireNonNull((MainActivity) getActivity()).resetTimeout();
        if (view.getId() == R.id.closeButtonPickUpVfCode) {
            MainActivity activity = (MainActivity) getActivity();
            if (activity != null) {
                activity.replaceFragment(new MainFragment());
            }
        }else if (view.getId() == R.id.numpadButtonSubmit){
            boolean allFieldsFull = true;
            for (EditText box : boxes) {
                if (box.getText().toString().isEmpty())
                    allFieldsFull = false;
            }
            if (allFieldsFull){
                System.out.println("All fields is: "+allFieldsFull);
                StringBuilder password = new StringBuilder();
                for (EditText box : boxes) {
                    password.append(box.getText().toString());
                }
                System.out.println("pass: "+password);
                openLattice(password.toString());
            }else{
                System.out.println("All fields is: "+allFieldsFull);
            }
        }
    }

    private void openLattice(String password) {
        Call<Object> call = pickUpRepository.getPickUpDetails(password);

        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.d(TAG, "onResponse: response code "+response.code());
                if (response.isSuccessful()) {
                    if (response.body() instanceof List){
                        Log.d(TAG, "onResponse: list: "+response.body());
                        LinkedTreeMap<String, Object> linkedTreeMap = ((List<LinkedTreeMap<String, Object>>) response.body()).get(0);
                        processWithData((String) linkedTreeMap.get("box_no"), password, (String) linkedTreeMap.get("mainboard_id"), (String) linkedTreeMap.get("device_id"));
                    }else{
                        if (response.body() instanceof String) {
                            Log.d(TAG, "onResponse: string: "+response.body());
                            processWithData(response.body().toString(), null, null, null);
                        }
                    }
                    Log.d(TAG, "onResponse: response.body() "+response.body());

                } else {
                    // Handle the error
                    Toast.makeText(getContext(), "Fetch failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void processWithData(final String boxNo, String password, String mainboard_id, String device_id) {

        Log.d(TAG, "processWithData: "+boxNo);

        if (boxNo.equalsIgnoreCase("Code Not Fount")){
            switch (sharedPreferences.getString("lg", "")) {
                case "AZ":
                    Toast.makeText(getActivity(), "Bağlama tapılmadı", Toast.LENGTH_SHORT).show();
                    break;
                case "EN":
                    Toast.makeText(getActivity(), "Code Not Fount", Toast.LENGTH_SHORT).show();
                    break;
                case "RU":
                    Toast.makeText(getActivity(), "Код не найден", Toast.LENGTH_SHORT).show();
            }
        }else {
            if (password != null){
                Call<String> call = cargomatUpdate.updateData(null, password);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful())
                            Log.d(TAG, "onResponse: cargomatUpdate: "+response.body());
                        else
                            Log.d(TAG, "onResponse: cargomatUpdate: "+response.code());
                    }

                    @Override
                    public void onFailure(Call<String> call, @NonNull Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
            switch (sharedPreferences.getString("lg", "")) {
                case "AZ":
                    Toast.makeText(getActivity(), boxNo+" açıqdır", Toast.LENGTH_SHORT).show();
                    break;
                case "EN":
                    Toast.makeText(getActivity(), boxNo+" is open", Toast.LENGTH_SHORT).show();
                    break;
                case "RU":
                    Toast.makeText(getActivity(), boxNo+" открыта", Toast.LENGTH_SHORT).show();
            }

//            openLock(Byte.parseByte(mainboard_id),Byte.parseByte(boxNo.replace("A", "")),null);


        }

        if (getActivity() != null){
            ((MainActivity) getActivity()).replaceFragment(new MainFragment());
        }
    }
   /* public static boolean openLock(byte boardNo, byte lockNo, String[] rsMsg)
    {
        return lockCmd((byte) 0x8A, boardNo, lockNo, rsMsg);
    }

    public static boolean openAllLock(byte boardNo, String[] rsMsg)
    {
        return lockCmd((byte) 0x9D, boardNo, (byte)0x02, rsMsg);
    }*/

    public void onClearButtonClick(View view) {
        Objects.requireNonNull((MainActivity) getActivity()).resetTimeout();
        for (EditText box : boxes) {
            box.setText("");
        }
        currentBoxIndex = 0;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        translatorUtils.convertAllText(sharedPreferences.getString("lg",""), PickUpVerificationCodeFragment.this, this.getView());
    }

}