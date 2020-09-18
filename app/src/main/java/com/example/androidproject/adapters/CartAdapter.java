package com.example.androidproject.adapters;

import android.content.Context;
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

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder>{

    List<BrandListData> mList;
    Context mContext;


    public CartAdapter(List<BrandListData> mList,Context mContext) {
        this.mList = mList;
        this.mContext=mContext;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.mTxtProductName.setText(mList.get(position).getProductName());
        holder.mTxtProductPrice.setText("$"+mList.get(position).getPrice());
        Utils.getInstance().downloadImageByGlide(mContext,mList.get(position).getThumbnail(),holder.mImgProduct);
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }
    public void autoUpadate(){
        notifyDataSetChanged();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView mImgProduct;
        public TextView mTxtProductName,mTxtProductPrice;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mImgProduct=(ImageView) itemView.findViewById(R.id.imgCart);
            mTxtProductName=(TextView) itemView.findViewById(R.id.txtProductName);
            mTxtProductPrice=(TextView) itemView.findViewById(R.id.txtPrice);

        }
        // each data item is just a string in this case

    }

}
