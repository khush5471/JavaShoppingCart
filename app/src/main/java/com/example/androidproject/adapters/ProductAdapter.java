package com.example.androidproject.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.BrandListData;
import com.example.androidproject.R;
import com.example.androidproject.Utils;
import com.example.androidproject.interfaces.ProductClickable;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    ArrayList<BrandListData> mBrandList;
    Context mContext;
    ProductClickable mProductClickable;

    public ProductAdapter(ArrayList<BrandListData> mBrandList, Context mContext, ProductClickable productClickable){
        Log.e("okok"," "+"okokokoko");

        this.mBrandList=new ArrayList<>();

        this.mBrandList=mBrandList;
        this.mContext=mContext;

        mProductClickable =productClickable;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_products,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.mTxtProductName.setText(mBrandList.get(position).getProductName());

        Utils.getInstance().downloadImageByGlide(mContext,mBrandList.get(position).getThumbnail(),holder.mImgBrand);
    }

    @Override
    public int getItemCount() {
        return mBrandList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView mImgBrand;
        public TextView mTxtProductName;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mImgBrand=(ImageView) itemView.findViewById(R.id.imgProduct);
            mTxtProductName=(TextView) itemView.findViewById(R.id.name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mProductClickable.getBrandData(mBrandList.get(getAdapterPosition()));
                }
            });

        }
        // each data item is just a string in this case

    }


}
