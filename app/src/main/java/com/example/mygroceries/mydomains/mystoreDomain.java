package com.example.mygroceries.mydomains;

public class mystoreDomain {
    String itemimage,itemname,itemprice,itemdescription;
    public mystoreDomain(){

    }

    public mystoreDomain(String itemimage, String itemname, String itemprice, String itemdescription) {
        this.itemimage = itemimage;
        this.itemname = itemname;
        this.itemprice = itemprice;
        this.itemdescription = itemdescription;
    }

    public String getItemimage() {
        return itemimage;
    }

    public void setItemimage(String itemimage) {
        this.itemimage = itemimage;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getItemprice() {
        return itemprice;
    }

    public void setItemprice(String itemprice) {
        this.itemprice = itemprice;
    }

    public String getItemdescription() {
        return itemdescription;
    }

    public void setItemdescription(String itemdescription) {
        this.itemdescription = itemdescription;
    }
}
