package com.example.androidproject.Views.fragments.Home;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidproject.Model.BrandCategories;
import com.example.androidproject.Model.BrandListData;
import com.example.androidproject.Model.Users;
import com.example.androidproject.Utils.FireBaseHandler;
import com.example.androidproject.Views.activities.DashBoardActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel {


    MutableLiveData<ArrayList<BrandListData>> mBrandProductList = new MutableLiveData<>();
    public  MutableLiveData<ArrayList<BrandCategories>> mBrandList = new MutableLiveData<>();
   public MutableLiveData<ArrayList<Users>> mUserListResponse=new MutableLiveData<>();

    /*Get the List of the products of the selected brands
    * */
    public void getProductList(String mSelectedBrand) {
        FireBaseHandler.getInstance().getFirebaseDatabaseReference("USERS").child("Brands").child(mSelectedBrand).child("mListBrandData").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.e("BRANDS_modelview", "" + snapshot.getValue());

                ArrayList<BrandListData> mListBrands = new ArrayList<>();

                if (null != null) {
                } else {
                    for (DataSnapshot child : snapshot.getChildren()) {
//                        Log.e("testing", "data " + child.getValue());
                        BrandListData userData = child.getValue(BrandListData.class);
                        mListBrands.add(userData);
//                        Log.e("okok", "Title = " + child.getKey() + " " + userData.getId() + " length " + userData.getProductName());
                    }
                }
                mBrandProductList.setValue(mListBrands);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    /*Get the list of the brands*/
    public void getBrandList() {
        FireBaseHandler.getInstance().getFirebaseDatabaseReference("USERS").child("BrandCategories").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ArrayList<BrandCategories> mListDrawer = new ArrayList<>();

                BrandCategories data = snapshot.getValue(BrandCategories.class);
                if (data == null) {
                } else {
                    for (DataSnapshot child : snapshot.getChildren()) {
                        BrandCategories userData = child.getValue(BrandCategories.class);
                        mListDrawer.add(userData);
                    }
                }

                mBrandList.setValue(mListDrawer);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /*
    * Get the list of the users that are registered.
    * */
    public void getUsers() {
        FireBaseHandler.getInstance().getFirebaseDatabaseReference("USERS").child("ACCOUNTS").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.e("SNAPSHOT", "" + snapshot.getValue());
                 ArrayList<Users> mUserList=new ArrayList<>();


                //first check if data on firebase empty or not
                Users data = snapshot.getValue(Users.class);
                if (data == null) {
//                    Toast.makeText(DashBoardActivity.this, "Data Empty", Toast.LENGTH_SHORT).show();
                } else {
                    mUserList.clear();
                    for (DataSnapshot child : snapshot.getChildren()) {
                        Users userData = child.getValue(Users.class);
                        mUserList.add(userData);
                        Log.e("++++++++", "Title = " + child.getKey() + " " + userData.getEmail() + " length " + mUserList.size());
                    }


                }

                mUserListResponse.setValue(mUserList);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}
