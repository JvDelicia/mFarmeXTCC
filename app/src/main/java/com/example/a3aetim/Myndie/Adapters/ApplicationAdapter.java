package com.example.a3aetim.Myndie.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a3aetim.Myndie.Classes.Application;
import com.example.a3aetim.Myndie.Images.DownloadImage;
import com.example.a3aetim.Myndie.R;

import java.util.ArrayList;
import java.util.List;


public class ApplicationAdapter extends RecyclerView.Adapter<ApplicationAdapter.ApplicationViewHolder> implements Filterable{
    private ArrayList<Application> mAppList;
    private ArrayList<Application> mFullAppList;
    private OnItemClickListener mListener;


    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }
    public void setOnitemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public static class ApplicationViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView mTitle,mDesc;

        public ApplicationViewHolder(final View itemView, final OnItemClickListener listener){
            super(itemView);
            mImageView = itemView.findViewById(R.id.imgvAppItemMarket);
            mTitle = itemView.findViewById(R.id.txtTitleAppMarket);
            mDesc = itemView.findViewById(R.id.txtAppDescMarket);
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
    public ApplicationAdapter(ArrayList<Application> appList){
        mAppList = appList;
        mFullAppList = new ArrayList<>(appList);
    }
    @Override
    public ApplicationViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.app_item,viewGroup,false);
        ApplicationViewHolder avh = new ApplicationViewHolder(view,mListener);
        return avh;
    }

    @Override
    public void onBindViewHolder(@NonNull ApplicationViewHolder applicationViewHolder, int i) {

        Application currentApp = mAppList.get(i);
        new DownloadImage(applicationViewHolder.mImageView).execute(currentApp.getImageUrl());
        applicationViewHolder.mTitle.setText(currentApp.getTitle());
        applicationViewHolder.mDesc.setText("US$ "+ String.valueOf(currentApp.getPrice()));
    }

    @Override
    public int getItemCount() {
        return mAppList.size();
    }

    @Override
    public Filter getFilter() {
        return applicationFilter;
    }
    private Filter applicationFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Application> filteredApps = new ArrayList<>();
            if(constraint == null || constraint.equals("") || constraint.length() == 0){
                filteredApps.addAll(mFullAppList);
            }
            else{
                String filtro = constraint.toString().toLowerCase().trim();
                for(Application app : mFullAppList){
                    if(app.getTitle().toLowerCase().contains(filtro)){
                        filteredApps.add(app);
                    }
                }
            }
            FilterResults filterR = new FilterResults();
            filterR.values = filteredApps;
            return filterR;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mAppList.clear();
            mAppList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
//https://medium.com/@crossphd/android-image-loading-from-a-string-url-6c8290b82c5e