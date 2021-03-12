package com.example.fitifit.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitifit.Model.ToDo;
import com.example.fitifit.MyUserToDoEdit;
import com.example.fitifit.R;

import java.util.ArrayList;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.MyViewHolder> {

    Context context;
    ArrayList<ToDo> myDoes;

    public ToDoAdapter(Context c, ArrayList<ToDo> p){
        context = c;
        myDoes = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_todo, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        myViewHolder.titledoes.setText(myDoes.get(position).getTitledoes());
        myViewHolder.descdoes.setText(myDoes.get(position).getDescdoes());
        myViewHolder.datedoes.setText(myDoes.get(position).getDatedoes());

        final String getTitleDoes = myDoes.get(position).getTitledoes();
        final String getDescDoes = myDoes.get(position).getDescdoes();
        final String getDateDoes = myDoes.get(position).getDatedoes();
        final String getTask = myDoes.get(position).getTask();

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aa= new Intent(context, MyUserToDoEdit.class);
                aa.putExtra("titledoes", getTitleDoes);
                aa.putExtra("descdoes", getDescDoes);
                aa.putExtra("datedoes", getDateDoes);
                aa.putExtra("task", getTask);    //hard to explain Türkce olarak diyetisyen to-go veriyo kullanıcı o to-do yaptığında silebilmesi icin yani roller karisiyo.. :DD
                context.startActivity(aa);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myDoes.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView titledoes, descdoes, datedoes;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titledoes = (TextView) itemView.findViewById(R.id.titledoes);
            descdoes = (TextView) itemView.findViewById(R.id.descdoes);
            datedoes = (TextView) itemView.findViewById(R.id.datedoes);
        }
    }
}
