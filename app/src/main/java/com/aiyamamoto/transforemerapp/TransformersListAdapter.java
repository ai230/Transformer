package com.aiyamamoto.transforemerapp;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aiyamamoto.transforemerapp.databinding.ItemTransformerBinding;
import com.aiyamamoto.transforemerapp.model.TransformersList;
import com.aiyamamoto.transforemerapp.network.response.TransformerResponse;

import java.util.ArrayList;

/**
 * Created by aiyamamoto on 2018-09-13.
 */

public class TransformersListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<TransformerResponse> mTransforemerList = new ArrayList<>();
    Activity activity;

    public TransformersListAdapter(Activity mActivity, TransformersList mTransforemerList) {
        activity = mActivity;
        this.mTransforemerList = mTransforemerList.getTransformers();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ItemTransformersListHolder(DataBindingUtil.inflate(inflater, R.layout.item_transformer, parent, false).getRoot());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TransformerResponse mTransformer = (TransformerResponse) mTransforemerList.get(position);
        ItemTransformersListHolder itemTransformersListHolder = (ItemTransformersListHolder) holder;
        itemTransformersListHolder.binding.setItem(mTransformer);
    }

    @Override
    public int getItemCount() {
        return mTransforemerList != null ? mTransforemerList.size() : 0;
    }

    private class ItemTransformersListHolder extends RecyclerView.ViewHolder {
        ItemTransformerBinding binding;

        private View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.card_layout:

                        break;

                    default:
                        break;
                }
            }
        };

        View.OnLongClickListener mOnLongClickListener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                switch (view.getId()) {
                    case R.id.card_layout:
                        break;

                    default:
                        break;
                }
                return true;
            }
        };

        public ItemTransformersListHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            binding.cardLayout.setOnClickListener(mOnClickListener);
            binding.cardLayout.setOnLongClickListener(mOnLongClickListener);
//            binding.button.setOnClickListener(mOnClickListener);
//            binding.name.setText();
        }
    }
}
