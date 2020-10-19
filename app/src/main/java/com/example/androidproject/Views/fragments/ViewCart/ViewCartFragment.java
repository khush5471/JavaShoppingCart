package com.example.androidproject.Views.fragments.ViewCart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.Model.BrandListData;
import com.example.androidproject.Model.Users;
import com.example.androidproject.R;
import com.example.androidproject.Utils.Constats;
import com.example.androidproject.Utils.Utils;
import com.example.androidproject.Views.fragments.BaseFragment;
import com.example.androidproject.adapters.CartAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewCartFragment extends BaseFragment implements View.OnClickListener {

    private RecyclerView mRecyclerCart;
    private Button mBtnCheckout;
    private ImageView mImgBack;
    private CartAdapter mAdapterCart;
    private RecyclerView.LayoutManager layoutManager;
    String mCurrentUserEmail;
    private List<BrandListData> mListCart = new ArrayList<>();
    private ViewCartViewModel mViewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        attachObserver();
        setListners();
        mCurrentUserEmail = Utils.getInstance().readData(getContext(), Constats.USER_EMAIL);
        mViewModel.getCartListFromFirebase(mCurrentUserEmail);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.buttonCheckout: {
                checkout();
            }
            break;
            case R.id.mImgBack: {
                getActivity().onBackPressed();
            }
        }
    }

    /*
     * Initialoze the variables*/
    private void init(View view) {

        mViewModel = new ViewModelProvider(this).get(ViewCartViewModel.class);
        mRecyclerCart = view.findViewById(R.id.recyclerViewCart);
        mBtnCheckout = view.findViewById(R.id.buttonCheckout);
        mImgBack = view.findViewById(R.id.mImgBack);

        layoutManager = new LinearLayoutManager(getContext());
        mRecyclerCart.setLayoutManager(layoutManager);
        mAdapterCart = new CartAdapter(mListCart, getContext());
        mRecyclerCart.setAdapter(mAdapterCart);
    }


    /*
     * Attaching observer to get data from the requested api.
     * */
    private void attachObserver() {

        mViewModel.mResponseCartUser.observe(this, new Observer<Users>() {
            @Override
            public void onChanged(Users users) {

                if (users == null) {
                    Toast.makeText(getContext(), "Data Empty", Toast.LENGTH_SHORT).show();

                } else {
                    mListCart = users.getmList();
                    mAdapterCart.refreshData(mListCart);
                }
            }
        });
    }

    /*
     * Setting listners on views*/
    private void setListners() {
        mBtnCheckout.setOnClickListener(this);
        mImgBack.setOnClickListener(this);
    }

    /*Just show the sum up price on clicking checkout button*/
    private void checkout() {
        int totalPrice = 0;
        for (BrandListData item : mListCart) {
            totalPrice += item.getPrice();
        }
        showToast(getString(R.string.total_purchase_price) + totalPrice);
    }


}
