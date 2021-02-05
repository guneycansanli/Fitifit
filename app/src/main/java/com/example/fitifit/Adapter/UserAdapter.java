package com.example.fitifit.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fitifit.Fragment.ProfileFragment;
import com.example.fitifit.Model.UserProfileModel;
import com.example.fitifit.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context mContext;
    private List<UserProfileModel> mUsers;
    private FirebaseUser firebaseUser;

    public UserAdapter(Context mContext, List<UserProfileModel> mUsers) {
        this.mContext = mContext;
        this.mUsers = mUsers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(mContext).inflate(R.layout.user_profile,parent,false);

        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final UserProfileModel userProfileModel = mUsers.get(position);
        holder.btn_follow.setVisibility(View.VISIBLE);

        holder.userName.setText(userProfileModel.getName());
        holder.Uname.setText(userProfileModel.getName());
        Glide.with(mContext).load(userProfileModel.getImage()).into(holder.profile_photo);
        Following(userProfileModel.getUid(),holder.btn_follow);

        if(userProfileModel.getUid().equals(firebaseUser.getUid()))
        {
            holder.btn_follow.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = mContext.getSharedPreferences("PREFS",Context.MODE_PRIVATE).edit();
                editor.putString("profileid",userProfileModel.getUid());
                editor.apply();
                ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction().replace(R.id.frame_Layout,
                        new ProfileFragment()).commit();
            }
        });
        holder.btn_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.btn_follow.getText().toString().equals("Takip Et"))
                {
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid())
                            .child("Following").child(userProfileModel.getUid()).setValue(true);

                    FirebaseDatabase.getInstance().getReference().child("Follow").child(userProfileModel.getUid())
                            .child("Followers").child(userProfileModel.getUid()).setValue(true);
                }
                else
                {
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid())
                            .child("Following").child(userProfileModel.getUid()).removeValue();

                    FirebaseDatabase.getInstance().getReference().child("Follow").child(userProfileModel.getUid())
                            .child("Followers").child(userProfileModel.getUid()).removeValue();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView userName, Uname ;
        public CircleImageView profile_photo;
        public Button btn_follow;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.txt_user_name);
            Uname = itemView.findViewById(R.id.txt_Uname);
            profile_photo = itemView.findViewById(R.id.profilePhoto);
            btn_follow = itemView.findViewById(R.id.btn_follow);


        }

    }

    private void Following(String userId, Button button)
    {
        DatabaseReference followPath = FirebaseDatabase.getInstance().getReference().child("Follow")
                .child(firebaseUser.getUid()).child("Following");

        followPath.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(userId).exists())
                {
                    button.setText("Takip Ediliyor");
                }
                else
                {
                    button.setText("Takip Et");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
