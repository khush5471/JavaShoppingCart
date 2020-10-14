package com.example.androidproject.Views.fragments.Home;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidproject.Model.BrandListData;
import com.example.androidproject.Utils.FireBaseHandler;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {


    MutableLiveData<ArrayList<BrandListData>> mBrandProductList = new MutableLiveData<>();


    public void getProductList(String mSelectedBrand) {
        FireBaseHandler.getInstance().getFirebaseDatabaseReference("USERS").child("Brands").child(mSelectedBrand).child("mListBrandData").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.e("BRANDS_modelview", "" + snapshot.getValue());

                List<BrandListData> mListBrands = new ArrayList<>();

                if (null != null) {
                } else {
                    for (DataSnapshot child : snapshot.getChildren()) {
//                        Log.e("testing", "data " + child.getValue());
                        BrandListData userData = child.getValue(BrandListData.class);
                        mListBrands.add(userData);
//                        Log.e("okok", "Title = " + child.getKey() + " " + userData.getId() + " length " + userData.getProductName());
                    }
                }
                mBrandProductList.setValue((ArrayList<BrandListData>) mListBrands);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
