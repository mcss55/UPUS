package com.mcss.upus.Fragment;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
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

import java.util.Scanner;

public class LatticeSelectionFragment extends Fragment implements View.OnClickListener {
    public LatticeSelectionFragment() {
    }

    TableLayout tableLayout;
    TextView textViewNumber;
    Button buttonDecrease, buttonIncrease;
    private int number = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void addRowWithData(String data1, String data2) {
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
        typeTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP,30);
        typeTextView.setTextColor(getResources().getColor(R.color.black));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            typeTextView.setTypeface(Typeface.create(getResources().getFont(R.font.intermedium), Typeface.NORMAL));
        }
        typeTextView.setText(data1);


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


        // increase/decrease button andtextview
        // Creating the Decrease Button

        buttonDecrease = new Button(getActivity());
//        buttonDecrease.setId(View.generateViewId());
        buttonDecrease.setLayoutParams(new TableRow.LayoutParams(pxToDp(60), ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        buttonDecrease.setText("-");
        buttonDecrease.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
        buttonDecrease.setBackground(getResources().getDrawable(R.drawable.bordered_button_red));

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


        // Add the TextViews to the TableRow
        newRow.addView(typeTextView);
        newRow.addView(availableTextView);
        newRow.addView(buttonDecrease);
        newRow.addView(textViewNumber);
        newRow.addView(buttonIncrease);


        // Add the TableRow to the TableLayout
        tableLayout.addView(newRow);
    }

    private int pxToDp(int dp){
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

        tableLayout = view.findViewById(R.id.customTable);

        addRowWithData("Small box", "10");
        addRowWithData("Small box", "2");
        addRowWithData("Small box", "13");
        addRowWithData("Small box", "17");

        return view;
    }

    private void decreaseNumber() {
        number--;
        textViewNumber.setText(String.valueOf(number));
    }

    private int intToSp(int sp){
        float scale = getResources().getDisplayMetrics().scaledDensity;
        return  (int) (sp * scale);
    }

    private void increaseNumber() {
        number++;
        textViewNumber.setText(String.valueOf(number));
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.buttonDecrease:
//                if (getAct() != null) {
//                    decreaseNumber();
//                }
//                break;
//            case R.id.buttonIncrease:
//                if (getAct() != null) {
//                    increaseNumber();
//                }
//                break;
//            default:
//                break;
//        }
    }

    private MainActivity getAct() {
        return (MainActivity) getActivity();
    }
}