package com.example.trial01.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trial01.R;
import com.example.trial01.model.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewProductAdapter extends RecyclerView.Adapter<RecyclerViewProductAdapter.MyViewHolder>{

//    private ListProductActivity mContext;
    private List<Product> mProduct;
    Context mContext;

//    public RecyclerViewProductAdapter(ListProductActivity listProductActivity, List<Product> mProduct) {
//        this.mContext = listProductActivity;
//        this.mProduct = mProduct;
//    }

    public RecyclerViewProductAdapter(Context context) {
        mProduct = new ArrayList<>();
        mContext = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_model_product, parent, false), i);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Product product = mProduct.get(position);
        holder.mId.setText(product.getId());
        holder.mName.setText(product.getName());
        holder.mId1.setText(product.getId());
        Picasso.with(mContext)
                .load(product.getImage())
                .placeholder(R.drawable.ic_product)
                .fit()
                .centerCrop()
                .into(holder.mImage);


    }

    @Override
    public int getItemCount() {
        return mProduct.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView mId, mId1, mName;
        private ImageView mImage;

        public MyViewHolder(View itemView, int i) {
            super(itemView);

            mId = itemView.findViewById(R.id.txt_id_0);
            mId1 = itemView.findViewById(R.id.txt_id_1);
            mName = itemView.findViewById(R.id.txt_name);
            mImage = itemView.findViewById(R.id.img_product);
        }
    }

    public void setItems(List<Product> items){
        mProduct = items;
        notifyDataSetChanged();
    }

    public void addItems(List<Product> items){
        mProduct.addAll(items);
        notifyDataSetChanged();
    }
}
