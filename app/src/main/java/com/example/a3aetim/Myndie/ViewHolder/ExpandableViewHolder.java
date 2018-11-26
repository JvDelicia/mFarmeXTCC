package com.example.a3aetim.Myndie.ViewHolder;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a3aetim.Myndie.Classes.Application;
import com.example.a3aetim.Myndie.R;
import com.sysdata.widget.accordion.ExpandableItemHolder;
import com.sysdata.widget.accordion.ItemAdapter;

public class ExpandableViewHolder extends com.sysdata.widget.accordion.ExpandedViewHolder {
    private TextView mTitleTextView;
    private TextView mDescriptionTextView;

    private ExpandableViewHolder(View itemView) {
        super(itemView);

        mTitleTextView = (TextView) itemView.findViewById(R.id.layout_expanded_title);
        mDescriptionTextView = (TextView) itemView.findViewById(R.id.layout_expanded_description);
    }

    @Override
    protected void onBindItemView(ExpandableItemHolder expandableItemHolder) {
        mTitleTextView.setText(R.string.app_Desc);
        mDescriptionTextView.setText(((Application) expandableItemHolder.item).getDescription());
    }

    @Override
    protected void onRecycleItemView() {
        // do nothing
    }

    @Override
    protected ItemAdapter.ItemViewHolder.Factory getViewHolderFactory() {
        return null;
    }

    public static class Factory implements ItemAdapter.ItemViewHolder.Factory {

        public static Factory create(@LayoutRes int itemViewLayoutId) {
            return new Factory(itemViewLayoutId);
        }

        @LayoutRes
        private final int mItemViewLayoutId;

        public Factory(@LayoutRes int itemViewLayoutId) {
            mItemViewLayoutId = itemViewLayoutId;
        }

        @Override
        public ItemAdapter.ItemViewHolder<?> createViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false /* attachToRoot */);
            return new ExpandableViewHolder(itemView);
        }
        @Override
        public int getItemViewLayoutId() {
            return mItemViewLayoutId;
        }
    }
}
