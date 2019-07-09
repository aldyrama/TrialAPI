package com.example.trial01.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.trial01.R;
import com.example.trial01.model.User;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewUserAdapter extends RecyclerView.Adapter<RecyclerViewUserAdapter.MyViewHolder> {

    private Context mContext;
    private List<User> mUser;

    public RecyclerViewUserAdapter(Context listUser, List<User> mUser) {

        this.mContext = listUser;
        this.mUser = mUser;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_model_user, parent, false), i);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        User currentUser = mUser.get(position);

        holder.mId.setText(currentUser.getId());
        holder.mNama.setText(currentUser.getUsername());
//        Picasso.with(mContext)
//                .load(currentUser.getImg())
//                .placeholder(R.drawable.ic_user)
//                .fit()
//                .centerCrop()
//                .into(holder.mImage);

    }

    @Override
    public int getItemCount() {
        return mUser.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView mImage;
        private TextView mId, mNama;

        public MyViewHolder(@NonNull View itemView, int i) {
            super(itemView);

            mId = itemView.findViewById(R.id.id);
            mNama = itemView.findViewById(R.id.name);
            mImage = itemView.findViewById(R.id.imageMember);

        }

    }
}
