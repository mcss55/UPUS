package com.mcss.upus.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.mcss.upus.Activity.MainActivity;
import com.mcss.upus.R;

public class MainFragment extends Fragment implements View.OnClickListener{
    Button dropOffButton, pickUpButton, registerButton, helpButton;

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
        registerButton = (Button) view.findViewById(R.id.registerBtn);
        helpButton = (Button) view.findViewById(R.id.helpBtn);

        dropOffButton.setOnClickListener(this);
        pickUpButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
        helpButton.setOnClickListener(this);

        return view;

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
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
            case R.id.registerBtn:
                Toast.makeText(getActivity(), "registerBtn", Toast.LENGTH_SHORT).show();
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
}