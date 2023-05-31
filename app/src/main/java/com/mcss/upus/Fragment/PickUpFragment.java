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


public class PickUpFragment extends Fragment implements View.OnClickListener {

    Button verifyCode, verifyFace, verifySwipe, verifyAccount, closeButton;

    public PickUpFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pick_up, container, false);
        verifyCode = (Button) view.findViewById(R.id.verifyByCodeBtn);
        verifyFace = (Button) view.findViewById(R.id.verifyByFace);
        verifySwipe = (Button) view.findViewById(R.id.verifyBySwipe);
        verifyAccount = (Button) view.findViewById(R.id.verifyByAcoount);

        closeButton = (Button) view.findViewById(R.id.pickUpCloseButton);

        verifyCode.setOnClickListener(this);
        verifyFace.setOnClickListener(this);
        verifySwipe.setOnClickListener(this);
        verifyAccount.setOnClickListener(this);

        closeButton.setOnClickListener(this);



        return view;

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.verifyByCodeBtn:
                if (getAct() != null){
                    getAct().replaceFragment(new PickUpVerificationCodeFragment());
                }
                break;
            case R.id.verifyByFace:
                Toast.makeText(getActivity(), "Coming soon...", Toast.LENGTH_SHORT).show();
                break;
            case R.id.verifyBySwipe:
                Toast.makeText(getActivity(), "Coming soon...", Toast.LENGTH_SHORT).show();
                break;
            case R.id.verifyByAcoount:
                if (getAct() != null){
                    getAct().openLoginDialog();
                }
                break;
            case R.id.pickUpCloseButton:
                if (getAct() != null){
                    getAct().replaceFragment(new MainFragment());
                }
                break;
            default:
                break;
        }
    }
    private MainActivity getAct(){
        return (MainActivity) getActivity();
    }
}