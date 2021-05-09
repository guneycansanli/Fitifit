package com.example.fitifit.HelperClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fitifit.Adapter.DietAdapter;
import com.example.fitifit.Model.UserProfileModel;
import com.example.fitifit.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FeaturedAdapter extends RecyclerView.Adapter<FeaturedAdapter.MyViewHolder> {

    ArrayList<FeaturedHelperClass> featuredWorkouts;
    Context context;

    public FeaturedAdapter(Context c, ArrayList<FeaturedHelperClass> featuredWorkouts) {
        this.context = c;
        this.featuredWorkouts = featuredWorkouts;
    }

    @NonNull
    @Override
    public FeaturedAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new FeaturedAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.featured_card_design, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FeaturedAdapter.MyViewHolder holder, int position) {

        FeaturedHelperClass featuredHelperClass = featuredWorkouts.get(position);


        holder.title.setText(featuredHelperClass.getTitle());
        holder.desc.setText(featuredHelperClass.getDesc());


    }

    @Override
    public int getItemCount() {
        return featuredWorkouts.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{


        TextView title,desc;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            //Hokks

            title = itemView.findViewById(R.id.featured_title);
            desc = itemView.findViewById(R.id.featured_desc);

        }
    }

}
