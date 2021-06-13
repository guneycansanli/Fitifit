package com.example.fitifit.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fitifit.BuyPersonalActivity;
import com.example.fitifit.ChatMainActivity;
import com.example.fitifit.DieticianChatMainActivity;
import com.example.fitifit.MyUserDiet;
import com.example.fitifit.MyUserToDo;
import com.example.fitifit.MyUserWorkoutActivity;
import com.example.fitifit.R;
import com.example.fitifit.WaterActivity;
import com.example.fitifit.WaterTrackerActivity;

public class NotificitionFragment extends Fragment {

    private LinearLayout myToDo, btn_chatWithDietician, myWater, myDiet, mySport, buyDoctor;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notificition, container, false);

        myToDo = view.findViewById(R.id.myToDo);
        btn_chatWithDietician = view.findViewById(R.id.chatWithDietician);
        myWater = view.findViewById(R.id.myWater);
        myDiet = view.findViewById(R.id.myDiet);
        mySport = view.findViewById(R.id.mySport);
        buyDoctor = view.findViewById(R.id.buyDoctor);


        myToDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyUserToDo.class);
                startActivity(intent);
                ((Activity) getActivity()).overridePendingTransition(0, 0);
            }
        });
        btn_chatWithDietician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ChatMainActivity.class);
                startActivity(i);
                ((Activity) getActivity()).overridePendingTransition(0, 0);
            }
        });


        myWater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent waterIntent = new Intent(getActivity(), WaterTrackerActivity.class);
                startActivity(waterIntent);
                ((Activity) getActivity()).overridePendingTransition(0, 0);
            }
        });

        myDiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent waterIntent = new Intent(getActivity(), MyUserDiet.class);
                startActivity(waterIntent);
                ((Activity) getActivity()).overridePendingTransition(0, 0);
            }
        });

        mySport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mySportIntent = new Intent(getActivity(), MyUserWorkoutActivity.class);
                startActivity(mySportIntent);
                ((Activity) getActivity()).overridePendingTransition(0, 0);
            }
        });

        buyDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mySportIntent = new Intent(getActivity(), BuyPersonalActivity.class);
                startActivity(mySportIntent);
                ((Activity) getActivity()).overridePendingTransition(0, 0);
            }
        });

        return view;


    }
}