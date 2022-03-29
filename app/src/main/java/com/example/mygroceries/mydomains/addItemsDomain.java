package com.example.mygroceries.mydomains;

public class addItemsDomain {

    String name,image, description,date,storeid,time,price;

    public addItemsDomain(){

    }

    public addItemsDomain(String name, String image, String description, String date, String storeid, String time,String price) {
        this.name = name;
        this.image = image;
        this.description = description;
        this.date = date;
        this.storeid = storeid;
        this.time = time;
        this.price=price;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStoreid() {
        return storeid;
    }

    public void setStoreid(String storeid) {
        this.storeid = storeid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
