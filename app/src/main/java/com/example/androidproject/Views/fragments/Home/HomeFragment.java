package com.example.androidproject.Views.fragments.Home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.Model.BrandCategories;
import com.example.androidproject.Model.BrandListData;
import com.example.androidproject.R;
import com.example.androidproject.Utils.FireBaseHandler;
import com.example.androidproject.Views.activities.DashBoardActivity;
import com.example.androidproject.Views.fragments.BaseFragment;
import com.example.androidproject.adapters.ProductAdapter;
import com.example.androidproject.interfaces.ClickableInterface;
import com.example.androidproject.interfaces.ProductClickable;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends BaseFragment implements View.OnClickListener, ClickableInterface, ProductClickable {


    private DrawerLayout mDrawer;
    private ImageView mImgDrawer, mImgCart;
    private TextView mTxtHeading;
    private RecyclerView mRecyclerProduct, mRecyClerDrawer;
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
        mViewModel.getProductList(mSelectedBrand);

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void getBrandListing(BrandCategories brandCategories) {

    }

    @Override
    public void getBrandData(BrandListData brandCategories) {

    }

    /*Initialize the views*/
    private void init(View view) {
        mRecyclerProduct = view.findViewById(R.id.recyclerProducts);
        mImgCart = view.findViewById(R.id.mImgCart);
        mTxtHeading = view.findViewById(R.id.txtHeadnig);

        layoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        mRecyclerProduct.setLayoutManager(layoutManager);

        mListBrands = new ArrayList<>();

        mViewModel=new ViewModelProvider(this).get(HomeViewModel.class);

    }


    /*Attaching observer to get the data from the response
    * */
    private void attachObservers(){
        mViewModel.mBrandProductList.observe(this, new Observer<ArrayList<BrandListData>>() {
            @Override
            public void onChanged(ArrayList<BrandListData> brandListData) {
                mListBrands.clear();
                mListBrands.addAll(brandListData);
                mAdapter = new ProductAdapter(mListBrands, getContext(), HomeFragment.this);
                mRecyclerProduct.setAdapter(mAdapter);
            }
        });
    }

    private void getBrandListings() {
        mListBrands.clear();
        FireBaseHandler.getInstance().getFirebaseDatabaseReference("USERS").child("Brands").child(mSelectedBrand).child("mListBrandData").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.e("SNAPSHOT-BRANDS", "" + snapshot.getValue());

//                BrandListData data = snapshot.getValue(BrandListData.class);
                if (null != null) {
                } else {
                    for (DataSnapshot child : snapshot.getChildren()) {
//                        Log.e("testing", "data " + child.getValue());
                        BrandListData userData = child.getValue(BrandListData.class);
                        mListBrands.add(userData);
//                        Log.e("okok", "Title = " + child.getKey() + " " + userData.getId() + " length " + userData.getProductName());
                    }
                }


//                layoutManager = new LinearLayoutManager(getApplicationContext());
//                mRecyClerDrawer.setLayoutManager(layoutManager);
//                mAdaterDrawer = new DrawerProductAdapter(mListDrawer);
//                mRecyClerDrawer.setAdapter(mAdaterDrawer);

//                Log.e("DDDDD"," "+mListBrands.get(3).getProductName());
                mAdapter = new ProductAdapter(mListBrands, getContext(), HomeFragment.this);
                mRecyclerProduct.setAdapter(mAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    //This code is currently not in use as this code is used only used once for adding data to firebase
    private void setDataListToFirebase() {

//        myUserReferences.child("BrandCategories").child("Nike").setValue(new BrandCategories(1,"Nike"));
//        myUserReferences.child("BrandCategories").child("Puma").setValue(new BrandCategories(2,"Puma"));
//        myUserReferences.child("BrandCategories").child("Addidas").setValue(new BrandCategories(3,"Addidas"));
//        myUserReferences.child("BrandCategories").child("Reebok").setValue(new BrandCategories(4,"Reebok"));
//        myUserReferences.child("BrandCategories").child("Converse").setValue(new BrandCategories(5,"Converse"));
//        myUserReferences.child("BrandCategories").child("New Balance").setValue(new BrandCategories(6,"New Balance"));


        ArrayList<BrandListData> mList = new ArrayList<>();
//
//        Nike
//        mList.add(new BrandListData(1,
//                "name-1",
//                "https://static.nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/f3b230b1-af8b-4c18-a3e5-709ebbf74387/jordan-zoom-92-shoe-TQSxWl.jpg",
//                "LORELYPSUM",
//                120));
//        mList.add(new BrandListData(2,
//                "product-2",
//                "https://static.nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/febb00d3-8456-4629-9057-dd6f7c2cbf3e/air-zoom-pegasus-37-running-shoe-W3gMz9.jpg",
//                "LORELYPSUM",110));
//
//        mList.add(new BrandListData(3,
//                "product-3",
//                "https://static.nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/49684ee1-ddc1-44fa-9359-e9855e6040e5/ispa-drifter-split-shoe-DpWWrR.jpg",
//                "LORELYPSUM",145));
//
//        mList.add(new BrandListData(4,
//                "product-4",
//                "https://static.nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/c456d6a1-6816-49c6-9b7d-532172b88a30/jordan-delta-shoe-mfThHh.jpg",
//                "LORELYPSUM",111));
//
//        mList.add(new BrandListData(5,
//                "product-5",
//                "https://static.nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/7401318c-0cc6-4aba-8b94-66e046f3c9cd/mercurial-superfly-7-academy-mg-multi-ground-football-boot-QtGpc4.jpg",
//                "LORELYPSUM",80));
//
//
//        mList.add(new BrandListData(6,
//                "product-6",
//                "https://static.nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/8bd7e152-014a-4616-bf39-667f96bc1abd/air-jordan-og-shoe-P4xR0h.jpg",
//                "LORELYPSUM",75));
//
//        mList.add(new BrandListData(7,
//                "product-7",
//                "https://static.nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/5fad31e4-bb8a-49d3-be99-0787a451c367/air-jordan-1-low-shoe-f82H50.jpg",
//                "LORELYPSUM",88));
//
//        mList.add(new BrandListData(8,
//                "product-8",
//                "https://static.nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/5b312dc2-30c0-410c-9d68-e446df5e3ffd/air-jordan-1-low-shoe-f82H50.jpg",
//                "LORELYPSUM",330));
//
//        mList.add(new BrandListData(9,
//                "product-9",
//                "https://static.nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/6453190c-e31c-4413-8019-e514aeee0e08/blazer-mid-vintage-77-shoe-KtXHtn.jpg",
//                "LORELYPSUM",450));
//        mList.add(new BrandListData(10,
//                "product-10",
//                "https://static.nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/7e71d830-3c93-48bc-93dd-8c1a86a691c8/mercurial-superfly-7-academy-mbappe-rosa-mg-multi-ground-football-boot-1vjDcQ.jpg",
//                "LORELYPSUM",96));
//
//        mList.add(new BrandListData(11,
//                "product-11",
//                "https://static.nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/2cc02c77-d83e-4b38-9073-725af6870453/adapt-bb-2-basketball-shoe-r9z9tc.jpg",
//                "LORELYPSUM",66));
//        mList.add(new BrandListData(12,
//                "product-12",
//                "https://static.nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/c6d31d43-e06d-40f9-822c-77c13996c7f6/air-force-1-older-shoe-vtdCRQ.jpg",
//                "LORELYPSUM",180));
////
//        mList.add(new BrandListData(13,
//                "product-13",
//                "https://www.converse.com/on/demandware.static/-/Sites-ConverseMaster/default/dw093e8eda/images/hi-res/166800C_standard.jpg?sw=580&sh=580&sm=fit",
//                "LORELYPSUM",88));

        //converse
//        mList.add(new BrandListData(1,
//                "product-1",
//                "https://www.converse.ca/media/catalog/product/cache/1/image/1800x/040ec09b1e35df139433887a97daa66f/1/6/164945c_a_107x1_2.jpg",
//                "LORELYPSUM",125));
//
//        mList.add(new BrandListData(2,
//                "product-2",
//                "https://www.converse.ca/media/catalog/product/cache/1/image/9df78eab33525d08d6e5fb8d27136e95/2/0/2020may_168917c_a_107x1.jpg",
//                "LORELYPSUM",320));
//
//
//        mList.add(new BrandListData(3,
//                "product-3",
//                "https://www.converse.ca/media/catalog/product/cache/1/image/9df78eab33525d08d6e5fb8d27136e95/2/0/2020may_168917c_a_107x1.jpg",
//                "LORELYPSUM",456));
//
//        mList.add(new BrandListData(4,
//                "product-4",
//                "https://www.converse.ca/media/catalog/product/cache/1/image/1800x/040ec09b1e35df139433887a97daa66f/1/6/168913c_a_107x1.jpg",
//                "LORELYPSUM",155));
//
//        mList.add(new BrandListData(5,
//                "product-5",
//                "https://www.converse.ca/media/catalog/product/cache/1/image/9df78eab33525d08d6e5fb8d27136e95/2/0/2020may_168582c_a_107x1.jpg",
//                "LORELYPSUM",320));
//
//        mList.add(new BrandListData(6,
//                "product-6",
//                "https://www.converse.ca/media/catalog/product/cache/1/image/9df78eab33525d08d6e5fb8d27136e95/2/0/2020may_168582c_a_107x1.jpg",
//                "LORELYPSUM",330));
//
//        mList.add(new BrandListData(7,
//                "product-7",
//                "https://www.converse.ca/media/catalog/product/cache/1/image/1800x/040ec09b1e35df139433887a97daa66f/1/6/168668c_a_107x1.jpg",
//                "LORELYPSUM",180));
//        mList.add(new BrandListData(8,
//                "product-8",
//                "https://www.converse.ca/media/catalog/product/cache/1/image/9df78eab33525d08d6e5fb8d27136e95/5/6/565061c_a_107x1.jpg",
//                "LORELYPSUM",750));
//
//        mList.add(new BrandListData(9,
//                "product-9",
//                "https://www.converse.ca/media/catalog/product/cache/1/image/9df78eab33525d08d6e5fb8d27136e95/2/0/2020may_768409c_a_107x1.jpg",
//                "LORELYPSUM",330));
//
//        mList.add(new BrandListData(10,
//                "product-10",
//                "https://www.converse.ca/media/catalog/product/cache/1/image/9df78eab33525d08d6e5fb8d27136e95/6/6/667355f_a_107x1.jpg",
//                "LORELYPSUM",111));
//
//        mList.add(new BrandListData(11,
//                "product-11",
//                "https://www.converse.ca/media/catalog/product/cache/1/image/9df78eab33525d08d6e5fb8d27136e95/3/1/314786f_a_107x1.jpg",
//                "LORELYPSUM",120));
//
//        mList.add(new BrandListData(12,
//                "product-12",
//                "https://www.converse.ca/media/catalog/product/cache/1/image/1800x/040ec09b1e35df139433887a97daa66f/5/6/568650c_a_107x1.jpg",
//                "LORELYPSUM",330));

//
//
//PUMA
//
//        mList.add(new BrandListData(1,
//                "product-1",
//                "https://images.puma.com/image/upload/f_auto,q_auto,b_rgb:fafafa,w_2000,h_2000/global/371381/01/sv01/fnd/PNA/fmt/png/PUMA-x-THE-HUNDREDS-RS-Pure-Men's-Sneakers",
//                "LORELYPSUM",180));
//
//        mList.add(new BrandListData(2,
//                "product-2",
//                "https://images.puma.com/image/upload/f_auto,q_auto,b_rgb:fafafa,w_2000,h_2000/global/306499/05/sv01/fnd/PNA/fmt/png/Mercedes-AMG-Petronas-RS-X%C2%B3-Sneakers",
//                "LORELYPSUM",112));
//
//        mList.add(new BrandListData(3,
//                "product-3",
//                "https://images.puma.com/image/upload/f_auto,q_auto,b_rgb:fafafa,w_2000,h_2000/global/306499/03/sv01/fnd/PNA/fmt/png/Mercedes-AMG-Petronas-RS-X%C2%B3-Sneakers",
//                "LORELYPSUM",180));
//
//        mList.add(new BrandListData(4,
//                "product-4",
//                "https://images.puma.com/image/upload/f_auto,q_auto,b_rgb:fafafa,w_2000,h_2000/global/306499/05/sv01/fnd/PNA/fmt/png/Mercedes-AMG-Petronas-RS-X%C2%B3-Sneakers",
//                "LORELYPSUM",450));
//
//        mList.add(new BrandListData(5,
//                "product-5",
//                "https://images.puma.com/image/upload/f_auto,q_auto,b_rgb:fafafa,w_2000,h_2000/global/372115/02/sv01/fnd/PNA/fmt/png/RS-X%C2%B3-Gradient-Women's-Sneakers",
//                "LORELYPSUM",111));
//
//        mList.add(new BrandListData(6,
//                "product-6",
//                "https://images.puma.com/image/upload/f_auto,q_auto,b_rgb:fafafa,w_2000,h_2000/global/373797/06/sv01/fnd/PNA/fmt/png/RS-X%C2%B3-Puzzle-Women's-Sneakers",
//                "LORELYPSUM",320));
//
//
//        mList.add(new BrandListData(7,
//                "product-7",
//                "https://images.puma.com/image/upload/f_auto,q_auto,b_rgb:fafafa,w_2000,h_2000/global/370510/01/sv01/fnd/PNA/fmt/png/PUMA-Vikky-v2-Suede-Sneakers-JR",
//                "LORELYPSUM",125));
//
//        mList.add(new BrandListData(8,
//                "product-8",
//                "https://images.puma.com/image/upload/f_auto,q_auto,b_rgb:fafafa,w_2000,h_2000/global/368596/01/sv01/fnd/PNA/fmt/png/Turino-Leather-Little-Kids'-Shoes",
//                "LORELYPSUM",180));
//
//        mList.add(new BrandListData(9,
//                "product-9",
//                "https://images.puma.com/image/upload/f_auto,q_auto,b_rgb:fafafa,w_2000,h_2000/global/372039/01/sv01/fnd/PNA/fmt/png/Anzarun-Little-Kids'-Shoes",
//                "LORELYPSUM",150));
//
//        mList.add(new BrandListData(10,
//                "product-10",
//                "https://images.puma.com/image/upload/f_auto,q_auto,b_rgb:fafafa,w_2000,h_2000/global/372714/01/sv01/fnd/PNA/fmt/png/PUMA-x-CENTRAL-SAINT-MARTINS-Cali-Women's-Sneakers",
//                "LORELYPSUM",120));

        //Addidas
//        mList.add(new BrandListData(1,
//                "product-1",
//                "https://assets.adidas.com/images/h_840,f_auto,q_auto:sensitive,fl_lossy/f34e97dd6e374fe4b582ab5300e3693d_9366/Prada_Superstar_Black_FW6679_FW6679_01_standard.jpg",
//                "LORELYPSUM",120));
//
//        mList.add(new BrandListData(2,
//                "product-2",
//                "https://assets.adidas.com/images/h_840,f_auto,q_auto:sensitive,fl_lossy/189b449050ef49b1aa68a8ba010163e6_9366/NMD_R1_Shoes_White_D96635_01_standard.jpg",
//                "LORELYPSUM",115));
//
//        mList.add(new BrandListData(3,
//                "product-3",
//                "https://assets.adidas.com/images/h_2000,f_auto,q_auto:sensitive,fl_lossy/97d279a9b36349719c1dabd50115a141_9366/NMD_R1_Shoes_White_FX8110_01_standard.jpg",
//                "LORELYPSUM",123));
//
//        mList.add(new BrandListData(4,
//                "product-4",
//                "https://assets.adidas.com/images/h_840,f_auto,q_auto:sensitive,fl_lossy/bad05624bbea46d8b284abfd014ad2d3_9366/Pharrell_Williams_Boost_Slides_Red_FY6140_01_standard.jpg",
//                "LORELYPSUM",180));
//
//        mList.add(new BrandListData(5,
//                "product-5",
//                "https://assets.adidas.com/images/h_840,f_auto,q_auto:sensitive,fl_lossy/df2c5c01282a48ed8e2dab5800e39ab0_9366/adidas_By_Stella_McCartney_Ultraboost_20_Shoes_White_FU8983_01_standard.jpg",
//                "LORELYPSUM",129));
//
//        mList.add(new BrandListData(6,
//                "product-6",
//                "https://assets.adidas.com/images/h_840,f_auto,q_auto:sensitive,fl_lossy/113fa5f1c6084042860dabd500feef91_9366/X_Ghosted.3_Firm_Ground_Cleats_Black_FW3545_01_standard.jpg",
//                "LORELYPSUM",133));
//
//
//        mList.add(new BrandListData(7,
//                "product-7",
//                "https://assets.adidas.com/images/h_840,f_auto,q_auto:sensitive,fl_lossy/95c19f27abb74432ae37a6a500ba9e76_9366/ULTRABOOST_LTD_Shoes_Black_BB4677_01_standard.jpg",
//                "LORELYPSUM",118));
//
//        mList.add(new BrandListData(8,
//                "product-8",
//                "https://assets.adidas.com/images/h_840,f_auto,q_auto:sensitive,fl_lossy/27770743527d4b20a606ab95005940d4_9366/Harden_Stepback_Shoes_Black_FW8486_01_standard.jpg",
//                "LORELYPSUM",120));
//
//        mList.add(new BrandListData(9,
//                "product-9",
//                "https://assets.adidas.com/images/h_840,f_auto,q_auto:sensitive,fl_lossy/799ddb826f2c424d9237abce00cc9c8a_9366/ZX_2K_Boost_Shoes_White_FY4004_01_standard.jpg",
//        "LORELYPSUM",150));

        //Reebok
//        mList.add(new BrandListData(1,
//                "product-1",
//                "https://assets.reebok.com/images/h_840,f_auto,q_auto:sensitive,fl_lossy/8f0b2c49355d495ca39aaac00127458b_9366/Club_C_85_Men's_Shoes_White_EG6426_01_standard.jpg",
//                "LORELYPSUM",111));
//
//        mList.add(new BrandListData(2,
//                "product-2",
//                "https://assets.reebok.com/images/h_840,f_auto,q_auto:sensitive,fl_lossy/474e2cc7ed4e4c009a31ab9100318080_9366/Reebok_Nano_X_Shoes_Black_EH3094_01_standard.jpg",
//                "LORELYPSUM",450));
//
//        mList.add(new BrandListData(3,
//                "product-3",
//                "https://assets.reebok.com/images/h_2000,f_auto,q_auto:sensitive,fl_lossy/b7dcf6e2a44f4b5e800aab5d017fa91a_9366/Reebok_Nano_X_Women's_Training_Shoes_White_FV6769_01_standard.jpg",
//                "LORELYPSUM",199));
//
//        mList.add(new BrandListData(4,
//                "product-4",
//                "https://assets.reebok.com/images/h_840,f_auto,q_auto:sensitive,fl_lossy/0cdc80e1291d4a6aa01bac0a0009f48d_9366/Question_Mid_Men's_Basketball_Shoes_White_FY1018_01_standard.jpg",
//                "LORELYPSUM",320));
//
//        mList.add(new BrandListData(5,
//                "product-5",
//                "https://assets.reebok.com/images/h_840,f_auto,q_auto:sensitive,fl_lossy/6bcdab0f935443a18b50aab80154adf5_9366/Club_C_85_Women's_Shoes_Black_EH0669_01_standard.jpg",
//                "LORELYPSUM",150));
//
//        mList.add(new BrandListData(6,
//                "product-6",
//                "https://assets.reebok.com/images/h_840,f_auto,q_auto:sensitive,fl_lossy/3eb60f6bb4f841678c87ac0d0186d06b_9366/Question_Mid_Shoes_Grade_School_White_FY1019_01_standard.jpg",
//                "LORELYPSUM",120));
//
//
//        mList.add(new BrandListData(7,
//                "product-7",
//                "https://assets.reebok.com/images/h_2000,f_auto,q_auto:sensitive,fl_lossy/395e35b992ce4fd2b2a1abaf0058e91a_9366/Reebok_Royal_Classic_Jogger_2_Shoes_Grey_FW8941_01_standard.jpg",
//                "LORELYPSUM",450));
//
//        mList.add(new BrandListData(8,
//                "product-8",
//                "https://assets.reebok.com/images/h_840,f_auto,q_auto:sensitive,fl_lossy/8e3285c287bf41fb9726abaf00b9e18f_9366/Reebok_Royal_Complete_CLN_2_Shoes_White_FW7447_01_standard.jpg",
//                "LORELYPSUM",123));
//
//        mList.add(new BrandListData(9,
//                "product-9",
//                "https://assets.reebok.com/images/h_840,f_auto,q_auto:sensitive,fl_lossy/db15402baeae4d3fbec0a9870118e0ce_9366/Reebok_Royal_Complete_2L_Multicolor_DV3980_01_standard.jpg",
//                "LORELYPSUM",111));


//NEW BALANCE

//        mList.add(new BrandListData(1,
//                "product-1",
//                "https://nb.scene7.com/is/image/NB/m990gl5_nb_02_i_b18d5b026e4d44da9d20?$pdpflexf2$&fmt=webp&wid=472&hei=472",
//                "LORELYPSUM",120));
//
//        mList.add(new BrandListData(2,
//                "product-2",
//                "https://nb.scene7.com/is/image/NB/mtarisgb_nb_02_i?$pdpflexf2$&fmt=webp&wid=472&hei=472",
//                "LORELYPSUM",133));
//
//        mList.add(new BrandListData(3,
//                "product-3",
//                "https://nb.scene7.com/is/image/NB/mw928hb3_nb_02_i?$pdpflexf2$&fmt=webp&wid=472&hei=472",
//                "LORELYPSUM",180));
//
//        mList.add(new BrandListData(4,
//                "product-4",
//                "https://nb.scene7.com/is/image/NB/mthierg5_nb_02_i?$dw_detail_main_lg$&bgc=f1f1f1&layer=1&bgcolor=f1f1f1&blendMode=mult&scale=10&wid=1600&hei=1600",
//                "LORELYPSUM",145));
//
//        mList.add(new BrandListData(5,
//                "product-5",
//                "https://nb.scene7.com/is/image/NB/ma900bk_nb_02_i?$pdpflexf2$&fmt=webp&wid=472&hei=472",
//                "LORELYPSUM",250));
//
//        mList.add(new BrandListData(6,
//                "product-6",
//                "https://nb.scene7.com/is/image/NB/w860g10_nb_02_i?$pdpflexf2$&fmt=webp&wid=472&hei=472",
//                "LORELYPSUM",183));
//
//
//        mList.add(new BrandListData(7,
//                "product-7",
//                "https://nb.scene7.com/is/image/NB/wl574anc_nb_02_i?$pdpflexf2$&fmt=webp&wid=472&hei=472",
//                "LORELYPSUM",199));
//
//        mList.add(new BrandListData(8,
//                "product-8",
//                "https://nb.scene7.com/is/image/NB/it455tb_nb_02_i?$pdpflexf2$&fmt=webp&wid=472&hei=472",
//                "LORELYPSUM",77));
//
//        mList.add(new BrandListData(9,
//                "product-9",
//                "https://nb.scene7.com/is/image/NB/ia680lk6_nb_02_i?$dw_detail_main_lg$&bgc=f1f1f1&layer=1&bgcolor=f1f1f1&blendMode=mult&scale=10&wid=1600&hei=1600",
//                "LORELYPSUM",222));
//
//        mList.add(new BrandListData(10,
//                "product-10",
//                "https://nb.scene7.com/is/image/NB/itravle1_nb_02_i?$pdpflexf2$&fmt=webp&wid=472&hei=472",
//                "LORELYPSUM",360));
//
//        myUserReferences.child("Brands").child("Addidas").setValue(new BrandEntity("Addidas", mList));
//        myUserReferences.child("Brands").child("Converse").setValue(new BrandEntity("Converse", mList));
//        myUserReferences.child("Brands").child("Puma").setValue(new BrandEntity("Puma", mList));
//        myUserReferences.child("Brands").child("Nike").setValue(new BrandEntity("Nike", mList));
//        myUserReferences.child("Brands").child("Reebok").setValue(new BrandEntity("Reebok", mList));
//        myUserReferences.child("Brands").child("New Balance").setValue(new BrandEntity("New Balance", mList));

    }
}
