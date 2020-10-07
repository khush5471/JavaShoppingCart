package com.example.androidproject.Model;

import java.util.ArrayList;

public class BrandEntity {

    String brandName;
    ArrayList<BrandListData> mListBrandData;

    public BrandEntity() {
    }
    public BrandEntity(String brandName, ArrayList<BrandListData> mListBrandData) {
        this.brandName = brandName;
        this.mListBrandData = mListBrandData;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public ArrayList<BrandListData> getmListBrandData() {
        return mListBrandData;
    }

    public void setmListBrandData(ArrayList<BrandListData> mListBrandData) {
        this.mListBrandData = mListBrandData;
    }
}
