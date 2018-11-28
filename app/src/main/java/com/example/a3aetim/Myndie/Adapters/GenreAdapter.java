package com.example.a3aetim.Myndie.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a3aetim.Myndie.Classes.Genre;
import com.example.a3aetim.Myndie.R;

import java.util.ArrayList;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.GenreViewHolder>{
    private ArrayList<Genre> mGenreList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }
    public void setOnitemClickListener(GenreAdapter.OnItemClickListener listener){
        mListener = listener;
    }

    public static class GenreViewHolder extends RecyclerView.ViewHolder{
        public TextView mTitle;

        public GenreViewHolder(final View itemView, final GenreAdapter.OnItemClickListener listener){
            super(itemView);
            mTitle = itemView.findViewById(R.id.titleGenreMarket);
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
    public GenreAdapter(ArrayList<Genre> genreList){
        mGenreList = genreList;
    }
    @Override
    public GenreAdapter.GenreViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.genre_item,viewGroup,false);
        GenreAdapter.GenreViewHolder gvh = new GenreAdapter.GenreViewHolder(view,mListener);
        return gvh;
    }

    @Override
    public void onBindViewHolder(@NonNull GenreAdapter.GenreViewHolder GenreViewHolder, int i) {
        Genre currentGenre = mGenreList.get(i);
        GenreViewHolder.mTitle.setText(currentGenre.getName().toUpperCase());
    }

    @Override
    public int getItemCount() {
        return mGenreList.size();
    }

}


