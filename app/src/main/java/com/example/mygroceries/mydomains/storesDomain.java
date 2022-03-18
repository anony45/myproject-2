package com.example.mygroceries.mydomains;

import java.io.Serializable;

public class storesDomain implements Serializable {
    String name;
    String image;

    public storesDomain(){

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
}
