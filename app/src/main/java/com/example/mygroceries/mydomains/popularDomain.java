package com.example.mygroceries.mydomains;

import java.io.Serializable;

public class popularDomain implements Serializable {
    private String title;
    private int pic;
    private  String description;
    private double price;
    private int noOfItems;

    public popularDomain(String title, int pic, String description, double price, int noOfItems) {
        this.title = title;
        this.pic = pic;
        this.description = description;
        this.price = price;
        this.noOfItems = noOfItems;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getNoOfItems() {
        return noOfItems;
    }

    public void setNoOfItems(int noOfItems) {
        this.noOfItems = noOfItems;
    }
}
