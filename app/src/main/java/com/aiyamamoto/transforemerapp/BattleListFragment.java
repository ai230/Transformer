package com.aiyamamoto.transforemerapp;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aiyamamoto.transforemerapp.base.BaseFragment;
import com.aiyamamoto.transforemerapp.databinding.FragmentBattleListBinding;
import com.aiyamamoto.transforemerapp.model.BattleResult;
import com.aiyamamoto.transforemerapp.model.Result;
import com.aiyamamoto.transforemerapp.model.Transformer;

import java.util.ArrayList;

public class BattleListFragment extends BaseFragment {

    private final String WIN = "WIN";
    private final String LOSE = "LOSE";
    private final String TIE = "TIE";
    private final String SURVIVOR = "SURVIVOR";
    private final String FAKE = "FAKE";

    private final String AUTOBOTS = "Autobots";
    private final String DECEPTICONS = "Decepticons";

    private final String OPTIMUS_PRIME = "Optimus Prime";
    private final String PREDAKING = "Predaking";

    private FragmentBattleListBinding mBinding;

    private ArrayList<Transformer> autobots;
    private ArrayList<Transformer> decepticons;

    private BattleListAdapter mBattleListAdapter;

    public OnFragmentInteractionListener mListener;

    private Result mResult = new Result();
    private int numberOfBattleWithSkip;
    private ArrayList<String> survivors = new ArrayList<>();

    public static BattleListFragment newInstance() {
        BattleListFragment fragment = new BattleListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_battle_list, container, false);

        autobots = MainActivity.autobotsList;
        decepticons = MainActivity.decepticonsList;

