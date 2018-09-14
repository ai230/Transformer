package com.aiyamamoto.transforemerapp;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aiyamamoto.transforemerapp.base.BaseFragment;
import com.aiyamamoto.transforemerapp.databinding.FragmentTransformersListBinding;
import com.aiyamamoto.transforemerapp.model.Transformer;
import com.aiyamamoto.transforemerapp.model.TransformersList;
import com.aiyamamoto.transforemerapp.network.TransformerService;
import com.aiyamamoto.transforemerapp.network.response.TransformerResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A placeholder fragment containing a simple view.
 */
public class TransformersListFragment extends BaseFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

//    Callback<List<TransformerResponse>> mTransforsListCallback;
//
//    mTransforsListCallback = new Callback<List<TransformerResponse>>
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
        getTrasnformersList();
        initTransformerListRecycler();
        return mBinding.getRoot();
    }

    private void initTransformerListRecycler() {
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.recyclerView.setHasFixedSize(true);
//        mTransformersListAdapter = new TransformersListAdapter(getActivity(), transformers);
//        mBinding.recyclerView.setAdapter(mTransformersListAdapter);
    }

    private void createTransformers() {
        Transformer t1 = new Transformer(1, "trams", 2,3,4,5,6,7, "A");
        Transformer t2 = new Transformer(2, "trams123", 2,3,4,5,6,7, "A");
        transformers.add(t1);
        transformers.add(t2);
    }

    private void getTrasnformersList() {
        TransformerService.getTransformersList(new Callback<TransformersList>() {
            @Override
            public void onResponse(Call<TransformersList> call, Response<TransformersList> response) {
                switch (response.code()) {
                    case 200:
                        TransformersList transformersList = response.body();
                        mTransformersListAdapter = new TransformersListAdapter(getActivity(), transformersList);
                        mBinding.recyclerView.setAdapter(mTransformersListAdapter);
                        break;
                    case 401:
                        showToast("401, Try again.");
                        break;
                    default:
                        showToast("Failed to get Transfermers list, Try again.");
                        break;

                }
            }

            @Override
            public void onFailure(Call<TransformersList> call, Throwable t) {
                showToast("Failed to get Transfermers list, Try again.");
            }
        });
    }
}
