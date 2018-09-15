package com.aiyamamoto.transforemerapp;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.aiyamamoto.transforemerapp.databinding.ItemTransformerBinding;
import com.aiyamamoto.transforemerapp.model.Transformer;
import com.aiyamamoto.transforemerapp.model.TransformersList;
import com.aiyamamoto.transforemerapp.network.response.TransformerResponse;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by aiyamamoto on 2018-09-13.
 */

public class TransformersListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<TransformerResponse> mTransforemerList = new ArrayList<>();
    Activity mActivity;

    private AdapterCallback mAdapterCallback;

    public interface AdapterCallback {
        void deleteTransformer(String transformerId, String name);
        void editTransformer(TransformerResponse transformer);
    }

    public TransformersListAdapter(Activity activity, AdapterCallback adapterCallback, TransformersList mTransforemerList) {
        mActivity = activity;
        this.mTransforemerList = mTransforemerList.getTransformers();
        mAdapterCallback = adapterCallback;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ItemTransformersListHolder(DataBindingUtil.inflate(inflater, R.layout.item_transformer, parent, false).getRoot());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TransformerResponse mTransformerResponse = (TransformerResponse) mTransforemerList.get(position);
        Transformer mTransformer = new Transformer(
                mTransformerResponse.getId(),
                mTransformerResponse.getName(),
                String.valueOf(mTransformerResponse.getStrength()),
                String.valueOf(mTransformerResponse.getIntelligence()),
                String.valueOf(mTransformerResponse.getSpeed()),
                String.valueOf(mTransformerResponse.getEndurance()),
                String.valueOf(mTransformerResponse.getRank()),
                String.valueOf(mTransformerResponse.getCourage()),
                String.valueOf(mTransformerResponse.getFirepower()),
                String.valueOf(mTransformerResponse.getSkill()),
                mTransformerResponse.getTeam(),
                mTransformerResponse.getTeam_icon());

        mTransformer.setOverAllRating(String.valueOf(calOverAllRating(mTransformerResponse)));
        ItemTransformersListHolder itemTransformersListHolder = (ItemTransformersListHolder) holder;
        Picasso.get().load(mTransformer.getTeam_icon()).into(itemTransformersListHolder.binding.teamIconImageview);
        itemTransformersListHolder.binding.setItem(mTransformer);
        itemTransformersListHolder.binding.setValue(mTransformerResponse);
        // seekbars to be enable
        itemTransformersListHolder.binding.seekBarStrength.setEnabled(false);
        itemTransformersListHolder.binding.seekBarIntelligence.setEnabled(false);
        itemTransformersListHolder.binding.seekBarSpeed.setEnabled(false);
        itemTransformersListHolder.binding.seekBarEndurance.setEnabled(false);
        itemTransformersListHolder.binding.seekBarRank.setEnabled(false);
        itemTransformersListHolder.binding.seekBarCourage.setEnabled(false);
        itemTransformersListHolder.binding.seekBarFirepower.setEnabled(false);
        itemTransformersListHolder.binding.seekBarSkill.setEnabled(false);
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
                        mAdapterCallback.editTransformer(mTransforemerList.get(getAdapterPosition()));
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
                        mAdapterCallback.deleteTransformer(mTransforemerList.get(getAdapterPosition()).getId()
                                , mTransforemerList.get(getAdapterPosition()).getName());
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
        }
    }

    private int calOverAllRating(TransformerResponse tf) {
        return tf.getStrength() + tf.getIntelligence() + tf.getSpeed() + tf.getEndurance() + tf.getFirepower();
    };


}
