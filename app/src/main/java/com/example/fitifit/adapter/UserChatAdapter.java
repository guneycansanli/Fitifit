package com.example.fitifit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fitifit.Model.UserProfileModel;
import com.example.fitifit.R;
import com.google.firebase.firestore.auth.User;

import java.util.List;

public class UserChatAdapter extends RecyclerView.Adapter<UserChatAdapter.ViewHolder> {

    private Context mContext;
    private List<UserProfileModel> mUsers;


    public UserChatAdapter(Context mContext, List<UserProfileModel> mUsers){
        this.mUsers= mUsers;
        this.mContext=mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item_chat, parent, false);
        return new UserChatAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       UserProfileModel user = mUsers.get(position);
       holder.username.setText(user.getName());
       if(user.getImage().equals("default")){
           holder.profile_image.setImageResource(R.mipmap.ic_launcher);
       }else{
           Glide.with(mContext).load(user.getImage()).into(holder.profile_image);
       }

    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView username;
        public ImageView profile_image;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.username);
            profile_image = itemView.findViewById(R.id.profile_image);
        }
    }




}
