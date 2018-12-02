package com.example.a3aetim.Myndie.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.a3aetim.Myndie.Classes.Application;
import com.example.a3aetim.Myndie.Classes.Genre;

import java.util.ArrayList;

public class WishlistAdapter {

    private ArrayList<Application> mApplicationList;
    private WishlistAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }
    public void setOnitemClickListener(WishlistAdapter.OnItemClickListener listener){
        mListener = listener;
    }

    public static class WishlistViewHolder extends RecyclerView.ViewHolder{

        public WishlistViewHolder(final View itemView, final WishlistAdapter.OnItemClickListener listener) {
            super(itemView);
        }
    }
}
