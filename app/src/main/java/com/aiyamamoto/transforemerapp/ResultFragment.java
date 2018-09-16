package com.aiyamamoto.transforemerapp;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aiyamamoto.transforemerapp.databinding.FragmentResultBinding;
import com.aiyamamoto.transforemerapp.model.Result;


/**
 * A simple {@link Fragment} subclass.
 * {@link ResultFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResultFragment extends Fragment {

    private static final String RESULT_KEY= "result_key";

    private FragmentResultBinding mBinding;

    private Result mResult;
    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param result Result.
     * @return A new instance of fragment ResultFragment.
     */
    public static ResultFragment newInstance(Result result) {
        ResultFragment fragment = new ResultFragment();
        Bundle args = new Bundle();
        args.putSerializable(RESULT_KEY, result);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_result, container, false);
        if (getArguments() != null) {
            mResult = (Result) getArguments().getSerializable(RESULT_KEY);
        }
        checkKingsFaced();
        setListeners();
        return mBinding.getRoot();
    }

    private void checkKingsFaced() {
        if (mResult.isOptimusAndPredakingFaced()) {
            mBinding.resultLayout.setVisibility(View.GONE);
            mBinding.message.setVisibility(View.VISIBLE);
            mBinding.message.setText("Optimus Prime and Predaking face each other (or a duplicate of each other), the game" +
                    " immediately ends with all competitors destroyed");
        } else {
            mBinding.resultLayout.setVisibility(View.VISIBLE);
            mBinding.message.setVisibility(View.GONE);
            initData();
        }
    }

    private void initData() {
        if (mResult != null) {
            mBinding.setItem(mResult);
        }
        StringBuilder survivors = new StringBuilder();
        int count = 0;
        if (mResult != null) {
            if(mResult.getSurvivors().size() == 0) {
                mBinding.losing .setVisibility(View.INVISIBLE);
                mBinding.losingSurvivors.setVisibility(View.INVISIBLE);
            } else {
                for (String survivor : mResult.getSurvivors()){
                    if (0 < count) {
                        survivors.append(", ");
                    }
                    survivors.append(survivor);
                    count++;
                }
                mBinding.losing .setVisibility(View.VISIBLE);
                mBinding.losingSurvivors.setVisibility(View.VISIBLE);
                mBinding.losingSurvivors.setText(survivors);
            }

        }

    }

    private void setListeners() {
        mBinding.backToList.setOnClickListener(mOnClickListener);
        mBinding.backToPre.setOnClickListener(mOnClickListener);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.backToList:
                    if (mListener != null) {
                        mListener.backToTransformerListFragment();
                    }
                    break;

                case R.id.backToPre:
                    if (mListener != null) {
                        mListener.backToBattleListFragment();
                    }
                    break;
            }
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * to allow an interaction in this fragment to be communicated
     * to MainActivity
     */
    public interface OnFragmentInteractionListener {
        void backToTransformerListFragment();
        void backToBattleListFragment();
    }
}
