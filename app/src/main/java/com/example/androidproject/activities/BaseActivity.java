package com.example.androidproject.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.androidproject.R;

public class BaseActivity extends AppCompatActivity {


    void addFragment(Fragment fragment,Boolean addToBackStack){

        if(addToBackStack) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commitAllowingStateLoss();
        }else {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commitAllowingStateLoss();
        }
    }

}
