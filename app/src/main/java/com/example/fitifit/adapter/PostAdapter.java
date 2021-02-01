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
import com.example.fitifit.Model.Post;
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

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>{

    public Context mContext;
    public List<Post> mPost;

    private FirebaseUser currentFirebaseUser;

    public PostAdapter(Context mContext, List<Post> mPost) {
        this.mContext = mContext;
        this.mPost = mPost;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(mContext).inflate(R.layout.post_object,parent,false);
        return new PostAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        Post post = mPost.get(position);

        Glide.with(mContext).load(post.getPostImage()).into(holder.post_photo);

        if(post.getPostAbout().equals(""))
        {
            holder.txt_post_about.setVisibility(View.GONE);
        }
        else
        {
            holder.txt_post_about.setVisibility(View.VISIBLE);
            holder.txt_post_about.setText(post.getPostAbout());
        }

        posterInfo(holder.profile_photo, holder.txt_user_name, holder.txt_poster, post.getPostFrom());
    }

    @Override
    public int getItemCount() {
        return mPost.size();
    }

    public class  ViewHolder extends RecyclerView.ViewHolder{

        public ImageView profile_photo,post_photo,like_photo,comment_photo,save_photo;
        public TextView txt_user_name,txt_like,txt_poster,txt_post_about,txt_comments;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            profile_photo = itemView.findViewById(R.id.profile_photo_post_object);
            post_photo = itemView.findViewById(R.id.post_image_post_object);
            like_photo = itemView.findViewById(R.id.like_post);
            comment_photo = itemView.findViewById(R.id.comment_post_object);
            save_photo = itemView.findViewById(R.id.save_post_object);

            txt_user_name = itemView.findViewById(R.id.txt_username_post_object);
            txt_like = itemView.findViewById(R.id.txt_likes_post_object);
            txt_poster = itemView.findViewById(R.id.txt_send_post_object);
            txt_post_about = itemView.findViewById(R.id.txt_about_post_object);
            txt_comments = itemView.findViewById(R.id.txt_comment_post_object);

        }
    }

    private void posterInfo(ImageView profile_photo,TextView userName, TextView poster, String userId)
    {
        DatabaseReference dataPath = FirebaseDatabase.getInstance().getReference("Users").child(userId);
        dataPath.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserProfileModel userProfileModel = snapshot.getValue(UserProfileModel.class);
                Glide.with(mContext).load(userProfileModel.getImage()).into(profile_photo);
                userName.setText(userProfileModel.getName());
                poster.setText(userProfileModel.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
