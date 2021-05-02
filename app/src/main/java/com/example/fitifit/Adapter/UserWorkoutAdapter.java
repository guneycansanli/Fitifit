package com.example.fitifit.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fitifit.Model.UserProfileModel;
import com.example.fitifit.R;
import com.example.fitifit.UserWorkoutActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserWorkoutAdapter extends RecyclerView.Adapter<UserWorkoutAdapter.ViewHolder> {

    private Context mContext;
    private List<UserProfileModel> mUsers;
    private FirebaseUser firebaseUser;
    private Intent intent;

    public UserWorkoutAdapter(Context mContext, List<UserProfileModel> mUsers) {
        this.mContext = mContext;
        this.mUsers = mUsers;
    }

    @NonNull
    @Override
    public UserWorkoutAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_profile, parent, false);

        return new UserWorkoutAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserWorkoutAdapter.ViewHolder holder, int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final UserProfileModel userProfileModel = mUsers.get(position);
        holder.btn_give_todo.setVisibility(View.VISIBLE);

        holder.userName.setText(userProfileModel.getName());
        holder.Uname.setText(userProfileModel.getName());
        if (userProfileModel.getImage().equals("default")) {
            holder.profile_photo.setImageResource(R.mipmap.ic_launcher);
        } else {
            Glide.with(mContext).load(userProfileModel.getImage()).into(holder.profile_photo);
        }

        holder.btn_give_todo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(mContext, UserWorkoutActivity.class);
                intent.putExtra("userid", userProfileModel.getUid());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView userName, Uname;
        public CircleImageView profile_photo;
        public Button btn_give_todo;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.txt_user_name);
            Uname = itemView.findViewById(R.id.txt_Uname);
            profile_photo = itemView.findViewById(R.id.profilePhoto);
            btn_give_todo = itemView.findViewById(R.id.btn_give_todo);


        }

    }
}
