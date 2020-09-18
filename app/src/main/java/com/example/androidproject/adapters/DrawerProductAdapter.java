package com.example.androidproject.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.BrandCategories;
import com.example.androidproject.R;
import com.example.androidproject.interfaces.ClickableInterface;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class DrawerProductAdapter extends RecyclerView.Adapter<DrawerProductAdapter.MyViewHolder> {

    ArrayList<BrandCategories> mList;
    ClickableInterface mClickableInterface;
    public DrawerProductAdapter(ArrayList<BrandCategories> list, ClickableInterface clickableInterface){

        mList=new ArrayList<>();
        mList=list;
        mClickableInterface=clickableInterface;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_brands,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.mTxtBrand.setText(mList.get(position).getBrandName());


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView mTxtBrand;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mTxtBrand=(TextView) itemView.findViewById(R.id.txtBrands);

            mTxtBrand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickableInterface.getBrandListing(mList.get(getAdapterPosition()));
                }
            });
        }
    }
}
