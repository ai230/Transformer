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
import com.aiyamamoto.transforemerapp.model.Result;
import com.aiyamamoto.transforemerapp.model.Transformer;

import java.util.ArrayList;

public class BattleListFragment extends BaseFragment implements BattleListAdapter.AdapterCallback{

    private FragmentBattleListBinding mBinding;

    private ArrayList<Transformer> autobots;
    private ArrayList<Transformer> decepticons;

    private BattleListAdapter mBattleListAdapter;

    public OnFragmentInteractionListener mListener;

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
        initRecyclerview();
        return mBinding.getRoot();
    }

    private void setListeners() {
        mBinding.seeResultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.navigateToResult(findResult());
                }
            }
        });
    }

    private void initRecyclerview() {
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.recyclerView.setHasFixedSize(true);
        mBattleListAdapter = new BattleListAdapter(getActivity(), autobots, decepticons, this);
        mBinding.recyclerView.setAdapter(mBattleListAdapter);
    }

    // if team players are not enough create fake one
    private void findItemSize() {
        int itemSize = autobots.size() > decepticons.size() ? autobots.size() : decepticons.size();

        while (autobots.size() < itemSize) {
            Transformer t = new Transformer("0","", 0,0,0,0,0,0,0,0, "", "");
            autobots.add(t);
        }
        while (decepticons.size() < itemSize) {
            Transformer t = new Transformer("0","", 0,0,0,0,0,0,0,0, "", "");
            decepticons.add(t);
        }
    }

    private Result findResult() {
        int numObBattle = autobots.size() < decepticons.size() ? autobots.size() : decepticons.size();
        String winningTeam = "";
        String winningTransformer = "";
        String losingTeam = "";
        ArrayList<String> survivors = new ArrayList<>();



        Result result = new Result(numObBattle, winningTeam, winningTransformer,losingTeam, survivors);
        return result;
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

    // AdapterCallback
    @Override
    public void passingdata() {

    }

    public interface OnFragmentInteractionListener {
        void navigateToResult(Result result);
    }
}
