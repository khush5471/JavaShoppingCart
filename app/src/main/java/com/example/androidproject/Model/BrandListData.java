package com.example.androidproject.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class BrandListData implements Parcelable {

    int id;
    String productName;
    String thumbnail;
    String description;
    int price;


    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public BrandListData() {
    }

    public BrandListData(int id, String productName, String thumbnail, String description, int price) {
        this.id = id;
        this.productName = productName;
        this.thumbnail = thumbnail;
        this.description = description;
        this.price = price;
    }






    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }





    protected BrandListData(Parcel in) {
        id = in.readInt();
        productName = in.readString();
        thumbnail = in.readString();
        description = in.readString();
        price = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(productName);
        dest.writeString(thumbnail);
        dest.writeString(description);
        dest.writeInt(price);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<BrandListData> CREATOR = new Parcelable.Creator<BrandListData>() {
        @Override
        public BrandListData createFromParcel(Parcel in) {
            return new BrandListData(in);
        }

        @Override
        public BrandListData[] newArray(int size) {
            return new BrandListData[size];
        }
    };
}