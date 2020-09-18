package com.example.androidproject;

import java.util.ArrayList;
import java.util.List;

public class Users {

    private String email;
    private String password;
    private String image;
    private String name;
    public List<BrandListData> mList=new ArrayList<>();


    public Users() {
    }

    public Users(String email, String password, String image, String name, List<BrandListData> mList) {
        this.email = email;
        this.password = password;
        this.image = image;
        this.name = name;
        this.mList = mList;
    }





    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<BrandListData> getmList() {
        return mList;
    }

    public void setmList(List<BrandListData> mList) {
        this.mList = mList;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
