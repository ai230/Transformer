package com.aiyamamoto.transforemerapp;

import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aiyamamoto.transforemerapp.base.BaseFragment;
import com.aiyamamoto.transforemerapp.databinding.FragmentTransformersListBinding;
import com.aiyamamoto.transforemerapp.model.TransformersList;
import com.aiyamamoto.transforemerapp.network.TransformerService;
import com.aiyamamoto.transforemerapp.utils.SharedPreferencesUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A placeholder fragment containing a simple view.
 */
public class TransformersListFragment extends BaseFragment implements TransformersListAdapter.AdapterCallback{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FragmentTransformersListBinding mBinding;
    private TransformersListAdapter mTransformersListAdapter;

    TransformersListFragmentListener transformersListFragmentListener;

    @Override
    public void deleteTransformer(final String transformerId, String name) {
        new AlertDialog.Builder(getActivity())
                .setTitle("DELETE")
                .setMessage("Are you sure, you want to delete " + name)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // OK button pressed
                        TransformerService.deleteTransformer(deleteTransformerCallback, transformerId);
                    }
                })
                .setNegativeButton("CANCEL", null)
                .show();
    }

    public interface TransformersListFragmentListener {
        void addCreateTransformerFragment();
    }

    private Callback<Void> deleteTransformerCallback = new Callback<Void>() {
        @Override
        public void onResponse(Call<Void> call, Response<Void> response) {
            switch (response.code()) {
                case 204:
                    showToast("Deleted.");
                    findToken();
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
        public void onFailure(Call<Void> call, Throwable t) {
            showToast("Failed to get Transfermers list, Try again.");
        }
    };


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
        getToken(); //TODO dose't need to go through when it came back from deleteing
        getTrasnformersList(this);
    }

    private void setToken(String token) {
        SharedPreferencesUtils.setToken(getActivity(), token);
    }

    private void getToken() {
        TransformerService.ACCESS_TOKEN = "";
        TransformerService.ACCESS_TOKEN = SharedPreferencesUtils.getToken(getActivity());
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
    }

    private void clearToken() {
        SharedPreferencesUtils.clearToken(getActivity());
        TransformerService.ACCESS_TOKEN = SharedPreferencesUtils.getToken(getActivity());
    }

    private void getTrasnformersList(final TransformersListAdapter.AdapterCallback callback) {
        TransformerService.getTransformersList(new Callback<TransformersList>() {
            @Override
            public void onResponse(Call<TransformersList> call, Response<TransformersList> response) {
                switch (response.code()) {
                    case 200:
                        TransformersList transformersList = response.body();
                        mTransformersListAdapter = new TransformersListAdapter(getActivity(), callback, transformersList);
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

    private void showAlertDialog() {
        new AlertDialog.Builder(getActivity())
                .setTitle("title")
                .setMessage("message")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // OK button pressed
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
