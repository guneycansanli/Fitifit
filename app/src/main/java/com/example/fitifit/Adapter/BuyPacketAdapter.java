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
import com.example.fitifit.Model.PacketModel;
import com.example.fitifit.Model.UserProfileModel;
import com.example.fitifit.R;
import com.example.fitifit.UserToDo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class BuyPacketAdapter extends RecyclerView.Adapter<BuyPacketAdapter.ViewHolder> {

    private Context mContext;
    private List<PacketModel> mPacket;
    private FirebaseUser firebaseUser;
    private Intent intent;

    public BuyPacketAdapter(Context mContext, List<PacketModel> mPacket) {
        this.mContext = mContext;
        this.mPacket = mPacket;
    }

    @NonNull
    @Override
    public BuyPacketAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.packet_box,parent,false);

        return new BuyPacketAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BuyPacketAdapter.ViewHolder holder, int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final PacketModel packetModel = mPacket.get(position);
        holder.more.setVisibility(View.VISIBLE);

        holder.titlePacket.setText(packetModel.getPacketName());
        holder.descPacket.setText(packetModel.getPacketDesc());
        holder.prize.setText(packetModel.getPacketPrize());

        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(mContext, PacketModel.class);
                intent.putExtra("userid",packetModel.getUser_id());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mPacket.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView titlePacket, descPacket, prize;
        public Button more;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titlePacket = itemView.findViewById(R.id.titlePacket);
            descPacket = itemView.findViewById(R.id.descPacket);
            prize = itemView.findViewById(R.id.prize);
            more = itemView.findViewById(R.id.more);


        }

    }


}
