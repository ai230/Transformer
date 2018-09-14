package com.aiyamamoto.transforemerapp;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aiyamamoto.transforemerapp.base.BaseFragment;
import com.aiyamamoto.transforemerapp.databinding.FragmentTransformersListBinding;
import com.aiyamamoto.transforemerapp.model.Transformer;
import com.aiyamamoto.transforemerapp.model.TransformersList;
import com.aiyamamoto.transforemerapp.network.TransformerService;
import com.aiyamamoto.transforemerapp.utils.SharedPreferencesUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A placeholder fragment containing a simple view.
 */
public class TransformersListFragment extends BaseFragment{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    TransformersListFragmentListener transformersListFragmentListener;

    public interface TransformersListFragmentListener {
        void addCreateTransformerFragment();
    }

    public static TransformersListFragment newInstance() {
        TransformersListFragment fragment = new TransformersListFragment();
        return fragment;
    }

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
        mBinding.fab.setOnClickListener(mOnClickListener);
        mBinding.fab.setVisibility(View.VISIBLE);
        findToken();
        initTransformerListRecycler();
        return mBinding.getRoot();
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.fab:
                    transformersListFragmentListener.addCreateTransformerFragment();
                    mBinding.fab.setVisibility(View.INVISIBLE);
                    break;

                default:
                    break;
            }
        }
    };

    private void initTransformerListRecycler() {
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.recyclerView.setHasFixedSize(true);
    }

    private void findToken() {
        getToken();

        if (TransformerService.ACCESS_TOKEN.equals("")) {
            TransformerService.getAllsparkToken(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.code() == 200) {
                        TransformerService.ACCESS_TOKEN = response.body().toString();
                        setToken(TransformerService.ACCESS_TOKEN);
                    } else {
                        Toast.makeText(getActivity(), "Try again", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(getActivity(), "Try again", Toast.LENGTH_SHORT).show();
                }
            });
        }
        getTrasnformersList();
    }

    private void setToken(String token) {
        SharedPreferencesUtils.setToken(getActivity(), token);
    }

    private void getToken() {
        TransformerService.ACCESS_TOKEN = "";
        TransformerService.ACCESS_TOKEN = SharedPreferencesUtils.getToken(getActivity());
    }

    private void clearToken() {
        SharedPreferencesUtils.clearToken(getActivity());
        TransformerService.ACCESS_TOKEN = SharedPreferencesUtils.getToken(getActivity());
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof TransformersListFragmentListener) {
            transformersListFragmentListener = (TransformersListFragmentListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        transformersListFragmentListener = null;
    }
}
