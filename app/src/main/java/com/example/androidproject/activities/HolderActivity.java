package com.example.androidproject.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.androidproject.BrandListData;
import com.example.androidproject.R;
import com.example.androidproject.Constats;
import com.example.androidproject.fragments.ViewCartFragment;
import com.example.androidproject.fragments.ViewProductFragment;

public class HolderActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Bundle data=getIntent().getBundleExtra(Constats.PUT_EXTRA);
        BrandListData brandEntity=(BrandListData) data.getParcelable(Constats.BUNDLE_DATA);
//        Log.e("DATA IS",brandEntity.getProductName());

        ViewProductFragment  productFragment=new ViewProductFragment();

        productFragment.setArguments(data);

        if (data.getInt(Constats.SELECTED_FRAGMENT)==1){
            addFragment(productFragment,false);

        }else if(data.getInt(Constats.SELECTED_FRAGMENT)==2){
            addFragment(new ViewCartFragment(),false);
        }

    }
}
