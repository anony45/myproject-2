package com.example.mygroceries.mydomains;

public class kvegetablesDomain {
    private String  title;
    private String  price;
    private int pic;

    public kvegetablesDomain(String title, String price, int pic) {
        this.title = title;
        this.price = price;
        this.pic = pic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }
}
