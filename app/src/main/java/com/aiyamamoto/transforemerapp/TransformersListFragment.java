package com.aiyamamoto.transforemerapp;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aiyamamoto.transforemerapp.databinding.FragmentTransformersListBinding;
import com.aiyamamoto.transforemerapp.model.Transformer;

import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class TransformersListFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    public TransformersListFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static TransformersListFragment newInstance(String param1, String param2) {
        TransformersListFragment fragment = new TransformersListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private FragmentTransformersListBinding mBinding;
    TransformersListAdapter mTransformersListAdapter;
    ArrayList<Transformer> transformers = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_transformers_list, container, false);
        createTransformers();
        initTransformerListRecycler();
        return mBinding.getRoot();
    }

    private void initTransformerListRecycler() {
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.recyclerView.setHasFixedSize(true);
        mTransformersListAdapter = new TransformersListAdapter(getActivity(), transformers);
        mBinding.recyclerView.setAdapter(mTransformersListAdapter);
    }

    private void createTransformers() {
        Transformer t1 = new Transformer(1, "trams", 2,3,4,5,6,7, "A");
        Transformer t2 = new Transformer(2, "trams123", 2,3,4,5,6,7, "A");
        transformers.add(t1);
        transformers.add(t2);
    }

}
