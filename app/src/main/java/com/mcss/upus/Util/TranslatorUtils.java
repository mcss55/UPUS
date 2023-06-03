package com.mcss.upus.Util;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.mcss.upus.Core.DBHandler;
import com.mcss.upus.Fragment.LatticeSelectionFragment;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TranslatorUtils {
    private final DBHandler dbHandler;
    private final String TAG = "translatorD";

    public TranslatorUtils(Context context) {
        dbHandler = new DBHandler(context);
    }


    public void convertAllText(String lang, AppCompatActivity activity) {
        HashMap<String, String> data = dbHandler.readAllData(lang);
        Resources resources = activity.getResources();
        data.forEach((key, value) -> {
            int resourceId = resources.getIdentifier(key, "id", activity.getPackageName());
            if (resourceId != 0) {
                View viewById = activity.findViewById(resourceId);
                if (viewById instanceof Button) {
                    ((Button) viewById).setText(value);
                } else if (viewById instanceof TextView) {
                    ((TextView) viewById).setText(value);
                }

            }
        });
    }


    /*public void addRowWithDataAndConvertIts(HashMap<String, String> map, String lang) {

        HashMap<String, List<String>> data = dbHandler.readAllData();
        HashMap<String, String> dataSpecificLang = dbHandler.readAllData(lang);
        List<String> values = new ArrayList<>();

        data.forEach((key, value) ->
                map.forEach((keyActual, valueActual) -> {

                    if (value.contains(valueActual)) {
                        dataSpecificLang.forEach((keySpecific, valueSpecific) -> {
                            if (key.equals(keySpecific)) {
                                values.add(valueSpecific);
                            }
                        });
                    }
                }));


        HashMap<Integer, TextView> textViewTypeList = LatticeSelectionFragment.getTextViewTypeList();

        Set<Integer> integerSet = textViewTypeList.keySet();
        ArrayList<TextView> values1 = new ArrayList<>(textViewTypeList.values());
        ArrayList<Integer> integers = new ArrayList<>(integerSet);

        for (int i = 0; i < integers.size(); i++) {
            TextView textView = values1.get(i);
            textView.setText(values.get(i));
            textViewTypeList.put(integers.get(i), textView);
        }

        LatticeSelectionFragment.setTextViewTypeList(textViewTypeList);


    }

    *//*@SuppressLint({"ResourceType", "UseCompatLoadingForDrawables"})
    private void addRowWithData(String data1, String data2, AppCompatActivity activity, Map<Integer, Button> buttonIncreaseList, Map<Integer, Button> buttonDecreaseList){
        Button buttonDecrease, buttonIncrease;
        TextView textViewNumber;

        TableRow newRow = new TableRow(activity);
        newRow.setPadding(0, 0, 0, pxToDp(20));


        // Type TextView
        TableRow.LayoutParams layoutParamstypeTxt = new TableRow.LayoutParams(pxToDp(465),
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParamstypeTxt.setMarginStart(pxToDp(20));

        TextView typeTextView = new TextView(activity);
        typeTextView.setLayoutParams(layoutParamstypeTxt);
        typeTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        typeTextView.setGravity(Gravity.CENTER);
        typeTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 46);
        typeTextView.setTextColor(activity.getResources().getColor(R.color.black));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            typeTextView.setTypeface(Typeface.create(activity.getResources().getFont(R.font.intermedium), Typeface.NORMAL));
        }
        typeTextView.setText(data1);
        typeTextView.setId(1234567890);


        // Available TextView
        TableRow.LayoutParams layoutParamsAvailableTxt = new TableRow.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParamsAvailableTxt.setMarginStart(pxToDp(200));

        TextView availableTextView = new TextView(activity);
        availableTextView.setLayoutParams(layoutParamsAvailableTxt);
        availableTextView.setGravity(Gravity.CENTER);
        availableTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 46);
        availableTextView.setTextColor(activity.getResources().getColor(R.color.black));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            availableTextView.setTypeface(Typeface.create(activity.getResources().getFont(R.font.intermedium), Typeface.NORMAL));
        }
        availableTextView.setText(data2);
        availableTextView.setId(View.generateViewId());


        // Creating the Decrease Button
        TableRow.LayoutParams layoutParamsIncreaseBtn = new TableRow.LayoutParams(pxToDp(172), ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParamsIncreaseBtn.setMarginStart(pxToDp(150));
        layoutParamsIncreaseBtn.topMargin = pxToDp(10);

        buttonDecrease = new Button(activity);
        buttonDecrease.setPadding(0, 0, 0, pxToDp(10));
        buttonDecrease.setLayoutParams(layoutParamsIncreaseBtn);
        buttonDecrease.setText("-");
        buttonDecrease.setTextSize(TypedValue.COMPLEX_UNIT_SP, 90);
        buttonDecrease.setBackground(activity.getResources().getDrawable(R.drawable.bordered_button_red));
        buttonDecrease.setId(View.generateViewId());


        // Creating the TextView for the Count Number
        TableRow.LayoutParams layoutParamsNumberCountTxt = new TableRow.LayoutParams(
                pxToDp(145), ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParamsNumberCountTxt.setMarginStart(pxToDp(20));

        textViewNumber = new TextView(activity);
        textViewNumber.setPadding(pxToDp(30), pxToDp(30), pxToDp(30), pxToDp(30));
        textViewNumber.setLayoutParams(layoutParamsNumberCountTxt);
        textViewNumber.setText("0");
        textViewNumber.setBackground(activity.getResources().getDrawable(R.drawable.rounded_corner_textview));
        textViewNumber.setTextSize(TypedValue.COMPLEX_UNIT_SP, 46);
        textViewNumber.setGravity(Gravity.CENTER);
        textViewNumber.setId(View.generateViewId());


        // Creating the Increase Button
        TableRow.LayoutParams layoutParamsBtnIncrease = new TableRow.LayoutParams(
                pxToDp(172), ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParamsBtnIncrease.topMargin = pxToDp(10);
        layoutParamsBtnIncrease.setMarginStart(pxToDp(20));

        buttonIncrease = new Button(activity);
        buttonIncrease.setPadding(0, 0, 0, pxToDp(10));
        buttonIncrease.setLayoutParams(layoutParamsBtnIncrease);
        buttonIncrease.setBackground(activity.getResources().getDrawable(R.drawable.bordered_button_green));
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
    }*/

    public void converDialogText(String lang, Dialog dialog) {
        HashMap<String, String> data = dbHandler.readAllData(lang);
        Resources resources = dialog.getContext().getResources();
        data.forEach((key, value) -> {
            int resourceId = resources.getIdentifier(key, "id", dialog.getContext().getPackageName());
            if (resourceId != 0) {
                View viewById = dialog.findViewById(resourceId);
                if (viewById instanceof EditText) {
                    ((EditText) viewById).setHint(value);
                } else if (viewById instanceof TextView) {
                    ((TextView) viewById).setText(value);
                }
                if (viewById instanceof Button) {
                    ((Button) viewById).setText(value);
                }
            }
        });
    }




    public void convertDynamicTextViews(String lang,Fragment fragment, View viewRoot) {

        ViewGroup view = (ViewGroup) viewRoot;

        HashMap<String, String> dataAz = dbHandler.readAllData("AZ");
        HashMap<String, String> dataEn = dbHandler.readAllData("EN");
        HashMap<String, String> dataRu = dbHandler.readAllData("RU");

        int chidlCount = 0;
        if (view != null) {
            chidlCount = view.getChildCount();
        }

        Log.d(TAG, "convertDynamicTextViews: viewGroup.getChildCount() "+chidlCount);
        for (int i = 0; i < chidlCount; i++) {

            View child = view.getChildAt(i);
//            Log.d(TAG, "convertDynamicTextViews: view class: "+child.getClass().getName()); // TableRow

            if (child instanceof TableRow){

                TableRow tableRow = (TableRow) child;
                int tableRowChildCount = tableRow.getChildCount();

                for (int j = 0; j < tableRowChildCount; j++) {

                    View tableRowChild = tableRow.getChildAt(j);

                    if (tableRowChild instanceof TextView && !(tableRowChild instanceof Button)) {

                        TextView foundedTextView = (TextView) tableRowChild;

                        Log.d(TAG, "convertDynamicTextViews: foundedText: "+foundedTextView.getText().toString());

                        if (foundedTextView.getText().toString().contains("box")
                                || foundedTextView.getText().toString().contains("qutu")
                                || foundedTextView.getText().toString().contains("коробка")){

                            String ownText = foundedTextView.getText().toString();
                            Log.d(TAG, "convertDynamicTextViews: ownText: " + ownText);

                            HashMap<String, String> foundedMap = searchInHashMaps(ownText, dataAz, dataEn, dataRu);


                            if (foundedMap != null) {
                                Log.d(TAG, "convertDynamicTextViews: foundedMap if: " + foundedMap);
                                String key = extractKey(ownText, foundedMap);
                                Log.d(TAG, "convertDynamicTextViews: key: " + key);
                                if (key != null){
                                    final String value = dbHandler.readAllData(lang).get(key);
                                    ((TextView) tableRowChild).setText(value);
                                    ((LatticeSelectionFragment) fragment).updateTextView(value, j, i);
                                }
                            } else {
                                Log.d(TAG, "convertDynamicTextViews: else");
                            }
                        }
                    }
                }
            }
        }
    }

    public HashMap<String, String> searchInHashMaps(final String targetKey,
                                                    final HashMap<String, String> hashMap1,
                                                    final HashMap<String, String> hashMap2,
                                                    final HashMap<String, String> hashMap3) {

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        CompletionService<HashMap<String, String>> completionService = new ExecutorCompletionService<>(executorService);

        completionService.submit(() -> searchHashMap(targetKey, hashMap1));
        completionService.submit(() -> searchHashMap(targetKey, hashMap2));
        completionService.submit(() -> searchHashMap(targetKey, hashMap3));

        try {
            for (int i = 0; i < 3; i++) {
                Future<HashMap<String, String>> future = completionService.take();
                HashMap<String, String> resultMap = future.get();
                if (resultMap != null) {
                    return resultMap;
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
        return null;
    }

    private String extractKey(String targetValue, final HashMap<String, String> map){
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getValue().equals(targetValue)) {
                return entry.getKey();
            }
        }
        return null;
    }

    private HashMap<String, String> searchHashMap(String targetValue, HashMap<String, String> hashMap) {
        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            if (entry.getValue().equals(targetValue)) {
                return hashMap;
            }
        }
        return null;
    }


    public void convertAllText(String lang, Fragment fragment, View view) {
        HashMap<String, String> data = dbHandler.readAllData(lang);
        Resources resources = fragment.getResources();
        data.forEach((key, value) -> {
            int resourceId = resources.getIdentifier(key, "id", fragment.requireContext().getPackageName());
            if (resourceId != 0) {
                View viewById = view.findViewById(resourceId);
                if (viewById instanceof Button) {
                    ((Button) viewById).setText(value);
                } else if (viewById instanceof TextView) {
                    ((TextView) viewById).setText(value);
                }
            }
        });
    }

    private void traverseViewGroup(ViewGroup viewGroup, String key, String value) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = viewGroup.getChildAt(i);
            Log.d(TAG, "childViewId: " + childView.getId());
            if (childView instanceof TextView && !(childView instanceof Button)) {
                for (char c : ((TextView) childView).getText().toString().toCharArray()) {
                    if (Character.isDigit(c)) {
                        return;
                    }
                }
                TextView textView = (TextView) childView;
                Log.d(TAG, "childView instanceof TextView: " + textView.getText());
                Log.d(TAG, "Activity name: " + viewGroup.getContext().getClass().getName());
                if (textView.getText().equals(value)) {
                    textView.setText(value);
                    Log.d(TAG, "textView.getText().equals(key(" + key + ")) " + textView.getText());
                } else {
                    Log.d(TAG, "else of textView.getText().equals(key): " + "TextView text: " + textView.getText() + " Excpected text: " + key + " Would Translate to: " + value);
                }
            } else if (childView instanceof ViewGroup) {
                traverseViewGroup((ViewGroup) childView, key, value);
            }
        }
    }


}
