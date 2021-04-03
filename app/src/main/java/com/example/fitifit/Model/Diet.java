package com.example.fitifit.Model;

public class Diet {

    private String food;
    private String comment;
    private String food_id;
    private String user_id;
    private String time;

    public Diet(){

    }

    public Diet(String food, String comment, String food_id, String user_id, String time) {
        this.food = food;
        this.comment = comment;
        this.food_id = food_id;
        this.user_id = user_id;
        this.time = time;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFood_id() {
        return food_id;
    }

    public void setFood_id(String food_id) {
        this.food_id = food_id;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
