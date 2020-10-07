package com.example.androidproject.Views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.androidproject.Model.BrandListData;
import com.example.androidproject.Utils.Constats;
import com.example.androidproject.R;
import com.example.androidproject.Model.Users;
import com.example.androidproject.Utils.Utils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewProductFragment extends BaseFragment {

    ImageView mImgProduct,mImgBack;
    TextView mTxtHeading,mTxtPrice;
    Button mButtonAddToCart;

    FirebaseDatabase database;
    DatabaseReference myUserReferences;

    String mCurrentUserEmail;
    Users mCurrentUserDaata;
    BrandListData data;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_viewproduct, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mImgProduct = (ImageView) view.findViewById(R.id.imgProduct);
        mImgBack = (ImageView) view.findViewById(R.id.mImgBack);
        mTxtHeading = (TextView) view.findViewById(R.id.txtHeadnig);
        mButtonAddToCart = (Button) view.findViewById(R.id.buttonAddToCart);
        mTxtPrice = (TextView) view.findViewById(R.id.txtDescription);



        data = (BrandListData) getArguments().getParcelable(Constats.BUNDLE_DATA);

        Utils.getInstance().downloadImageByGlide(getContext(), data.getThumbnail(), mImgProduct);
        mTxtHeading.setText(data.getProductName());

        database = FirebaseDatabase.getInstance();
        myUserReferences = database.getReference("USERS");

        mCurrentUserEmail = Utils.getInstance().readData(getContext(), Constats.USER_EMAIL);
        Log.e("CURRENT_USER", "" + mCurrentUserEmail);
        getUsers();


        mButtonAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItemToCart();
            }
        });
        mTxtPrice.setText("Price - $"+data.getPrice());

        mImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }


    private void addItemToCart() {
        if (mCurrentUserDaata.getmList() == null) {
            mCurrentUserDaata.mList = new ArrayList<>();
            mCurrentUserDaata.mList.add(data);
        } else {
            mCurrentUserDaata.getmList().add(data);
        }

        myUserReferences.child("ACCOUNTS").child(mCurrentUserDaata.getName()).setValue(mCurrentUserDaata)
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                getUsers();
            }
        });

        sendEmail();
    }

    private void getUsers() {
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


//                    setDataInViews();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void sendEmail(){
        String emailBody="Product name - "+data.getProductName()+"\n"+"PRICE - "+data.getPrice();


        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/html");
        intent.putExtra(android.content.Intent.EXTRA_EMAIL,new String[] { mCurrentUserEmail});
        intent.putExtra(Intent.EXTRA_SUBJECT, "ITEM ADDED TO CART");
        intent.putExtra(Intent.EXTRA_TEXT, emailBody);
        startActivity(Intent.createChooser(intent, "Send Email"));

    }

}
