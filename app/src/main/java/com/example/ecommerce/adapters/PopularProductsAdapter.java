package com.example.ecommerce.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ecommerce.R;

import com.example.ecommerce.models.PopularProductsModel;

import java.util.List;

public class PopularProductsAdapter extends RecyclerView.Adapter<PopularProductsAdapter.ViewHolder> {

    private Context context;
    private List<PopularProductsModel> popularProductsModelList;

    public PopularProductsAdapter(Context context,List<PopularProductsModel> list) {
        this.context=context;
        this.popularProductsModelList = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PopularProductsAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_items,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PopularProductsAdapter.ViewHolder holder, int position) {

        Glide.with(context).load(popularProductsModelList.get(position).getImg_url()).into((holder.newImg));
        holder.newName.setText(popularProductsModelList.get(position).getName());
        holder.newPrice.setText(String.valueOf(popularProductsModelList.get(position).getPrice()));

    }

    @Override
    public int getItemCount() {
        return popularProductsModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView newImg;
        TextView newName,newPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            newImg=itemView.findViewById(R.id.new_img);
            newName=itemView.findViewById(R.id.new_product_name);
            newPrice=itemView.findViewById(R.id.new_price);
        }
    }
}
