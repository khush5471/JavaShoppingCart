package com.example.androidproject.Views.fragments.SignIn;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidproject.Model.Users;
import com.example.androidproject.R;
import com.example.androidproject.Utils.FireBaseHandler;
import com.example.androidproject.Utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SignInViewModel extends ViewModel {


    MutableLiveData<Task<AuthResult>> mLoginResponse = new MutableLiveData<Task<AuthResult>>();

    MutableLiveData<String> mErrorResponse = new MutableLiveData<String>();

    MutableLiveData<Boolean> mIsLoading = new MutableLiveData<Boolean>();

    /*Login the user via firebase credintials*/
    public void loginUserUsingEmailPassword(String email, String password, Activity mActivity) {

        if (email.isEmpty()) {
            mErrorResponse.setValue(mActivity.getString(R.string.email_empty));
        } else if (!Utils.getInstance().validEmail(email)) {
            mErrorResponse.setValue(mActivity.getString(R.string.invalid_email));
        } else if (password.isEmpty()) {
            mErrorResponse.setValue(mActivity.getString(R.string.password_empty));
        } else {

            mIsLoading.setValue(true);

            FireBaseHandler.getInstance().getFireBaseAuth().signInWithEmailAndPassword(email, password).addOnCompleteListener(mActivity, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    mIsLoading.setValue(false);
                    mLoginResponse.setValue(task);
                }
            });
        }

    }


}
