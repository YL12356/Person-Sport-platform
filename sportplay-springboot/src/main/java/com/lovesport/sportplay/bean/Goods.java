package com.lovesport.sportplay.bean;

public class Goods {
    private int id;
    private String category;
    private String pname;
    private String pimg;
    private Double price;


    public int getId() {
        return id;
    }
    public String getCategory() {
        return category;
    }
    public String getPname() {
        return pname;
    }
    public String getPimg() {
        return pimg;
    }
    public Double getPrice() {
        return price;
    }


    public void setId(int id) {
        this.id = id;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public void setPname(String pname) {
        this.pname = pname;
    }
    public void setPimg(String pimg) {
        this.pimg = pimg;
    }
    public void setPrice(Double price) {
        this.price = price;
    }


    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", category='" + category + '\'' +
                ", pname='" + pname + '\'' +
                ", pimg='" + pimg + '\'' +
                ", price=" + price +
                '}';
    }
}
