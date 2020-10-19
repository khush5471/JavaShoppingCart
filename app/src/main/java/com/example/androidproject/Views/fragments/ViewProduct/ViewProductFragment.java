package com.example.androidproject.Views.fragments.ViewProduct;

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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidproject.Model.BrandListData;
import com.example.androidproject.Model.Users;
import com.example.androidproject.R;
import com.example.androidproject.Utils.Constats;
import com.example.androidproject.Utils.Utils;
import com.example.androidproject.Views.fragments.BaseFragment;

public class ViewProductFragment extends BaseFragment implements View.OnClickListener {

    private ImageView mImgProduct, mImgBack;
    private TextView mTxtHeading, mTxtPrice;
    private Button mButtonAddToCart;
    private String mCurrentUserEmail;
    private Users mCurrentUserDaata;
    private BrandListData data;

    private ViewProductViewModel mViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_viewproduct, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);
        setDatainViewsFromBundle();
        attachObserver();
        setListners();
        mCurrentUserEmail = Utils.getInstance().readData(getContext(), Constats.USER_EMAIL);
        mViewModel.getUsers(mCurrentUserEmail);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.buttonAddToCart: {
                mViewModel.addItemToCart(mCurrentUserDaata, data, getActivity());
            }
            break;

            case R.id.mImgBack: {
                getActivity().onBackPressed();
            }
            break;
        }
    }


    /* Initializing the views*/
    private void init(View view) {
        mViewModel = new ViewModelProvider(this).get(ViewProductViewModel.class);
        mImgProduct = view.findViewById(R.id.imgProduct);
        mImgBack = view.findViewById(R.id.mImgBack);
        mTxtHeading = view.findViewById(R.id.txtHeadnig);
        mButtonAddToCart = view.findViewById(R.id.buttonAddToCart);
        mTxtPrice = view.findViewById(R.id.txtDescription);
    }

    /*
     * Setts the data from the bundle in views.
     * */
    private void setDatainViewsFromBundle() {
        data = getArguments().getParcelable(Constats.BUNDLE_DATA);
        Utils.getInstance().downloadImageByGlide(getContext(), data.getThumbnail(), mImgProduct);
        mTxtHeading.setText(data.getProductName());
        mTxtPrice.setText("Price - $" + data.getPrice());
    }

    /*
     * Setting listners on views.*/
    private void setListners() {
        mImgBack.setOnClickListener(this);
        mButtonAddToCart.setOnClickListener(this);
    }

    /*Attaching observer to get the response data from the request.
     * */
    private void attachObserver() {
        mViewModel.mCurrentUserData.observe(this, new Observer<Users>() {
            @Override
            public void onChanged(Users users) {
                Log.e("In ,", "OBSERVERS 1");
                if (users == null) {
                    Toast.makeText(getContext(), "Data Empty", Toast.LENGTH_SHORT).show();
                } else {
                    mCurrentUserDaata = users;
                }
            }
        });

        mViewModel.mIsAdded.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Log.e("In ,", "OBSERVERS 2");

                if (aBoolean) {
                    mViewModel.getUsers(mCurrentUserEmail);
                    sendEmail();
                }
            }
        });
    }


    /*Sets the email to curreent user using email app and shows the list of items added to the cart
     * */
    private void sendEmail() {
        String emailBody = "Product name - " + data.getProductName() + "\n" + "PRICE - " + data.getPrice();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/html");
        intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{mCurrentUserEmail});
        intent.putExtra(Intent.EXTRA_SUBJECT, "ITEM ADDED TO CART");
        intent.putExtra(Intent.EXTRA_TEXT, emailBody);
        startActivity(Intent.createChooser(intent, "Send Email"));
    }

}
