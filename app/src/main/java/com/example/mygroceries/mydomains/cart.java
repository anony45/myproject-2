package com.example.mygroceries.mydomains;

import java.io.Serializable;

public class cart implements Serializable {
    private String pid,name,price,quantity,discount,image,totalprice;
    public cart (){

    }

    public cart(String pid, String name, String price, String quantity, String discount,String totalprice) {
        this.pid = pid;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.discount = discount;
        this.totalprice=totalprice;
    }

    public cart(String image) {
        this.image = image;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getTotalprice() {
        return totalprice;
    }
    public void setTotalprice(String totalprice) {
        this.totalprice = totalprice;
    }
}
