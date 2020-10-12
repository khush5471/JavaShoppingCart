package com.example.androidproject.Views.fragments.SignUp;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidproject.Model.BrandListData;
import com.example.androidproject.Model.Users;
import com.example.androidproject.R;
import com.example.androidproject.Utils.FireBaseHandler;
import com.example.androidproject.Utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SignupViewModel extends ViewModel {

    MutableLiveData<String> mErrorResponse = new MutableLiveData<String>();
    MutableLiveData<String> mSuccessResponse = new MutableLiveData<String>();
    MutableLiveData<Boolean> mIsLoading = new MutableLiveData<Boolean>();
    MutableLiveData<ArrayList<String>> mRegisteredUserList = new MutableLiveData<>();

    /*Get the list of the users from the firebase*/
    public void getUsersListFromDataBase() {
        FireBaseHandler.getInstance().getFirebaseDatabaseReference("USERS").child("ACCOUNTS").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.e("SNAPSHOT", "" + snapshot.getValue());

                ArrayList<String> mListUsers = new ArrayList<>();

                //first check if data on firebase empty or not
                Users data = snapshot.getValue(Users.class);
                if (data == null) {
                    mErrorResponse.setValue("Data Empty");
                } else {
                    for (DataSnapshot child : snapshot.getChildren()) {
                        Users userData = child.getValue(Users.class);
                        mListUsers.add(child.getKey());
                        Log.e("okok", "Title = " + child.getKey() + " " + userData.getEmail() + " length " + mListUsers.size());
                    }
                }

                mRegisteredUserList.setValue(mListUsers);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /*signup the users*/
    public void signupUsers(final String name, final String email, final String password, String confirmPassword, final String mImgUrl, ArrayList<String> mUserList,
                            final Activity activity) {


        if (name.isEmpty() || name.length() <=1) {
            mErrorResponse.setValue(activity.getString(R.string.validation_name));
        } else if (mUserList.contains(name)) {
            mErrorResponse.setValue(activity.getString(R.string.error_user_alredy_exists));
        } else if (email.isEmpty()) {
            mErrorResponse.setValue(activity.getString(R.string.error_useralready_exists));
        } else if (!Utils.getInstance().validEmail(email)) {
            mErrorResponse.setValue(activity.getString(R.string.invalid_email));
        } else if (password.isEmpty()) {
            mErrorResponse.setValue(activity.getString(R.string.password_empty));
        } else if (password.length() < 5) {
            mErrorResponse.setValue(activity.getString(R.string.validation_password));
        } else if (confirmPassword.isEmpty()) {
            mErrorResponse.setValue(activity.getString(R.string.confirm_password_empty));
        } else if (!password.equals(confirmPassword)) {
            mErrorResponse.setValue(activity.getString(R.string.password_confirmpassword_mismatch));
        } else if (mImgUrl==null || mImgUrl.isEmpty()) {
            mErrorResponse.setValue(activity.getString(R.string.image_empty));
        } else {

            mIsLoading.setValue(true);
            FireBaseHandler.getInstance().getFireBaseAuth()
                    .createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()){
                                Log.e("in success","task");
                                FireBaseHandler.getInstance().getFirebaseDatabaseReference("ACCOUNTS")
                                        .child(name)
                                        .setValue(new Users(email, password, mImgUrl, name, new ArrayList<BrandListData>()));

                                getUsersListFromDataBase();
                                mIsLoading.setValue(false);
                                mSuccessResponse.setValue(activity.getString(R.string.user_registered));

                            }else {
                                mIsLoading.setValue(false);
                                mErrorResponse.setValue(""+task.getException().getMessage());
                            }
                        }
                    });

        }
    }
}
