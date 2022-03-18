package com.example.mygroceries.mydomains;

public class items {
    private String decription,date,time,image;
            public items(){

            }

    public items(String decription, String date, String time, String image) {
        this.decription = decription;
        this.date = date;
        this.time = time;
        this.image = image;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
