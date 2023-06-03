package com.mcss.upus.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceDataStore;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.denzcoskun.imageslider.constants.ActionTypes;
import com.denzcoskun.imageslider.interfaces.TouchListener;
import com.mcss.upus.Activity.MainActivity;
import com.mcss.upus.Activity.SlideShow;
import com.mcss.upus.R;
import com.mcss.upus.Util.TranslatorUtils;

import java.util.prefs.Preferences;

public class MainFragment extends Fragment implements View.OnClickListener, SharedPreferences.OnSharedPreferenceChangeListener {
    Button dropOffButton, pickUpButton, registerButton, helpButton;
    TranslatorUtils translatorUtils;


    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        dropOffButton = (Button) view.findViewById(R.id.dropOffBtn);
        pickUpButton = (Button) view.findViewById(R.id.pickUpBtn);
        helpButton = (Button) view.findViewById(R.id.helpBtn);

        dropOffButton.setOnClickListener(this);
        pickUpButton.setOnClickListener(this);
        helpButton.setOnClickListener(this);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        translatorUtils = new TranslatorUtils(getActivity());
        translatorUtils.convertAllText(sharedPreferences.getString("lg",""), MainFragment.this, view);
        return view;

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {

        getAct().resetTimeout();

        switch (view.getId()){
            case R.id.dropOffBtn:
                if (getAct() != null){
                    getAct().openLoginDialog();
                }
                break;
            case R.id.pickUpBtn:
                if (getAct() != null){
                    getAct().replaceFragment(new PickUpFragment());
                }
                break;
            case R.id.helpBtn:
                Toast.makeText(getActivity(), "helpBtn", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    private MainActivity getAct(){
        return (MainActivity) getActivity();
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        Log.d("mainTag", "onSharedPreferenceChanged: mainfragment: "+sharedPreferences);
        translatorUtils.convertAllText(sharedPreferences.getString("lg",""), MainFragment.this, this.getView());
    }
}