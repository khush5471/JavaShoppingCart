package com.example.androidproject.Views.fragments.Home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.Model.BrandListData;
import com.example.androidproject.R;
import com.example.androidproject.Utils.Constats;
import com.example.androidproject.Views.activities.HolderActivity;
import com.example.androidproject.Views.fragments.BaseFragment;
import com.example.androidproject.adapters.ProductAdapter;
import com.example.androidproject.interfaces.ProductClickable;

import java.util.ArrayList;

public class HomeFragment extends BaseFragment implements ProductClickable {


    private RecyclerView mRecyclerProduct;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<BrandListData> mListBrands;
    private String mSelectedBrand = "Nike";
    private HomeViewModel mViewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        attachObservers();
        Log.e("Home", "Fragment");
        getSelectedBrandList(mSelectedBrand);
    }


    @Override
    public void getBrandData(BrandListData brandCategories) {
        Intent intent = new Intent(getContext(), HolderActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constats.BUNDLE_DATA, brandCategories);
        bundle.putInt(Constats.SELECTED_FRAGMENT, 1);
        intent.putExtra(Constats.PUT_EXTRA, bundle);
        startActivity(intent);

    }

    /*Initialize the views*/
    private void init(View view) {

        mRecyclerProduct = view.findViewById(R.id.recyclerProducts);


        layoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        mRecyclerProduct.setLayoutManager(layoutManager);

        mListBrands = new ArrayList<>();

        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

    }

    /*
     * This public method will get the selected brand from the drawer and will render the recycelrview list accordingly*/
    public void getSelectedBrandList(String brand) {
        mSelectedBrand = brand;
        mViewModel.getProductList(mSelectedBrand);
    }

    /*Attaching observer to get the data from the response
     * */
    private void attachObservers() {
        Log.e("In", "OBSERVER");
        mViewModel.mBrandProductList.observe(getActivity(), new Observer<ArrayList<BrandListData>>() {
            @Override
            public void onChanged(ArrayList<BrandListData> brandListData) {
                mListBrands.clear();
                mListBrands.addAll(brandListData);
                mAdapter = new ProductAdapter(mListBrands, getContext(), HomeFragment.this);
                mRecyclerProduct.setAdapter(mAdapter);
            }
        });

    }


}
