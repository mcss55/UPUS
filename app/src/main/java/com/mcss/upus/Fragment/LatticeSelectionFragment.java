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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.mcss.upus.Activity.MainActivity;
import com.mcss.upus.R;

public class LatticeSelectionFragment extends Fragment implements View.OnClickListener {
    public LatticeSelectionFragment() {
    }

    TableLayout tableLayout;
    TextView textViewNumber;
    Button  buttonDecrease, buttonIncrease;
    private int number = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void addRowWithData(String data1, String data2) {
        // Create a new TableRow
        TableRow newRow = new TableRow(getActivity());
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        newRow.setLayoutParams(layoutParams);

        // Create TextViews for each data item
        TextView textView1 = new TextView(getActivity());
        textView1.setLayoutParams(new TableRow.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                3f
        ));
        textView1.setGravity(Gravity.CENTER_HORIZONTAL);
        textView1.setTextSize(30);
        textView1.setTextColor(getResources().getColor(R.color.black));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            textView1.setTypeface(Typeface.create(getResources().getFont(R.font.intermedium), Typeface.NORMAL));
        }
        textView1.setText(data1);

        TextView textView2 = new TextView(getActivity());
        textView2.setLayoutParams(new TableRow.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                3f
        ));
        textView2.setGravity(Gravity.CENTER_HORIZONTAL);
        textView2.setTextSize(30);
        textView2.setTextColor(getResources().getColor(R.color.black));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            textView2.setTypeface(Typeface.create(getResources().getFont(R.font.intermedium), Typeface.NORMAL));
        }
        textView2.setText(data2);


        // increase/decrease button andtextview
        // Creating the Decrease Button

        buttonDecrease = new Button(getActivity());
        buttonDecrease.setId(R.id.buttonDecrease);
        buttonDecrease.setLayoutParams(new TableRow.LayoutParams(
                dpToPx(60), ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        buttonDecrease.setText("-");
        buttonDecrease.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
        buttonDecrease.setBackground(getResources().getDrawable(R.drawable.bordered_button_red));

// Creating the TextView for the Number

        textViewNumber = new TextView(getActivity());
        textViewNumber.setId(R.id.textViewNumber);
        textViewNumber.setLayoutParams(new TableRow.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        textViewNumber.setText("0");
        textViewNumber.setPadding(dpToPx(5), 0, dpToPx(5), 0);
        textViewNumber.setBackground(getResources().getDrawable(R.drawable.rounded_corner_textview));
        textViewNumber.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        textViewNumber.setLayoutParams(new TableRow.LayoutParams(
                0, ViewGroup.LayoutParams.WRAP_CONTENT, 0.37f
        ));
        textViewNumber.setGravity(Gravity.CENTER);

// Creating the Increase Button

        buttonIncrease = new Button(getActivity());
        buttonIncrease.setLayoutParams(new TableRow.LayoutParams(
                dpToPx(60), ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        buttonIncrease.setId(R.id.buttonIncrease);
        buttonIncrease.setPadding(0, 0, 30, 0);
        buttonIncrease.setBackground(getResources().getDrawable(R.drawable.bordered_button_green));
        buttonIncrease.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
        buttonIncrease.setText("+");


        // Add the TextViews to the TableRow
        newRow.addView(textView1);
        newRow.addView(textView2);
        newRow.addView(buttonDecrease);
        newRow.addView(textViewNumber);
        newRow.addView(buttonIncrease);


        // Add the TableRow to the TableLayout
        tableLayout.addView(newRow);
    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_lattice_selection, container, false);

//        buttonDecrease.setOnClickListener(this);
//        buttonIncrease.setOnClickListener(this);

        tableLayout = view.findViewById(R.id.customTable);

        addRowWithData("Salam","69");

        return view;
    }

    private void decreaseNumber() {
        number--;
        textViewNumber.setText(String.valueOf(number));
    }

    private void increaseNumber() {
        number++;
        textViewNumber.setText(String.valueOf(number));
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonDecrease:
                if (getAct() != null) {
                    decreaseNumber();
                }
                break;
            case R.id.buttonIncrease:
                if (getAct() != null) {
                    increaseNumber();
                }
                break;
            default:
                break;
        }
    }

    private MainActivity getAct() {
        return (MainActivity) getActivity();
    }
}