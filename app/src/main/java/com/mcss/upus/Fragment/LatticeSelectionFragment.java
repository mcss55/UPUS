package com.mcss.upus.Fragment;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.mcss.upus.Activity.MainActivity;
import com.mcss.upus.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

public class LatticeSelectionFragment extends Fragment implements View.OnClickListener {
    public LatticeSelectionFragment() {
        buttonIncreaseList = new HashMap<>();
        buttonDecreaseList = new HashMap<>();
        textViewCountNumberList = new HashMap<>();
        textViewTypeList = new HashMap<>();
        textViewAvailableList = new HashMap<>();
    }


    HashMap<Integer, Button> buttonIncreaseList;
    HashMap<Integer, Button> buttonDecreaseList;
    //
    HashMap<Integer, TextView> textViewCountNumberList;
    HashMap<Integer, TextView> textViewTypeList;
    HashMap<Integer, TextView> textViewAvailableList;

    TableLayout tableLayout;


    private int number = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void addRowWithData(String data1, String data2) {
        Button buttonDecrease, buttonIncrease;
        TextView textViewNumber;
        // Create a new TableRow
        TableRow newRow = new TableRow(getActivity());
        // Create TextViews for each data item


        // Type TextView
        TextView typeTextView = new TextView(getActivity());
        TableRow.LayoutParams layoutParamstypeTxt = new TableRow.LayoutParams(0,
                ViewGroup.LayoutParams.WRAP_CONTENT, 3f);
        layoutParamstypeTxt.weight = 3;
        typeTextView.setLayoutParams(layoutParamstypeTxt);
        typeTextView.setGravity(Gravity.CENTER_HORIZONTAL);
        typeTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        typeTextView.setTextColor(getResources().getColor(R.color.black));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            typeTextView.setTypeface(Typeface.create(getResources().getFont(R.font.intermedium), Typeface.NORMAL));
        }
        typeTextView.setText(data1);
        typeTextView.setId(View.generateViewId());


        // Available TextView
        TableRow.LayoutParams layoutParamsAvailableTxt = new TableRow.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                3f
        );
        TextView availableTextView = new TextView(getActivity());
        layoutParamstypeTxt.weight = 3;
        availableTextView.setLayoutParams(layoutParamsAvailableTxt);
        availableTextView.setGravity(Gravity.CENTER_HORIZONTAL);
        availableTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        availableTextView.setTextColor(getResources().getColor(R.color.black));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            availableTextView.setTypeface(Typeface.create(getResources().getFont(R.font.intermedium), Typeface.NORMAL));
        }
        availableTextView.setText(data2);
        availableTextView.setId(View.generateViewId());

        // increase/decrease button andtextview
        // Creating the Decrease Button

        buttonDecrease = new Button(getActivity());
