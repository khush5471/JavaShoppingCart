package com.example.androidproject.Views.fragments.Cart;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidproject.Model.Users;
import com.example.androidproject.Utils.FireBaseHandler;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class CartViewModel extends ViewModel {


    MutableLiveData<Users> mResponseCartUser = new MutableLiveData<>();

    /*Get the cart list of the current logined user.
    * */
    public void getCartListFromFirebase(final String mCurrentUserEmail) {
        FireBaseHandler.getInstance().getFirebaseDatabaseReference("USERS").child("ACCOUNTS").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //first check if data on firebase empty or not
                Users data = snapshot.getValue(Users.class);
                if (data == null) {
                    mResponseCartUser.setValue(null);
                } else {

                    for (DataSnapshot child : snapshot.getChildren()) {
                        Users userData = child.getValue(Users.class);
                        if (userData.getEmail().equals(mCurrentUserEmail)) {
                            mResponseCartUser.setValue(userData);
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
}
