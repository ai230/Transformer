package com.aiyamamoto.transforemerapp;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aiyamamoto.transforemerapp.databinding.ItemBattleBinding;
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
    private final String SURVIVOR = "SURVIVOR";

    private ArrayList<Transformer> mAutobots = new ArrayList<>();
    private ArrayList<Transformer> mDecepticons = new ArrayList<>();
    private Activity mActivity;

    private AdapterCallback mAdapterCallback;

    public BattleListAdapter(Activity activity, ArrayList<Transformer> autobots, ArrayList<Transformer> decepticons, AdapterCallback callback) {
        this.mAutobots = autobots;
        this.mDecepticons = decepticons;
        this.mActivity = activity;
        mAdapterCallback = callback;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ItemBattleListHolder(DataBindingUtil.inflate(inflater, R.layout.item_battle, parent,false).getRoot());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemBattleListHolder itemBattleListHolder = (ItemBattleListHolder) holder;

        itemBattleListHolder.binding.setAutobot(mAutobots.get(position));
        itemBattleListHolder.binding.setDecepticon(mDecepticons.get(position));

        if(mAutobots.get(position).getTeam_icon().isEmpty()) {
            itemBattleListHolder.binding.autobotsTeamIcon.setImageDrawable(mActivity.getResources().getDrawable(R.drawable.ic_block));
        } else {
            Picasso.get().load(mAutobots.get(position).getTeam_icon()).into(itemBattleListHolder.binding.autobotsTeamIcon);
        }

        if(mDecepticons.get(position).getTeam_icon().isEmpty()) {
            itemBattleListHolder.binding.decepticonTeamIcon.setImageDrawable(mActivity.getResources().getDrawable(R.drawable.ic_block));
        } else {
            Picasso.get().load(mDecepticons.get(position).getTeam_icon()).into(itemBattleListHolder.binding.decepticonTeamIcon);
        }

        if (mAutobots.get(position).getId().equals("0") || mDecepticons.get(position).getId().equals("0")) {
            if (mAutobots.get(position).getId().equals("0")) {
                itemBattleListHolder.binding.autobotsSkillsLayout.setVisibility(View.INVISIBLE);
                mDecepticons.get(position).setResult(SURVIVOR);
            }
            if (mDecepticons.get(position).getId().equals("0")) {
                itemBattleListHolder.binding.decepticonSkillsLayout.setVisibility(View.INVISIBLE);
                mAutobots.get(position).setResult(SURVIVOR);
            }

        } else {
            if (battleName(position)) {
                // do something
            } else if (battleCourage(position)) {
                setTextViewBg(itemBattleListHolder.binding.autobotsCourage, mAutobots.get(position).getResult(), position);
                setTextViewBg(itemBattleListHolder.binding.decepticonCourage, mDecepticons.get(position).getResult(), position);
            } else if (battleStrength(position)){
                setTextViewBg(itemBattleListHolder.binding.autobotsStrength, mAutobots.get(position).getResult(), position);
                setTextViewBg(itemBattleListHolder.binding.decepticonStrength, mDecepticons.get(position).getResult(), position);
            } else if (battleSkill(position)) {
                setTextViewBg(itemBattleListHolder.binding.autobotsSkill,mAutobots.get(position).getResult(), position);
                setTextViewBg(itemBattleListHolder.binding.decepticonSkill, mDecepticons.get(position).getResult(), position);
            } else if (battleOverallRating(position)) {
                setTextViewBg(itemBattleListHolder.binding.autobotsOverall,mAutobots.get(position).getResult(), position);
                setTextViewBg(itemBattleListHolder.binding.decepticonOverall, mDecepticons.get(position).getResult(), position);
            }
        }
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

    /** Special rules:
     * 1. Any Transformer named Optimus Prime or Predaking wins his fight automatically
     * regardless ofany other criteria
     *
     * 2. In the event either of the above face each other (or a duplicate of each other), the game
     * immediately ends with all competitors destroyed
     * @param position
     * @return Battle ends return true, Otherwise return false
     */
    private boolean battleName(int position) {
        String autobotName = mAutobots.get(position).getName();
        String decepticonName = mDecepticons.get(position).getName();

        // at first check their name are not special name
        if (!autobotName.equals(R.string.Optimus_Prime) && !decepticonName.equals(R.string.Predaking)) {
            return false;
        } else {
            if (autobotName.equals(R.string.Optimus_Prime) && decepticonName.equals(R.string.Predaking)) {
                // Special rule2
                return true;
            } else {
                // Special rule1
                if (autobotName.equals(R.string.Optimus_Prime)) {
                    mAutobots.get(position).setResult(WIN);
                    mDecepticons.get(position).setResult(LOSE);
                    return true;
                } else if (decepticonName.equals(R.string.Predaking)) {
                    mAutobots.get(position).setResult(LOSE);
                    mDecepticons.get(position).setResult(WIN);
                    return true;
                } else {
                    //
                    return false;
                }
            }
        }
    }

    // TODO this function beed to be changed
    /** Battle rule 1:
     * If any fighter is down 4 or more points of courage and 3 or more points of strength
     * compared to their opponent, the opponent automatically wins the face-off regardless of
     * overall rating (opponent has ran away)
     * @param position
     * @return Battle ends return true, Otherwise return false
     */
    private boolean battleCourage(int position) {
        // chaeck Courage
        int courageResult = mAutobots.get(position).getCourage() - mDecepticons.get(position).getCourage();
        if (courageResult >= 4) {
            // autobots win deception lose
            mAutobots.get(position).setResult(WIN);
            mDecepticons.get(position).setResult(LOSE);
            return true;
        } else if (courageResult <= -4) {
            //deception lose deception win
            mAutobots.get(position).setResult(LOSE);
            mDecepticons.get(position).setResult(WIN);
            return true;
        } else {
            // go next
            return false;
        }
    }

    /** Battle rule 1:
     * If any fighter is down 4 or more points of courage and 3 or more points of strength
     * compared to their opponent, the opponent automatically wins the face-off regardless of
     * overall rating (opponent has ran away)
     * @param position
     * @return Battle ends return true, Otherwise return false
     */
    private boolean battleStrength(int position) {
        // chaeck Strength
        int strengthResult = mAutobots.get(position).getStrength() - mDecepticons.get(position).getStrength();
        if (strengthResult >= 3) {
            // autobots win deception lose
            mAutobots.get(position).setResult(WIN);
            mDecepticons.get(position).setResult(LOSE);
            return true;
        } else if (strengthResult <= -3) {
            //deception lose deception win
            mAutobots.get(position).setResult(LOSE);
            mDecepticons.get(position).setResult(WIN);
            return true;
        } else {
            // go next
            return false;
        }
    }

    /** Battle rule 2:
     * if one of the fighters is 3 or more points of skill above their opponent,
     * they winthe fight regardless of overall rating
     * @param position
     * @return Battle ends return true, Otherwise return false
     */
    private boolean battleSkill(int position) {
        // chaeck Skill
        int skillResult = mAutobots.get(position).getSkill() - mDecepticons.get(position).getSkill();
        if (skillResult >= 3) {
            // autobots win deception lose
            mAutobots.get(position).setResult(WIN);
            mDecepticons.get(position).setResult(LOSE);
            return true;
        } else if (skillResult <= -3) {
            //deception lose deception win
            mAutobots.get(position).setResult(LOSE);
            mDecepticons.get(position).setResult(WIN);
            return true;
        } else {
            // go next
            return false;
        }
    }

    /** Battle rule 3:
     * the Transformer with the highest overall rating
     * @param position
     * @return Battle ends return true, Otherwise return false
     */
    private boolean battleOverallRating(int position) {
        // chaeck Overall rating
        if (mAutobots.get(position).getOverallRating() > mDecepticons.get(position).getOverallRating()) {
            // autobots win deception lose
            mAutobots.get(position).setResult(WIN);
            mDecepticons.get(position).setResult(LOSE);
        } else if (mAutobots.get(position).getOverallRating() < mDecepticons.get(position).getOverallRating()) {
            //deception lose deception win
            mAutobots.get(position).setResult(LOSE);
            mDecepticons.get(position).setResult(WIN);
        } else {
            // tie
            mAutobots.get(position).setResult(TIE);
            mDecepticons.get(position).setResult(TIE);
        }
        return true;
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

    public interface AdapterCallback {
        void passingdata();
    }
}
