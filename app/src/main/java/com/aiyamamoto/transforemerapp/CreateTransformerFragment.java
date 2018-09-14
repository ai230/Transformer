package com.aiyamamoto.transforemerapp;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aiyamamoto.transforemerapp.base.BaseFragment;
import com.aiyamamoto.transforemerapp.databinding.FragmentCreateTransformerBinding;
import com.aiyamamoto.transforemerapp.network.response.TransformerResponse;
import com.aiyamamoto.transforemerapp.databinding.*;


public class CreateTransformerFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentCreateTransformerBinding mBinding;


    public CreateTransformerFragment() {
        // Required empty public constructor
    }

    public static CreateTransformerFragment newInstance() {
        CreateTransformerFragment fragment = new CreateTransformerFragment();
        return fragment;
    }

    public static CreateTransformerFragment newInstance(TransformerResponse transformerResponse) {
        CreateTransformerFragment fragment = new CreateTransformerFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_transformer, container, false);
        return mBinding.getRoot();
    }

}
