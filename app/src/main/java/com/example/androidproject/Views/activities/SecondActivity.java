package com.example.androidproject.Views.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.androidproject.R;
import com.example.androidproject.Views.fragments.SignIn.SignInFragment;

public class SecondActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        addFragment(new SignInFragment(),false);

    }
}
