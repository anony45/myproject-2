package com.example.mygroceries.mydomains;

import java.io.Serializable;

public class edititemsdomain implements Serializable {
    private String  name;
    private String description;
    private String  price;
    private String image;
    edititemsdomain(){
    }
    public edititemsdomain(String name, String description, String price, String image) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
