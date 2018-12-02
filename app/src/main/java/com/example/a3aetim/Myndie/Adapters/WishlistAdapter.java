package com.example.a3aetim.Myndie.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a3aetim.Myndie.Classes.Application;
import com.example.a3aetim.Myndie.Classes.Genre;
import com.example.a3aetim.Myndie.Images.DownloadImage;
import com.example.a3aetim.Myndie.R;

import java.util.ArrayList;
import java.util.List;

public class WishlistAdapter  extends RecyclerView.Adapter<WishlistAdapter.WishlistViewHolder> {

    private ArrayList<Application> mAppList;
    private WishlistAdapter.OnItemClickListener mListener;


    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public void setOnitemClickListener(WishlistAdapter.OnItemClickListener listener){
        mListener = listener;
    }

    public static class WishlistViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView mTitle,mDesc;

        public WishlistViewHolder(final View itemView, final WishlistAdapter.OnItemClickListener listener){
            super(itemView);
            mImageView = itemView.findViewById(R.id.imgvAppItemWish);
            mTitle = itemView.findViewById(R.id.txtTitleAppWish);
            mDesc = itemView.findViewById(R.id.txtAppDescWish);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int pos =getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            listener.onItemClick(itemView,pos);
                        }
                    }
                }
            });
        }
    }

    public WishlistAdapter(ArrayList<Application> appList){
        mAppList = appList;
    }


    @Override
    public WishlistAdapter.WishlistViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.wishlist_item,viewGroup,false);
        WishlistAdapter.WishlistViewHolder wvh = new WishlistAdapter.WishlistViewHolder(view,mListener);
        return wvh;
    }

    @Override
    public void onBindViewHolder(@NonNull WishlistAdapter.WishlistViewHolder applicationViewHolder, int i) {

        Application currentApp = mAppList.get(i);
        new DownloadImage(applicationViewHolder.mImageView).execute(currentApp.getImageUrl());
        if(currentApp.getTitle().length()>40){
            applicationViewHolder.mTitle.setText(currentApp.getTitle().substring(0,40)+"...");
        }else{
            applicationViewHolder.mTitle.setText(currentApp.getTitle());
        }
        applicationViewHolder.mDesc.setText("US$ "+ String.valueOf(currentApp.getPrice()));
    }

    @Override
    public int getItemCount() {
        return mAppList.size();
    }
}
