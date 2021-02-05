package com.example.fitifit.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fitifit.EditActivity;
import com.example.fitifit.Model.ProfileModel;
import com.example.fitifit.R;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileHolder> {

    private List<ProfileModel> list;
    private Context mContext;
    private FirebaseUser firebaseUser;

    public ProfileAdapter(List<ProfileModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ProfileHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.profile_items,parent,false);
        return new ProfileHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileHolder holder, int position) {

        holder.nameTv.setText(list.get(position).getName());
        holder.emailTv.setText(list.get(position).getEmail());

        // load image using glide
        Glide.with(holder.itemView.getContext().getApplicationContext())
                .load(list.get(position).getImage())
                .placeholder(R.drawable.profile)
                .into(holder.profileImage);
        // add item click Listener
        //uid
        String userUID = list.get(position).getUid();
        holder.setDataOnClick(userUID);

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ProfileHolder extends RecyclerView.ViewHolder{

        private CircleImageView profileImage;
        private TextView nameTv, emailTv;

        public ProfileHolder(@NonNull View itemView) {
            super(itemView);

            profileImage = itemView.findViewById(R.id.profileImage);
            nameTv  = itemView.findViewById(R.id.nameTv);
            emailTv = itemView.findViewById(R.id.emailTv);
        }

        public void setDataOnClick(String userUID) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext().getApplicationContext(), EditActivity.class);
                    intent.putExtra("uid", userUID);
                   intent.putExtra("position", getAdapterPosition());
                   itemView.getContext().startActivity(intent);

                }
            });

        }
    }

}