        setListeners();
        findItemSize();
        battle();
        initRecyclerview();
        return mBinding.getRoot();
    }

    private void setListeners() {
        mBinding.seeResultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    findWinningTeam();
                    mListener.navigateToResult(mResult);
                }
            }
        });
    }

    private void initRecyclerview() {
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.recyclerView.setHasFixedSize(true);
        mBattleListAdapter = new BattleListAdapter(getActivity(), autobots, decepticons);
        mBinding.recyclerView.setAdapter(mBattleListAdapter);
    }

    // if team players are not enough create fake one
    private void findItemSize() {
        numberOfBattleWithSkip = autobots.size() > decepticons.size() ? autobots.size() : decepticons.size();
        mResult.setNumberOfBattle(autobots.size() < decepticons.size() ? autobots.size() : decepticons.size());
        while (autobots.size() < numberOfBattleWithSkip) {
            Transformer t = new Transformer("0","", 0,0,0,0,0,0,0,0, "", "");
            autobots.add(t);
        }
        while (decepticons.size() < numberOfBattleWithSkip) {
            Transformer t = new Transformer("0","", 0,0,0,0,0,0,0,0, "", "");
            decepticons.add(t);
        }
    }


    private void battle() {
        for (int position = 0; position < numberOfBattleWithSkip; position++) {
            BattleResult battleResult = new BattleResult();
            // initialize
            autobots.get(position).setBattleResult(battleResult);
            autobots.get(position).setResult("");
            decepticons.get(position).setBattleResult(battleResult);
            decepticons.get(position).setResult("");

            if (autobots.get(position).getId().equals("0") || decepticons.get(position).getId().equals("0")) {
                // survived
                if (autobots.get(position).getId().equals("0")) {
                    autobots.get(position).setResult(FAKE);
                    decepticons.get(position).setResult(SURVIVOR);
                    survivors.add(decepticons.get(position).getName());
                    mResult.setSurvivors(survivors);
                }
                if (decepticons.get(position).getId().equals("0")) {
                    decepticons.get(position).setResult(FAKE);
                    autobots.get(position).setResult(SURVIVOR);
                    survivors.add(autobots.get(position).getName());
                    mResult.setSurvivors(survivors);
                }
            } else { // battled
                if (battleName(position, battleResult)) {
                } else if (battleCourageAndStrength(position, battleResult)) {
                } else if (battleSkill(position, battleResult)) {
                } else if (battleOverallRating(position, battleResult)) {
                }
            }
        }
        MainActivity.autobotsList = autobots;
        MainActivity.decepticonsList = decepticons;
    }

    /** Special rules:
     * 1. Any Transformer named Optimus Prime or Predaking wins his fight automatically
     * regardless of any other criteria
     *
     * 2. In the event either of the above face each other (or a duplicate of each other), the game
     * immediately ends with all competitors destroyed
     * @param position
     * @return Battle ends return true, Otherwise return false
     */
    private boolean battleName(int position, BattleResult battleResult) {
        String autobotName = autobots.get(position).getName();
        String decepticonName = decepticons.get(position).getName();

        // at first check their name are not special name
        if (!autobotName.equals(OPTIMUS_PRIME) && !decepticonName.equals(PREDAKING)) {
            return false;
        } else {
            if (autobotName.equals(OPTIMUS_PRIME) && decepticonName.equals(PREDAKING)) {
                // Special rule2
                mResult.setMessage(OPTIMUS_PRIME + " and " + PREDAKING + "faced each other, so the game ends");
                return true;
            } else {
                // Special rule1
                if (autobotName.equals(OPTIMUS_PRIME)) {
                    autobots.get(position).setResult(WIN);
                    decepticons.get(position).setResult(LOSE);

                    battleResult.setNameWin(true);
                    autobots.get(position).setBattleResult(battleResult);
                    return true;
                } else if (decepticonName.equals(PREDAKING)) {
                    autobots.get(position).setResult(LOSE);
                    decepticons.get(position).setResult(WIN);

                    battleResult.setNameWin(true);
                    decepticons.get(position).setBattleResult(battleResult);
                    return true;
                } else {
                    //
                    return false;
                }
            }
        }
    }

    /** Battle rule 1:
     * If any fighter is down 4 or more points of courage and 3 or more points of strength
     * compared to their opponent, the opponent automatically wins the face-off regardless of
     * overall rating (opponent has ran away)
     * @param position
     * @return Battle ends return true, Otherwise return false
     */
    private boolean battleCourageAndStrength(int position, BattleResult battleResult) {
        // check Courage
        int courageResult = autobots.get(position).getCourage() - decepticons.get(position).getCourage();
        int strengthResult = autobots.get(position).getStrength() - decepticons.get(position).getStrength();

        // both high
        if (courageResult >= 4 && strengthResult >= 3) {
            // autobots win deception lose
            autobots.get(position).setResult(WIN);
            decepticons.get(position).setResult(LOSE);

            battleResult.setCourageAndStrengthWin(true);
            autobots.get(position).setBattleResult(battleResult);
            return true;
        } else if (courageResult <= -4 && strengthResult <= -3) {
            //deception lose deception win
            autobots.get(position).setResult(LOSE);
            decepticons.get(position).setResult(WIN);

            battleResult.setCourageAndStrengthWin(true);
            decepticons.get(position).setBattleResult(battleResult);
            return true;
        } else {
            // go next
            return false;
        }
    }

    /** Battle rule 2:
     * if one of the fighters is 3 or more points of skill above their opponent,
     * they win the fight regardless of overall rating
     * @param position
     * @return Battle ends return true, Otherwise return false
     */
    private boolean battleSkill(int position, BattleResult battleResult) {
        // check Skill
        int skillResult = autobots.get(position).getSkill() - decepticons.get(position).getSkill();
        if (skillResult >= 3) {
            // autobots win deception lose
            autobots.get(position).setResult(WIN);
            decepticons.get(position).setResult(LOSE);

            battleResult.setSkillWin(true);
            autobots.get(position).setBattleResult(battleResult);
            return true;
        } else if (skillResult <= -3) {
            //deception lose deception win
            autobots.get(position).setResult(LOSE);
            decepticons.get(position).setResult(WIN);

            battleResult.setSkillWin(true);
            decepticons.get(position).setBattleResult(battleResult);
            return true;
        } else {
            // go next
            return false;
        }
    }

    /** Battle rule 3:
     * the Transformer with the highest overall rating
     * @param position battleResult
     * @return Battle ends return true, Otherwise return false
     */
    private boolean battleOverallRating(int position, BattleResult battleResult) {
        // check Overall rating
        if (autobots.get(position).getOverallRating() > decepticons.get(position).getOverallRating()) {
            // autobots win deception lose
            autobots.get(position).setResult(WIN);
            decepticons.get(position).setResult(LOSE);

            battleResult.setOverallRatingWin(true);
            autobots.get(position).setBattleResult(battleResult);
        } else if (autobots.get(position).getOverallRating() < decepticons.get(position).getOverallRating()) {
            //deception lose deception win
            autobots.get(position).setResult(LOSE);
            decepticons.get(position).setResult(WIN);

            battleResult.setOverallRatingWin(true);
            decepticons.get(position).setBattleResult(battleResult);
        } else {
            // tie
            autobots.get(position).setResult(TIE);
            decepticons.get(position).setResult(TIE);
        }
        return true;
    }

    private void findWinningTeam() {
        int numberOfautobotsLosing = 0;
        int numberOfdecepticonsLosing = 0;
        ArrayList<String> autobotsSurvivors = new ArrayList<>();
        ArrayList<String> decepticonsSurvivors = new ArrayList<>();

        for (int i=0; i < numberOfBattleWithSkip; i++) {
            if (autobots.get(i).getResult().equals(LOSE)) {
                numberOfautobotsLosing++;
            }
            if (decepticons.get(i).getResult().equals(LOSE)) {
                numberOfdecepticonsLosing++;
            }
        }
        if(numberOfautobotsLosing < numberOfdecepticonsLosing){
            // autobots win
            int bestOverallRating = 0;
            for (int i=0; i < numberOfBattleWithSkip; i++) {
                // autobots win so find decepticons's survivors
                if (decepticons.get(i).getResult().equals(SURVIVOR)) {
                    decepticonsSurvivors.add(decepticons.get(i).getName());
                }
                if (autobots.get(i).getResult().equals(WIN)) {
                    // check who has highest overall-rating in the winner
                    if (bestOverallRating < autobots.get(i).getOverallRating()) {
                        bestOverallRating = autobots.get(i).getOverallRating();
                        mResult.setWinningTransformer(autobots.get(i).getName());
                    }
                }
            }
            mResult.setWinningTeam(AUTOBOTS);
            mResult.setSurvivors(decepticonsSurvivors);
        } else {
            //decepticons win
            int bestOverallRating = 0;
            for (int i=0; i < numberOfBattleWithSkip; i++) {
                // decepticons win so find autobots's survivors
                if (autobots.get(i).getResult().equals(SURVIVOR)) {
                    autobotsSurvivors.add(autobots.get(i).getName());
                }
                if (decepticons.get(i).getResult().equals(WIN)) {
                    // check who has highest overall-rating in the winner
                    if (bestOverallRating < decepticons.get(i).getOverallRating()) {
                        bestOverallRating = decepticons.get(i).getOverallRating();
                        mResult.setWinningTransformer(decepticons.get(i).getName());
                    }
                }
            }
            mResult.setWinningTeam(DECEPTICONS);
            mResult.setSurvivors(autobotsSurvivors);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void navigateToResult(Result result);
    }

}
