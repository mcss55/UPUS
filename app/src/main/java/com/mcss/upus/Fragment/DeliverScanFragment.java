package com.mcss.upus.Fragment;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.mcss.upus.Activity.MainActivity;
import com.mcss.upus.R;
import com.mcss.upus.Util.TranslatorUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class DeliverScanFragment extends Fragment implements View.OnClickListener, SharedPreferences.OnSharedPreferenceChangeListener {
    TextView cabinetNumber, trackingNumber, cellPhoneNumber;
    Button btnInverntory, btnRemove;
    TableLayout tableLayout;
    SharedPreferences sharedPreferences;
    TranslatorUtils translatorUtils;
    HashMap<Integer, Button> inventoryButtonHashMap;
    HashMap<Integer, Button> removeButtonDecreaseHashMap;
    HashMap<Integer, TextView> cabinetNumberTxtHashMap;
    HashMap<Integer, TextView> trackingNumberTxtHashMap;
    ImageView closeButton;
    HashMap<Integer, TextView> cellPhoneNumberTxtHashMap;
    HashMap<Integer, List<String>> data;
    HashMap<String,List<String>> dataFromLattice;
    private static DeliverScanFragment instance = null;
    static int countDataKey;
    private final String TAG = "deliverTag";


    public DeliverScanFragment(HashMap<String, List<String>> dataFromLattice) {
        this.dataFromLattice = dataFromLattice;
        inventoryButtonHashMap = new HashMap<>();
        removeButtonDecreaseHashMap = new HashMap<>();
        cabinetNumberTxtHashMap = new HashMap<>();
        trackingNumberTxtHashMap = new HashMap<>();
        cellPhoneNumberTxtHashMap = new HashMap<>();

    }

    public static DeliverScanFragment getInstance() {
        return instance;
    }

    private int pxToDp(int dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                getResources().getDisplayMetrics()
        );
    }

    public void addRowWithData(String data1, String data2, String data3) {

        TableRow tableRow = new TableRow(getActivity());

        // Cabinet Number

        cabinetNumber = new TextView(getActivity());
        cabinetNumber.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        cabinetNumber.setText(data1);
        cabinetNumber.setTextColor(Color.BLACK);
        cabinetNumber.setTextSize(TypedValue.COMPLEX_UNIT_SP, 34);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            cabinetNumber.setTypeface(Typeface.create(getResources().getFont(R.font.intersemibold), Typeface.NORMAL));
        }
        TableRow.LayoutParams layoutParams1 = (TableRow.LayoutParams) cabinetNumber.getLayoutParams();
        layoutParams1.setMarginStart(pxToDp(120));
        cabinetNumber.setLayoutParams(layoutParams1);
        cabinetNumber.setId(View.generateViewId());

        // Tracking Number

        trackingNumber = new TextView(getActivity());
        trackingNumber.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        trackingNumber.setText(data2);
        trackingNumber.setTextColor(Color.BLACK);
        trackingNumber.setTextSize(TypedValue.COMPLEX_UNIT_SP, 34);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            trackingNumber.setTypeface(Typeface.create(getResources().getFont(R.font.intersemibold), Typeface.NORMAL));
        }
        trackingNumber.setPadding(0, 0, 0, 0);
        TableRow.LayoutParams layoutParams2 = (TableRow.LayoutParams) trackingNumber.getLayoutParams();
        layoutParams2.setMarginStart(pxToDp(240));
        trackingNumber.setLayoutParams(layoutParams2);
        trackingNumber.setId(View.generateViewId());

        // Cell Phone Number

        cellPhoneNumber = new TextView(getActivity());
        cellPhoneNumber.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        cellPhoneNumber.setText(data3);
        cellPhoneNumber.setTextColor(Color.BLACK);
        cellPhoneNumber.setTextSize(TypedValue.COMPLEX_UNIT_SP, 34);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            cellPhoneNumber.setTypeface(Typeface.create(getResources().getFont(R.font.intersemibold), Typeface.NORMAL));
        }
        cellPhoneNumber.setPadding(0, 0, 0, 0);
        TableRow.LayoutParams layoutParams3 = (TableRow.LayoutParams) cellPhoneNumber.getLayoutParams();
        layoutParams3.setMarginStart(pxToDp(100));
        cellPhoneNumber.setLayoutParams(layoutParams3);
        cellPhoneNumber.setId(View.generateViewId());

        // Button Inventory

        btnInverntory = new Button(getActivity());
        btnInverntory.setId(View.generateViewId());
        btnInverntory.setLayoutParams(new TableRow.LayoutParams(pxToDp(192), pxToDp(85)));
        btnInverntory.setText("Inventory");
        btnInverntory.setTextColor(Color.WHITE);
        btnInverntory.setTextSize(TypedValue.COMPLEX_UNIT_SP, 31);
        btnInverntory.setBackgroundResource(R.drawable.bordered_button_green);
        btnInverntory.setPadding(pxToDp(10), 0, pxToDp(10), 0);
        TableRow.LayoutParams layoutParams4 = (TableRow.LayoutParams) btnInverntory.getLayoutParams();
        layoutParams4.setMarginStart(pxToDp(20));
        layoutParams4.bottomMargin = pxToDp(10);
        btnInverntory.setLayoutParams(layoutParams4);
        btnInverntory.setOnClickListener(this);

        // Button Remove

        btnRemove = new Button(getActivity());
        btnRemove.setId(View.generateViewId());
        btnRemove.setLayoutParams(new TableRow.LayoutParams(pxToDp(192), pxToDp(85)));
        btnRemove.setText("Remove");
        btnRemove.setTextColor(Color.WHITE);
        btnRemove.setTextSize(TypedValue.COMPLEX_UNIT_SP, 31);
        btnRemove.setBackgroundResource(R.drawable.bordered_button_red);
        btnRemove.setPadding(pxToDp(10), 0, pxToDp(10), 0);
        TableRow.LayoutParams layoutParams5 = (TableRow.LayoutParams) btnRemove.getLayoutParams();
        layoutParams5.setMarginStart(pxToDp(10));
        layoutParams5.setMarginEnd(pxToDp(10));
        btnRemove.setLayoutParams(layoutParams5);
        btnRemove.setOnClickListener(this);

        // Add views to table row
        tableRow.addView(cabinetNumber);
        tableRow.addView(trackingNumber);
        tableRow.addView(cellPhoneNumber);
        tableRow.addView(btnInverntory);
        tableRow.addView(btnRemove);

        // Add table row to table layout
        tableLayout.addView(tableRow);

        inventoryButtonHashMap.put(btnInverntory.getId(), btnInverntory);
        removeButtonDecreaseHashMap.put(btnRemove.getId(), btnRemove);
        trackingNumberTxtHashMap.put(trackingNumber.getId(), trackingNumber);
        cellPhoneNumberTxtHashMap.put(cellPhoneNumber.getId(), cellPhoneNumber);
        cabinetNumberTxtHashMap.put(cabinetNumber.getId(), cabinetNumber);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onResume() {
        super.onResume();
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_deliver_scan, container, false);
        tableLayout = view.findViewById(R.id.customTableDeliver);

        addRowWithData("A432", "A2sadsd", "+994501234567");
        addRowWithData("A433", "A2sads2", "+994503481507");
        addRowWithData("A434", "A2sads3", "+994503481503");

        for (Map.Entry<String, List<String>> stringListEntry : dataFromLattice.entrySet()) {
            Log.d(TAG, "onCreateView: data from lattice: "+stringListEntry.getKey()+" list: "+stringListEntry.getValue());
        }

        closeButton = view.findViewById(R.id.closeButtonDelivery);
        closeButton.setOnClickListener(this);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        translatorUtils = new TranslatorUtils(getActivity());

        translatorUtils.convertAllText(sharedPreferences.getString("lg", ""), DeliverScanFragment.this, view);
        instance=this;
        return view;

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        translatorUtils.convertAllText(sharedPreferences.getString("lg", ""), DeliverScanFragment.this, this.getView());
    }

    @Override
    @SuppressLint("ResourceType")
    public void onClick(View view) {

        Objects.requireNonNull((MainActivity) getActivity()).resetTimeout();

        countDataKey = 0;
        data = new HashMap<>();

        if (view instanceof Button) {

            if (((Button) view).getText().toString().equalsIgnoreCase("inventory")) {

                Button buttonInventory = (Button) view;
                TextView cabinetNumber = cabinetNumberTxtHashMap.get(buttonInventory.getId() - 3);
                TextView trackingNumber = trackingNumberTxtHashMap.get(buttonInventory.getId() - 2);
                TextView cellPhoneNumber = cellPhoneNumberTxtHashMap.get(buttonInventory.getId() - 1);
                data.put(countDataKey++, new ArrayList<>(Arrays.asList(cabinetNumber.getText().toString(),
                        trackingNumber.getText().toString(),
                        cellPhoneNumber.getText().toString())));
                Log.d("deliverytag", "onClick: " + data + " Cabinet: " + data.get(countDataKey - 1).get(0) + " achildi");

                ((MainActivity) getActivity()).openSearchDialog(data.get(countDataKey-1));

            } else if (((Button) view).getText().toString().equalsIgnoreCase("remove")) {

                Button buttonRemove = (Button) view;
                TextView cabinetNumber = cabinetNumberTxtHashMap.get(buttonRemove.getId() - 4);
                TextView trackingNumber = trackingNumberTxtHashMap.get(buttonRemove.getId() - 3);
                TextView cellPhoneNumber = cellPhoneNumberTxtHashMap.get(buttonRemove.getId() - 2);
                Button buttonInventory = inventoryButtonHashMap.get(buttonRemove.getId() - 1);
                ViewGroup rootView = (ViewGroup) buttonRemove.getParent();
                rootView.removeView(cabinetNumber);
                rootView.removeView(trackingNumber);
                rootView.removeView(cellPhoneNumber);
                rootView.removeView(buttonRemove);
                rootView.removeView(buttonInventory);

            }
        }else if (view.getId() == R.id.closeButtonDelivery){
            if (getActivity() != null){
                ((MainActivity) getActivity()).replaceFragment(new MainFragment());
            }
        }


    }
}