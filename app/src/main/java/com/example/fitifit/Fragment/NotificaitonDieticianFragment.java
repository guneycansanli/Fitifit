package com.example.fitifit.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.fitifit.DieticianChatMainActivity;
import com.example.fitifit.R;
import com.example.fitifit.SelectForTodo;


public class NotificaitonDieticianFragment extends Fragment {

    private LinearLayout giveToDo;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=  inflater.inflate(R.layout.fragment_notificaiton_dietician, container, false);

        giveToDo = view.findViewById(R.id.give_todo);


                giveToDo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getActivity(), SelectForTodo.class);
                        startActivity(i);
                        ((Activity) getActivity()).overridePendingTransition(0, 0);
                    }
                });


        return view;
    }
}