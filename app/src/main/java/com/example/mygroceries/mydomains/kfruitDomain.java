package com.example.mygroceries.mydomains;

import java.io.Serializable;

public class kfruitDomain implements Serializable {
    public String  name;
    public String description;
    public String  price;
    public String image;

public kfruitDomain(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
