package com.example.androidproject.Views.fragments.SignIn;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidproject.Utils.Constats;
import com.example.androidproject.Utils.FireBaseHandler;
import com.example.androidproject.Utils.Utils;
import com.example.androidproject.Views.activities.DashBoardActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInViewModel extends ViewModel {



    MutableLiveData<Task<AuthResult>> mLoginResponse =new MutableLiveData<Task<AuthResult>>();

    MutableLiveData<String> mErrorResponse =new MutableLiveData<String>();

    MutableLiveData<Boolean> mIsLoading =new MutableLiveData<Boolean>();

    public void loginUserUsingEmailPassword(String email, String password, Activity mActivity){

        if (email.isEmpty()) {
            mErrorResponse.setValue("Please enter email");
        } else if (!Utils.getInstance().validEmail(email)) {
            mErrorResponse.setValue("Enter valid email");
        } else if (password.isEmpty()) {
            mErrorResponse.setValue("Enter passeord");
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
