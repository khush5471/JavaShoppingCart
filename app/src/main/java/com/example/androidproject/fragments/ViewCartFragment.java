package com.example.androidproject.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.BrandListData;
import com.example.androidproject.Constats;
import com.example.androidproject.R;
import com.example.androidproject.Users;
import com.example.androidproject.Utils;
import com.example.androidproject.adapters.CartAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewCartFragment extends BaseFragment {

    private RecyclerView mRecyclerCart;
    private Button mBtnCheckout;
    ImageView mImgBack;


    private RecyclerView.Adapter mAdapterCart;
    private RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference myUserReferences;

    String mCurrentUserEmail;
    Users mCurrentUserDaata;
    BrandListData data;

    private List<BrandListData> mListCart=new ArrayList<>();



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerCart = (RecyclerView) view.findViewById(R.id.recyclerViewCart);
        mBtnCheckout = (Button) view.findViewById(R.id.buttonCheckout);
        mImgBack = (ImageView) view.findViewById(R.id.mImgBack);



        layoutManager = new LinearLayoutManager(getContext());
        mRecyclerCart.setLayoutManager(layoutManager);

//        mCartObject=

        mAdapterCart = new CartAdapter(mListCart,getContext());;
        mRecyclerCart.setAdapter(mAdapterCart);

        database = FirebaseDatabase.getInstance();
        myUserReferences = database.getReference("USERS");
        mCurrentUserEmail = Utils.getInstance().readData(getContext(), Constats.USER_EMAIL);
        Log.e("CURRENT_USER", "" + mCurrentUserEmail);
        getDataFroFireBase();
        mBtnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkout();
            }
        });

        mImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

    private void getDataFroFireBase(){
        myUserReferences.child("ACCOUNTS").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.e("SNAPSHOT", "" + snapshot.getValue());


                //first check if data on firebase empty or not
                Users data = snapshot.getValue(Users.class);
                if (data == null) {
                    Toast.makeText(getContext(), "Data Empty", Toast.LENGTH_SHORT).show();
                } else {
//                    mUserList.clear();
                    for (DataSnapshot child : snapshot.getChildren()) {
                        Users userData = child.getValue(Users.class);

                        if (userData.getEmail().equals(mCurrentUserEmail)) {
                            mCurrentUserDaata = userData;
                            break;
                        }


//                        mUserList.add(userData);
                        Log.e("++++++++", "Title = " + child.getKey() + " " + userData.getEmail() + " length ");
                    }

                    mListCart=mCurrentUserDaata.getmList();
                    Log.e("******", ""+mListCart.size());

                    mAdapterCart = new CartAdapter(mListCart,getContext());;
                    mRecyclerCart.setAdapter(mAdapterCart);//                    setDataInViews();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void checkout(){

        int totalPrice=0;
        for (BrandListData item: mListCart){

            totalPrice+=item.getPrice();
        }

        showToast("YOUR TOTAL PURCHASE PRICE IS $"+totalPrice);

    }
}
