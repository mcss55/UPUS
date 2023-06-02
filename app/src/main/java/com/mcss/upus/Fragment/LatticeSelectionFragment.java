package com.mcss.upus.Fragment;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.mcss.upus.Util.TranslatorUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

public class LatticeSelectionFragment extends Fragment implements View.OnClickListener, SharedPreferences.OnSharedPreferenceChangeListener {
    TranslatorUtils translatorUtils;
    public static List<Integer> ids;

    public LatticeSelectionFragment() {
        ids = new ArrayList<>();
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint({"ResourceType", "UseCompatLoadingForDrawables"})
    private void addRowWithData(String data1, String data2) {

        Button buttonDecrease, buttonIncrease;
        TextView textViewNumber;

        TableRow newRow = new TableRow(getActivity());
        newRow.setPadding(0, 0, 0, pxToDp(20));


        // Type TextView
        TableRow.LayoutParams layoutParamstypeTxt = new TableRow.LayoutParams(pxToDp(465),
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParamstypeTxt.setMarginStart(pxToDp(20));

        TextView typeTextView = new TextView(getActivity());
        typeTextView.setLayoutParams(layoutParamstypeTxt);
        typeTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        typeTextView.setGravity(Gravity.CENTER);
        typeTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 46);
        typeTextView.setTextColor(getResources().getColor(R.color.black));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            typeTextView.setTypeface(Typeface.create(getResources().getFont(R.font.intermedium), Typeface.NORMAL));
        }
        typeTextView.setText(data1);
        typeTextView.setId(View.generateViewId());
        ids.add(typeTextView.getId());


        // Available TextView
        TableRow.LayoutParams layoutParamsAvailableTxt = new TableRow.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParamsAvailableTxt.setMarginStart(pxToDp(200));

        TextView availableTextView = new TextView(getActivity());
        availableTextView.setLayoutParams(layoutParamsAvailableTxt);
        availableTextView.setGravity(Gravity.CENTER);
        availableTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 46);
        availableTextView.setTextColor(getResources().getColor(R.color.black));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            availableTextView.setTypeface(Typeface.create(getResources().getFont(R.font.intermedium), Typeface.NORMAL));
        }
        availableTextView.setText(data2);
        availableTextView.setId(View.generateViewId());


        // Creating the Decrease Button
        TableRow.LayoutParams layoutParamsIncreaseBtn = new TableRow.LayoutParams(pxToDp(172), ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParamsIncreaseBtn.setMarginStart(pxToDp(150));
        layoutParamsIncreaseBtn.topMargin = pxToDp(10);

        buttonDecrease = new Button(getActivity());
        buttonDecrease.setPadding(0, 0, 0, pxToDp(10));
        buttonDecrease.setLayoutParams(layoutParamsIncreaseBtn);
        buttonDecrease.setText("-");
        buttonDecrease.setTextSize(TypedValue.COMPLEX_UNIT_SP, 90);
        buttonDecrease.setBackground(getResources().getDrawable(R.drawable.bordered_button_red));
        buttonDecrease.setId(View.generateViewId());


        // Creating the TextView for the Count Number
        TableRow.LayoutParams layoutParamsNumberCountTxt = new TableRow.LayoutParams(
                pxToDp(145), ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParamsNumberCountTxt.setMarginStart(pxToDp(20));

        textViewNumber = new TextView(getActivity());
        textViewNumber.setPadding(pxToDp(30), pxToDp(30), pxToDp(30), pxToDp(30));
        textViewNumber.setLayoutParams(layoutParamsNumberCountTxt);
        textViewNumber.setText("0");
        textViewNumber.setBackground(getResources().getDrawable(R.drawable.rounded_corner_textview));
        textViewNumber.setTextSize(TypedValue.COMPLEX_UNIT_SP, 46);
        textViewNumber.setGravity(Gravity.CENTER);
        textViewNumber.setId(View.generateViewId());


        // Creating the Increase Button
        TableRow.LayoutParams layoutParamsBtnIncrease = new TableRow.LayoutParams(
                pxToDp(172), ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParamsBtnIncrease.topMargin = pxToDp(10);
        layoutParamsBtnIncrease.setMarginStart(pxToDp(20));

        buttonIncrease = new Button(getActivity());
        buttonIncrease.setPadding(0, 0, 0, pxToDp(10));
        buttonIncrease.setLayoutParams(layoutParamsBtnIncrease);
        buttonIncrease.setBackground(getResources().getDrawable(R.drawable.bordered_button_green));
        buttonIncrease.setTextSize(TypedValue.COMPLEX_UNIT_SP, 90);
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
        buttonDecreaseList.forEach((id, button) -> button.setOnClickListener(this));
        textViewAvailableList.forEach((id, textView) -> textView.setOnClickListener(this));
        textViewTypeList.forEach((id, textView) -> textView.setOnClickListener(this));
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



        Button nextStepBtn = view.findViewById(R.id.nextStepBtn);
        nextStepBtn.setOnClickListener(this);
        Button latticeCloseButton = view.findViewById(R.id.latticeCloseButton);
        tableLayout = view.findViewById(R.id.customTable);
        latticeCloseButton.setOnClickListener(this);
        addRowWithData("Small box", "10");
        addRowWithData("Small box", "2");
        addRowWithData("Small box", "13");
        addRowWithData("Small box", "17");
        addRowWithData("Small box", "17");
        addRowWithData("Small box", "17");
        addRowWithData("Small box", "17");
        addRowWithData("Small box", "17");
        addRowWithData("Small box", "17");

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        translatorUtils = new TranslatorUtils(getActivity());
        translatorUtils.convertAllText(sharedPreferences.getString("lg", ""), LatticeSelectionFragment.this, view);
        return view;
    }


    @SuppressLint({"ResourceType", "NonConstantResourceId"})
    @Override
    public void onClick(View view) {

        Objects.requireNonNull((MainActivity) getActivity()).resetTimeout();

        if (view.getId() == R.id.nextStepBtn) {
            MainActivity mainActivity = (MainActivity) getActivity();
            if (mainActivity != null) {
                mainActivity.replaceFragment(new DeliverScanFragment());
            }
            return;
        }

        if (view instanceof Button) {
            if (((Button) view).getText().equals("+")) {
                TextView textViewCount = textViewCountNumberList.get(view.getId() - 1);
                if (textViewCount != null) {
                    int i = Integer.parseInt(textViewCount.getText().toString()) + 1;
                    textViewCount.setText(String.valueOf(i));
                }
            } else if (((Button) view).getText().equals("-")) {
                TextView textViewCount = textViewCountNumberList.get(view.getId() + 1);
                if (textViewCount != null && Integer.parseInt(textViewCount.getText().toString()) > 0) {
                    int i = Integer.parseInt(textViewCount.getText().toString()) - 1;
                    textViewCount.setText(String.valueOf(i));
                }
            } else if (!((Button) view).getText().toString().equals("Next step")) {
                MainActivity mainActivity = (MainActivity) getActivity();
                if (mainActivity != null) {
                    mainActivity.replaceFragment(new MainFragment());
                }
            }
        }


    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        translatorUtils.convertAllText(sharedPreferences.getString("lg", ""), LatticeSelectionFragment.this, this.getView());
    }
}