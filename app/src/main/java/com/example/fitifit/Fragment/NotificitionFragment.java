package com.example.fitifit.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.fitifit.MyUserToDo;
import com.example.fitifit.R;

public class NotificitionFragment extends Fragment {

    private TextView myToDo;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=  inflater.inflate(R.layout.fragment_notificition, container, false);

        myToDo = view.findViewById(R.id.myToDo);

        myToDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyUserToDo.class);
                startActivity(intent);
                ((Activity) getActivity()).overridePendingTransition(0, 0);
            }
        });


        return view;



    }
}