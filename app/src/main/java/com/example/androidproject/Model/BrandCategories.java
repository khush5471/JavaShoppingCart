package com.example.androidproject.Model;

public class BrandCategories {

    int id;
    String brandName;

    public BrandCategories() {
    }

    public BrandCategories(int id, String brandName) {
        this.id = id;
        this.brandName = brandName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }


}
