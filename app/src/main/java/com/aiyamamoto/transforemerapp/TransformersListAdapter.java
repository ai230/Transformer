package com.aiyamamoto.transforemerapp;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aiyamamoto.transforemerapp.databinding.ItemTransformerBinding;
import com.aiyamamoto.transforemerapp.model.Transformer;

import java.util.ArrayList;

/**
 * Created by aiyamamoto on 2018-09-13.
 */

public class TransformersListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Transformer> mTransforemerList = new ArrayList<>();

    public TransformersListAdapter(Activity mActivity, ArrayList<Transformer> mTransforemerList) {
        this.mTransforemerList = mTransforemerList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ItemTransformersListHolder(DataBindingUtil.inflate(inflater, R.layout.item_transformer, parent, false).getRoot());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Transformer mTransformer = (Transformer) mTransforemerList.get(position);
        ItemTransformersListHolder itemTransformersListHolder = (ItemTransformersListHolder) holder;
        itemTransformersListHolder.binding.setItem(mTransformer);
    }

    @Override
    public int getItemCount() {
        return mTransforemerList != null ? mTransforemerList.size() : 0;
    }

    private class ItemTransformersListHolder extends RecyclerView.ViewHolder {
        ItemTransformerBinding binding;

        private View.OnClickListener mOnclickListener1 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        };

        public ItemTransformersListHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
//            binding.dataLayout.setOnClickListener(mOnClickListener);
//            binding.button.setOnClickListener(mOnClickListener);
//            binding.name.setText();
        }
    }
}
