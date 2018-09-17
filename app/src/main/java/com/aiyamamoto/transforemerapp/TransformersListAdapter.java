package com.aiyamamoto.transforemerapp;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aiyamamoto.transforemerapp.databinding.ItemTransformerBinding;
import com.aiyamamoto.transforemerapp.model.Transformer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by aiyamamoto on 2018-09-13.
 */

public class TransformersListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Transformer> mTransforemerList = new ArrayList<>();
    Activity mActivity;

    private AdapterCallback mAdapterCallback;

    public interface AdapterCallback {
        void deleteTransformer(String transformerId, String name);
        void editTransformer(Transformer transformer);
    }

    public TransformersListAdapter(Activity activity, AdapterCallback adapterCallback, ArrayList<Transformer> mTransforemerList) {
        mActivity = activity;
        this.mTransforemerList = mTransforemerList;
        mAdapterCallback = adapterCallback;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ItemTransformersListHolder(DataBindingUtil.inflate(inflater, R.layout.item_transformer, parent, false).getRoot());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Transformer mTransformer = (Transformer) mTransforemerList.get(position);

        mTransformer.setOverallRating(calOverallRating(mTransformer));
        ItemTransformersListHolder itemTransformersListHolder = (ItemTransformersListHolder) holder;
        Picasso.get().load(mTransformer.getTeam_icon()).into(itemTransformersListHolder.binding.teamIconImageview);
        itemTransformersListHolder.binding.setItem(mTransformer);

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

    private int calOverallRating(Transformer tf) {
        return tf.getStrength() + tf.getIntelligence() + tf.getSpeed() + tf.getEndurance() + tf.getFirepower();
    }


}
