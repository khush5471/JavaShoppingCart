package com.example.androidproject.Views.fragments.ViewProduct;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidproject.Model.BrandListData;
import com.example.androidproject.Model.Users;
import com.example.androidproject.Utils.FireBaseHandler;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewProductViewModel extends ViewModel {


    MutableLiveData<Users> mCurrentUserData = new MutableLiveData<>();
    MutableLiveData<Boolean> mIsAdded=new MutableLiveData<>();

    /*Get the data of the current loggined user*/
    public void getUsers(final String mCurrentUserEmail) {
        FireBaseHandler.getInstance().getFirebaseDatabaseReference("USERS").child("ACCOUNTS").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //first check if data on firebase empty or not
                Users data = snapshot.getValue(Users.class);
                if (data == null) {
                    mCurrentUserData.setValue(null);
                } else {
                    for (DataSnapshot child : snapshot.getChildren()) {
                        Users userData = child.getValue(Users.class);
                        if (userData.getEmail().equals(mCurrentUserEmail)) {
                            mCurrentUserData.setValue(userData);
                            break;
                        }
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    /*
    * Adding the selected items to the cart.
    * */
    public void addItemToCart(Users mCurrentUserDaata, BrandListData data, Activity activity) {
        if (mCurrentUserDaata.getmList() == null) {
            mCurrentUserDaata.mList = new ArrayList<>();
            mCurrentUserDaata.mList.add(data);
        } else {
            mCurrentUserDaata.getmList().add(data);
        }

        FireBaseHandler.getInstance().getFirebaseDatabaseReference("USERS").child("ACCOUNTS").child(mCurrentUserDaata.getName()).setValue(mCurrentUserDaata)
                .addOnSuccessListener(activity, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        mIsAdded.setValue(true);
                    }
                });

    }

}
