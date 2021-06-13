package com.example.fitifit.Model;

public class DieticiansPackage {

    private String packetName;
    private String packetDesc;
    private String packetPrize;
    private String user_id;

    public DieticiansPackage(String packetName, String packetDesc, String packetPrize, String user_id) {
        this.packetName = packetName;
        this.packetDesc = packetDesc;
        this.packetPrize = packetPrize;
        this.user_id = user_id;
    }

    public String getPacketName() {
        return packetName;
    }

    public void setPacketName(String packetName) {
        this.packetName = packetName;
    }

    public String getPacketDesc() {
        return packetDesc;
    }

    public void setPacketDesc(String packetDesc) {
        this.packetDesc = packetDesc;
    }

    public String getPacketPrize() {
        return packetPrize;
    }

    public void setPacketPrize(String packetPrize) {
        this.packetPrize = packetPrize;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
