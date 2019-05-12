package com.example.saurabh_pc.medcare3;

public class imagePres {
    String userId;
    String date;
    byte[] image;

    public String getUserId() {
        return userId;
    }

    public String getDate() {
        return date;
    }


    public byte[] getImage() {
        return image;
    }

    public imagePres(String userId, String date, byte[] image) {

        this.userId = userId;
        this.date = date;
        this.image = image;
    }
}
