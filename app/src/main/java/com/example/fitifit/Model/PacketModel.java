package com.example.fitifit.Model;

public class PacketModel {

   private String PacketDesc, PacketName, PacketPrize, user_id;

    public PacketModel(String packetDesc, String packetName, String packetPrize, String user_id) {
        PacketDesc = packetDesc;
        PacketName = packetName;
        PacketPrize = packetPrize;
        this.user_id = user_id;
    }
    public PacketModel() {

    }

    public String getPacketDesc() {
        return PacketDesc;
    }

    public void setPacketDesc(String packetDesc) {
        PacketDesc = packetDesc;
    }

    public String getPacketName() {
        return PacketName;
    }

    public void setPacketName(String packetName) {
        PacketName = packetName;
    }

    public String getPacketPrize() {
        return PacketPrize;
    }

    public void setPacketPrize(String packetPrize) {
        PacketPrize = packetPrize;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
