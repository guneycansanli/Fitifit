package com.example.fitifit.HelperClasses;

public class FeaturedHelperClass {


    String title, desc, workout_id;

    public FeaturedHelperClass(){

    }

    public FeaturedHelperClass(String title, String desc, String workout_id) {
        this.title = title;
        this.desc = desc;
        this.workout_id = workout_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getWorkout_id() {
        return workout_id;
    }

    public void setWorkout_id(String workout_id) {
        this.workout_id = workout_id;
    }

}