//        buttonDecrease.setId(View.generateViewId());
        buttonDecrease.setLayoutParams(new TableRow.LayoutParams(pxToDp(60), ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        buttonDecrease.setText("-");
        buttonDecrease.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
        buttonDecrease.setBackground(getResources().getDrawable(R.drawable.bordered_button_red));
        buttonDecrease.setId(View.generateViewId());
// Creating the TextView for the Number
        TableRow.LayoutParams layoutParamsNumberCountTxt = new TableRow.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.37f
        );

        textViewNumber = new TextView(getActivity());
//        textViewNumber.setId(View.generateViewId());
        layoutParamsNumberCountTxt.setMarginStart(pxToDp(5));
        layoutParamsNumberCountTxt.setMarginEnd(pxToDp(5));
        textViewNumber.setLayoutParams(layoutParamsNumberCountTxt);
        textViewNumber.setText("0");
        textViewNumber.setBackground(getResources().getDrawable(R.drawable.rounded_corner_textview));
        textViewNumber.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        textViewNumber.setGravity(Gravity.CENTER);
        textViewNumber.setId(View.generateViewId());

        // Creating the Increase Button
        buttonIncrease = new Button(getActivity());
        TableRow.LayoutParams layoutParamsBtnIncrease = new TableRow.LayoutParams(
                pxToDp(60), ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParamsBtnIncrease.setMarginEnd(pxToDp(30));
        buttonIncrease.setLayoutParams(layoutParamsBtnIncrease);
//        buttonIncrease.setId(R.id.buttonIncrease);
        buttonIncrease.setBackground(getResources().getDrawable(R.drawable.bordered_button_green));
        buttonIncrease.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
        buttonIncrease.setText("+");
        buttonIncrease.setId(View.generateViewId());

        // Add the TextViews to the TableRow
        newRow.addView(typeTextView);
        newRow.addView(availableTextView);
        newRow.addView(buttonDecrease);
        newRow.addView(textViewNumber);
        newRow.addView(buttonIncrease);


        // Add the TableRow to the TableLayout
        tableLayout.addView(newRow);


        buttonIncreaseList.put(buttonIncrease.getId(), buttonIncrease);
        buttonDecreaseList.put(buttonDecrease.getId(), buttonDecrease);
        textViewAvailableList.put(availableTextView.getId(), availableTextView);
        textViewTypeList.put(typeTextView.getId(), typeTextView);
        textViewCountNumberList.put(textViewNumber.getId(), textViewNumber);

        buttonIncreaseList.forEach((id, button) -> button.setOnClickListener(this));
        buttonDecreaseList.forEach((id, button)  -> button.setOnClickListener(this));
        textViewAvailableList.forEach((id, textView)  -> textView.setOnClickListener(this));
        textViewTypeList.forEach((id, textView)  -> textView.setOnClickListener(this));
        textViewCountNumberList.forEach((id, textView) -> textView.setOnClickListener(this));

    }

    private int pxToDp(int dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                getResources().getDisplayMetrics()
        );
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_lattice_selection, container, false);

//        buttonDecrease.setOnClickListener(this);
//        buttonIncrease.setOnClickListener(this);

        Button nextStepBtn = (Button) view.findViewById(R.id.nextStepBtn);
        nextStepBtn.setOnClickListener(this);
        Button latticeCloseButton = (Button) view.findViewById(R.id.latticeCloseButton);
        tableLayout = view.findViewById(R.id.customTable);
        latticeCloseButton.setOnClickListener(this);
        addRowWithData("Small box", "10");
        addRowWithData("Small box", "2");
        addRowWithData("Small box", "13");
        addRowWithData("Small box", "17");

        Log.d("BUTTON: ", nextStepBtn.getText().toString());

        return view;
    }

    private void decreaseNumber(int id) {
        number--;
        Objects.requireNonNull(textViewCountNumberList.get(id)).setText(String.valueOf(number));
    }

    private int intToSp(int sp) {
        float scale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * scale);
    }

    private void increaseNumber(int id) {
        number++;
        Objects.requireNonNull(textViewCountNumberList.get(id)).setText(String.valueOf(number));
    }


    @SuppressLint({"ResourceType", "NonConstantResourceId"})
    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.nextStepBtn) {
            MainActivity mainActivity = (MainActivity) getActivity();
            if (mainActivity != null) {
                mainActivity.replaceFragment(new DeliverScanFragment());
            }
            return;
        }

        if (view instanceof Button) {
//            Log.d("CLICK BTN: ", ((Button) view).getText().toString());
            if (((Button) view).getText().equals("+")){
                TextView textViewCount = textViewCountNumberList.get(view.getId() - 1);
                if (textViewCount != null) {
                    int i = Integer.parseInt(textViewCount.getText().toString()) + 1;
                    textViewCount.setText(String.valueOf(i));
                }
            }else if (((Button) view).getText().equals("-")){
                TextView textViewCount = textViewCountNumberList.get(view.getId() + 1);
                if (textViewCount != null && Integer.parseInt(textViewCount.getText().toString()) > 0) {
                    int i = Integer.parseInt(textViewCount.getText().toString()) - 1;
                    textViewCount.setText(String.valueOf(i));
                }
            }else if (!((Button) view).getText().toString().equals("Next step")){
                MainActivity mainActivity=(MainActivity) getActivity();
                 if(mainActivity != null){
                     mainActivity.replaceFragment(new MainFragment());
                 }
            }
        }
    }
}