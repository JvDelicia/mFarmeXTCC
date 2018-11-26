package com.example.a3aetim.Myndie.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a3aetim.Myndie.R;

import java.util.ArrayList;

public class BackAppAdapter extends RecyclerView.Adapter<BackAppAdapter.ApplicationViewHolder>{

    private ArrayList mBack;
    private OnItemClickListener mListener;
    private RecyclerView.LayoutManager mLayoutManager;
    private ApplicationAdapter mAppAdapter;


    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }
    public void setOnitemClickListener(BackAppAdapter.OnItemClickListener listener){
        mListener = listener;
    }

    public static class ApplicationViewHolder extends RecyclerView.ViewHolder{
        public TextView mTitle;
        public RecyclerView mRecyclerView;

        public ApplicationViewHolder(final View itemView, final BackAppAdapter.OnItemClickListener listener){
            super(itemView);
            mTitle = itemView.findViewById(R.id.txtTituloQuadro);
            mRecyclerView = itemView.findViewById(R.id.RecyclerViewBase);
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
    public BackAppAdapter(ArrayList backlist, ApplicationAdapter applicationAdapter, RecyclerView.LayoutManager LayoutManager){
        mBack = backlist;
        mAppAdapter = applicationAdapter;
        mLayoutManager = LayoutManager;
    }
    @Override
    public BackAppAdapter.ApplicationViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.backapp,viewGroup,false);
        BackAppAdapter.ApplicationViewHolder avh = new BackAppAdapter.ApplicationViewHolder(view,mListener);
        return avh;
    }

    @Override
    public void onBindViewHolder(@NonNull ApplicationViewHolder applicationViewHolder, int i) {
        String title = (String) mBack.get(i);
        applicationViewHolder.mTitle.setText(title);
        applicationViewHolder.mRecyclerView.setAdapter(mAppAdapter);
        applicationViewHolder.mRecyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    public int getItemCount() {
        return mBack.size();
    }
    public void setAppFilter(CharSequence filter){
        mAppAdapter.getFilter().filter(filter);
    }
}