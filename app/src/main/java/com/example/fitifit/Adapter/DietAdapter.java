package com.example.fitifit.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitifit.Model.Diet;
import com.example.fitifit.MyUserDietEdit;
import com.example.fitifit.MyUserToDoEdit;
import com.example.fitifit.R;

import java.util.ArrayList;

public class DietAdapter extends RecyclerView.Adapter<DietAdapter.MyViewHolder> {

    Context context;
    ArrayList<Diet> myDiet;


    public DietAdapter(Context c, ArrayList<Diet> p){
        context = c;
        myDiet = p;
    }

    @NonNull
    @Override
    public DietAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DietAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_diet, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull DietAdapter.MyViewHolder myViewHolder, int position) {
        myViewHolder.food.setText(myDiet.get(position).getFood());
        myViewHolder.comment.setText(myDiet.get(position).getComment());

        final String getFood = myDiet.get(position).getFood();
        final String getComment = myDiet.get(position).getComment();
        final String getFood_id = myDiet.get(position).getFood_id();
        final String getUser_id = myDiet.get(position).getUser_id();
        final String getTime = myDiet.get(position).getTime();


        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aa= new Intent(context, MyUserDietEdit.class);
                aa.putExtra("food", getFood);
                aa.putExtra("comment", getComment);
                aa.putExtra("food_id", getFood_id);
                aa.putExtra("user_id", getUser_id);
                aa.putExtra("time", getTime);
                context.startActivity(aa);

            }
        });
    }

    @Override
    public int getItemCount() {
        return myDiet.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView food, comment;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            food = (TextView) itemView.findViewById(R.id.food);
            comment= (TextView) itemView.findViewById(R.id.comment);

        }
    }

}
