package com.example.androidproject.Views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.Model.BrandCategories;
import com.example.androidproject.Model.BrandListData;
import com.example.androidproject.Model.Users;
import com.example.androidproject.R;
import com.example.androidproject.Utils.Constats;
import com.example.androidproject.Utils.Utils;
import com.example.androidproject.Views.fragments.Home.HomeFragment;
import com.example.androidproject.Views.fragments.Home.HomeViewModel;
import com.example.androidproject.adapters.DrawerProductAdapter;
import com.example.androidproject.interfaces.ClickableInterface;

import java.util.ArrayList;

public class DashBoardActivity extends BaseActivity implements View.OnClickListener, ClickableInterface {


    private DrawerLayout mDrawer;
    private ImageView mImgDrawer, mImgViewUser, mImgCart;
    private TextView mTxtHeading;
    private Button mButtonLogout;
    private RecyclerView mRecyClerDrawer;
    private RecyclerView.Adapter mAdaterDrawer;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<BrandCategories> mListDrawer;
    private ArrayList<BrandListData> mListBrands;
    private ArrayList<Users> mUserList;
    private String mCurrentUser;
    private String mCurrentUserImage;
    private String mSelectedBrand = "Nike";
    private HomeViewModel mViewModel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        init();
        attachObservers();
        setListners();
        readDataFromFireBase();
        mCurrentUser = Utils.getInstance().readData(this, Constats.USER_EMAIL);
        Log.e("CURRENT_USER", mCurrentUser + "ss");
        addFragment(new HomeFragment(), false);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.mImgDrawer: {
                mDrawer.openDrawer(Gravity.LEFT);
            }
            break;

            case R.id.buttonLogout: {
                mDrawer.closeDrawer(Gravity.LEFT);
                finish();
                Intent intent = new Intent(DashBoardActivity.this, SecondActivity.class);
                startActivity(intent);
            }
            break;

            case R.id.mImgCart: {
                Intent intent = new Intent(DashBoardActivity.this, HolderActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(Constats.SELECTED_FRAGMENT, 2);
                intent.putExtra(Constats.PUT_EXTRA, bundle);
                startActivity(intent);
            }
            break;
        }
    }

    @Override
    public void getBrandListing(BrandCategories brandCategories) {
        mSelectedBrand = brandCategories.getBrandName();
        mTxtHeading.setText(brandCategories.getBrandName());
        mDrawer.closeDrawer(Gravity.LEFT);
        Fragment fragment = this.getSupportFragmentManager().findFragmentById(R.id.container);
        if (fragment instanceof HomeFragment) {
            ((HomeFragment) fragment).getSelectedBrandList(mSelectedBrand);
        }
    }

    /*Initialize all the variables
     * */
    private void init() {
        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        mRecyClerDrawer = findViewById(R.id.recyclerBrandList);
        mImgDrawer = findViewById(R.id.mImgDrawer);
        mImgViewUser = findViewById(R.id.imgUser);
        mImgCart = findViewById(R.id.mImgCart);

        mDrawer = findViewById(R.id.drawer_layout);
        mTxtHeading = findViewById(R.id.txtHeadnig);
        mButtonLogout = findViewById(R.id.buttonLogout);

        mListDrawer = new ArrayList<>();
        mUserList = new ArrayList<>();
    }

    /*
     * Setting click listners on views
     * */
    private void setListners() {
        mImgDrawer.setOnClickListener(this);
        mButtonLogout.setOnClickListener(this);
        mImgCart.setOnClickListener(this);
    }

    /*
     * Reads the data from firebase like brands and userlist*/
    private void readDataFromFireBase() {

//        /*Setting data to firebase. this method is only called once uring the first time.
//        * */
//        AddDataToFireBase.getInstance().setDataListToFirebase();

        mViewModel.getBrandList();
        mViewModel.getUsers();

    }

    private void attachObservers() {
        mViewModel.mBrandList.observe(this, new Observer<ArrayList<BrandCategories>>() {
            @Override
            public void onChanged(ArrayList<BrandCategories> brandCategories) {

                mListDrawer.clear();
                mListDrawer.addAll(brandCategories);
                layoutManager = new LinearLayoutManager(getApplicationContext());
                mRecyClerDrawer.setLayoutManager(layoutManager);
                mAdaterDrawer = new DrawerProductAdapter(mListDrawer, DashBoardActivity.this);
                mRecyClerDrawer.setAdapter(mAdaterDrawer);
            }
        });

        mViewModel.mUserListResponse.observe(this, new Observer<ArrayList<Users>>() {
            @Override
            public void onChanged(ArrayList<Users> users) {
                mUserList.clear();
                mUserList.addAll(users);
                setDataInViews();

            }
        });
    }

    /*Setting the user image as per the logined user.
     * */
    private void setDataInViews() {

        if (!mUserList.isEmpty()) {
            for (Users data1 : mUserList) {

                if (data1.getEmail().equals(mCurrentUser)) {
                    mCurrentUserImage = data1.getImage();
                    Log.e("@@@@@@", mCurrentUserImage);
                    break;
                }
            }
        }

        if (mCurrentUserImage != null && !mCurrentUserImage.isEmpty()) {
            Utils.getInstance().downloadImageByGlide(getApplicationContext(), mCurrentUserImage, mImgViewUser);
        }
    }


}
