package com.aiyamamoto.transforemerapp;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aiyamamoto.transforemerapp.databinding.ItemBattleBinding;
import com.aiyamamoto.transforemerapp.model.Result;
import com.aiyamamoto.transforemerapp.model.Transformer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by aiyamamoto on 2018-09-15.
 */

public class BattleListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final String WIN = "WIN";
    private final String LOSE = "LOSE";
    private final String TIE = "TIE";

    private ArrayList<Transformer> mAutobots = new ArrayList<>();
    private ArrayList<Transformer> mDecepticons = new ArrayList<>();
    private Activity mActivity;

    public BattleListAdapter(Activity activity, ArrayList<Transformer> autobots, ArrayList<Transformer> decepticons) {
        this.mAutobots = autobots;
        this.mDecepticons = decepticons;
        this.mActivity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ItemBattleListHolder(DataBindingUtil.inflate(inflater, R.layout.item_battle, parent,false).getRoot());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemBattleListHolder itemBattleListHolder = (ItemBattleListHolder) holder;

//        itemBattleListHolder.binding.setAutobot(mAutobots.get(position));
//        itemBattleListHolder.binding.setDecepticon(mDecepticons.get(position));

        // set team icons
        if(mAutobots.get(position).getTeam_icon().isEmpty()) {
            itemBattleListHolder.binding.autobotsTeamIcon.setImageDrawable(mActivity.getResources().getDrawable(R.drawable.ic_block));
        } else {
            Picasso.get().load(mAutobots.get(position).getTeam_icon()).into(itemBattleListHolder.binding.autobotsTeamIcon);
        }

        // set team icons
        if(mDecepticons.get(position).getTeam_icon().isEmpty()) {
            itemBattleListHolder.binding.decepticonTeamIcon.setImageDrawable(mActivity.getResources().getDrawable(R.drawable.ic_block));
        } else {
            Picasso.get().load(mDecepticons.get(position).getTeam_icon()).into(itemBattleListHolder.binding.decepticonTeamIcon);
        }

        // set invisible fot fake transformer
        if (mAutobots.get(position).getId().equals("0")) {
            itemBattleListHolder.binding.autobotsSkillsLayout.setVisibility(View.INVISIBLE);
        } else if (mDecepticons.get(position).getId().equals("0")){
            itemBattleListHolder.binding.decepticonSkillsLayout.setVisibility(View.INVISIBLE);
        }

        if (mAutobots.get(position).getBattleResult().isNameWin()) {
            setTextViewBg(itemBattleListHolder.binding.autobotsName, mAutobots.get(position).getResult(), position);
            setTextViewBg(itemBattleListHolder.binding.decepticonName, mDecepticons.get(position).getResult(), position);
        }else if (mAutobots.get(position).getBattleResult().isCourageAndStrengthWin()) {
            setTextViewBg(itemBattleListHolder.binding.autobotsCourage, mAutobots.get(position).getResult(), position);
            setTextViewBg(itemBattleListHolder.binding.autobotsStrength, mAutobots.get(position).getResult(), position);
            setTextViewBg(itemBattleListHolder.binding.decepticonCourage, mDecepticons.get(position).getResult(), position);
            setTextViewBg(itemBattleListHolder.binding.decepticonStrength, mDecepticons.get(position).getResult(), position);
        } else if (mAutobots.get(position).getBattleResult().isSkillWin()) {
            setTextViewBg(itemBattleListHolder.binding.autobotsSkill,mAutobots.get(position).getResult(), position);
            setTextViewBg(itemBattleListHolder.binding.decepticonSkill,mDecepticons.get(position).getResult(), position);
        } else if (mAutobots.get(position).getBattleResult().isOverallRatingWin()) {
            setTextViewBg(itemBattleListHolder.binding.autobotsOverall,mAutobots.get(position).getResult(), position);
            setTextViewBg(itemBattleListHolder.binding.decepticonOverall,mDecepticons.get(position).getResult(), position);
        }

        if (mDecepticons.get(position).getBattleResult().isNameWin()) {
            setTextViewBg(itemBattleListHolder.binding.decepticonName, mDecepticons.get(position).getResult(), position);
            setTextViewBg(itemBattleListHolder.binding.autobotsName, mAutobots.get(position).getResult(), position);
        } else if (mDecepticons.get(position).getBattleResult().isCourageAndStrengthWin()) {
            setTextViewBg(itemBattleListHolder.binding.decepticonCourage, mDecepticons.get(position).getResult(), position);
            setTextViewBg(itemBattleListHolder.binding.decepticonStrength, mDecepticons.get(position).getResult(), position);
            setTextViewBg(itemBattleListHolder.binding.autobotsCourage, mAutobots.get(position).getResult(), position);
            setTextViewBg(itemBattleListHolder.binding.autobotsStrength, mAutobots.get(position).getResult(), position);
        } else if (mDecepticons.get(position).getBattleResult().isSkillWin()) {
            setTextViewBg(itemBattleListHolder.binding.decepticonSkill, mDecepticons.get(position).getResult(), position);
            setTextViewBg(itemBattleListHolder.binding.autobotsSkill,mAutobots.get(position).getResult(), position);
        } else if (mDecepticons.get(position).getBattleResult().isOverallRatingWin()) {
            setTextViewBg(itemBattleListHolder.binding.decepticonOverall, mDecepticons.get(position).getResult(), position);
            setTextViewBg(itemBattleListHolder.binding.autobotsOverall,mAutobots.get(position).getResult(), position);
        }

        itemBattleListHolder.binding.setAutobot(mAutobots.get(position));
        itemBattleListHolder.binding.setDecepticon(mDecepticons.get(position));
    }

    private void setTextViewBg(TextView tv, String result, int position) {
        switch (result) {
            case WIN:
                tv.setBackgroundColor(mActivity.getResources().getColor(R.color.colorPink));
                break;
            case LOSE:
                tv.setBackgroundColor(mActivity.getResources().getColor(R.color.colorGray));
                break;
            case TIE:
                tv.setBackgroundColor(mActivity.getResources().getColor(R.color.colorOrange));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mAutobots.size();
    }

    private class ItemBattleListHolder extends RecyclerView.ViewHolder {
        ItemBattleBinding binding;

        public ItemBattleListHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }

}
