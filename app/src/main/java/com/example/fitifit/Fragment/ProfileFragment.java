package com.example.fitifit.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.fitifit.BlogWebActivity;
import com.example.fitifit.BmiCalculatorActivity;
import com.example.fitifit.CaloriCounter.GetStart;
import com.example.fitifit.ChatMainActivity;
import com.example.fitifit.DieticianChatMainActivity;
import com.example.fitifit.FitStopWatchActivity;
import com.example.fitifit.MainActivity;
import com.example.fitifit.Model.UserProfileModel;
import com.example.fitifit.R;
import com.example.fitifit.WaterActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ProfileFragment extends Fragment {

    private CardView btn_chat, btn_timing, btnBmi, caloriCounter, btn_water, blogWeb;
    
    private String currentUid;
    private DatabaseReference myRef,reference;
    private List<UserProfileModel> mUsers;


    public ProfileFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_profile, container, false);


        btn_timing = view.findViewById(R.id.btn_timing);
        btn_chat =view.findViewById(R.id.chatWithDietician);
        btnBmi = view.findViewById(R.id.btnBmi);
        caloriCounter = view.findViewById(R.id.caloriCounter);
        btn_water = view.findViewById(R.id.water);
        blogWeb = view.findViewById(R.id.blogWeb);

        mUsers = new ArrayList<>();


        btn_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ChatMainActivity.class);
                startActivity(i);
                ((Activity) getActivity()).overridePendingTransition(0, 0);

            }
        });

        btn_timing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), FitStopWatchActivity.class);
                startActivity(i);
                ((Activity) getActivity()).overridePendingTransition(0, 0);
            }
        });

        btnBmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), BmiCalculatorActivity.class);
                startActivity(i);
            }
        });

        caloriCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), GetStart.class);
                startActivity(i);
                ((Activity) getActivity()).overridePendingTransition(0, 0);
            }
        });
        btn_water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), WaterActivity.class);
                startActivity(i);
                ((Activity) getActivity()).overridePendingTransition(0, 0);
            }
        });

        blogWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), BlogWebActivity.class);
                startActivity(i);
                ((Activity) getActivity()).overridePendingTransition(0, 0);
            }
        });


        return view;
    }

}
