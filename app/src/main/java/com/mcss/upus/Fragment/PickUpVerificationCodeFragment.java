package com.mcss.upus.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.mcss.upus.Activity.MainActivity;
import com.mcss.upus.R;

import java.util.Objects;

public class PickUpVerificationCodeFragment extends Fragment implements View.OnClickListener {

    private EditText[] boxes;
    private int currentBoxIndex;

    public PickUpVerificationCodeFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pick_up_verification_code, container, false);
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
        submitButton.setOnClickListener(this::onClick);
        clearButton.setOnClickListener(this::onClearButtonClick);
        closeBtn.setOnClickListener(this);
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
        }
    }

    public void onClearButtonClick(View view) {
        Objects.requireNonNull((MainActivity) getActivity()).resetTimeout();
        for (EditText box : boxes) {
            box.setText("");
        }
        currentBoxIndex = 0;

    }
}