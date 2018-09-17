package com.aiyamamoto.transforemerapp;

import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
import com.aiyamamoto.transforemerapp.network.response.TransformerResponse;
import com.aiyamamoto.transforemerapp.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * {@link TransformersListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TransformersListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransformersListFragment extends BaseFragment implements TransformersListAdapter.AdapterCallback{

    private FragmentTransformersListBinding mBinding;
    private TransformersListAdapter mTransformersListAdapter;

    OnFragmentInteractionListener mListener;

    /**
     * To be called when it is long clicked each transformer
     *
     * @param transformerId
     * @param name showing the name of transformer to delete
     */
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

    @Override
    public void editTransformer(Transformer transformerResponse) {
        mListener.editTransformer(transformerResponse);
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
                    showToast("401 " + getString(R.string.failed_to_delete_msg));
                    break;
                default:
                    showToast(getString(R.string.failed_to_delete_msg));
                    break;
            }
        }

        @Override
        public void onFailure(Call<Void> call, Throwable t) {
            showToast(getString(R.string.failed_to_delete_msg));
        }
    };

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ResultFragment.
     */
    public static TransformersListFragment newInstance() {
        TransformersListFragment fragment = new TransformersListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_transformers_list, container, false);
        findToken();
        initTransformerListRecycler();
        return mBinding.getRoot();
    }

    private void initTransformerListRecycler() {
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.recyclerView.setHasFixedSize(true);
    }

    private void findToken() {
        getToken();
        getTransformersList(this);
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
                    switch (response.code()) {
                        case 200:
                            TransformerService.ACCESS_TOKEN = response.body().toString();
                            setToken(TransformerService.ACCESS_TOKEN);
                            break;
                        default:
                            Toast.makeText(getActivity(), "Try again", Toast.LENGTH_SHORT).show();
                            break;
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

    /**
     * This method is to call HTTP GET request
     * to get transformers list.
     *
     * @param callback TransformersListAdapter.AdapterCallback
     *                 to delete a transformer or editing a transformer
     */
    private void getTransformersList(final TransformersListAdapter.AdapterCallback callback) {
        TransformerService.getTransformersList(new Callback<TransformersList>() {
            @Override
            public void onResponse(Call<TransformersList> call, Response<TransformersList> response) {
                switch (response.code()) {
                    case 200:
                        TransformersList transformersList = response.body();
                        mTransformersListAdapter = new TransformersListAdapter(getActivity(), callback, setOrderTransformerList(transformersList));
                        mBinding.recyclerView.setAdapter(mTransformersListAdapter);
                        break;
                    case 401:
                        showToast("401 " + getString(R.string.failed_to_get_msg));
                        break;
                    default:
                        showToast(getString(R.string.failed_to_get_msg));
                        break;
                }
            }

            @Override
            public void onFailure(Call<TransformersList> call, Throwable t) {
                showToast(getString(R.string.failed_to_get_msg));
            }
        });
    }

    /**
     * To organize by team and sort transformer list by rank.
     *
     * @param list TransformersList that is responded from database
     * @return Sorted {@code ArrayList<TransformerResponse>}
     */
    public ArrayList<Transformer> setOrderTransformerList(TransformersList list) {

        ArrayList<TransformerResponse> transformerResponseList = list.getTransformers();
        MainActivity.autobotsList = new ArrayList<>();
        MainActivity.decepticonsList = new ArrayList<>();

        for (int i=0; i < transformerResponseList.size(); i++) {
            Transformer transformer = new Transformer(transformerResponseList.get(i).getId(),
                    transformerResponseList.get(i).getName(),
                    transformerResponseList.get(i).getStrength(),
                    transformerResponseList.get(i).getIntelligence(),
                    transformerResponseList.get(i).getSpeed(),
                    transformerResponseList.get(i).getEndurance(),
                    transformerResponseList.get(i).getRank(),
                    transformerResponseList.get(i).getCourage(),
                    transformerResponseList.get(i).getFirepower(),
                    transformerResponseList.get(i).getSkill(),
                    transformerResponseList.get(i).getTeam(),
                    transformerResponseList.get(i).getTeam_icon());
            if (transformerResponseList.get(i).getTeam().equals("A")) {
                MainActivity.autobotsList.add(transformer);
            } else {
                MainActivity.decepticonsList.add(transformer);
            }
        }

        // sort by rank
        Collections.sort(MainActivity.autobotsList, new Comparator<Transformer>() {
            @Override
            public int compare(Transformer o1, Transformer o2) {
                return o2.getRank() - o1.getRank();
            }
        });
        Collections.sort(MainActivity.decepticonsList, new Comparator<Transformer>() {
            @Override
            public int compare(Transformer o1, Transformer o2) {
                return o2.getRank() - o1.getRank();
            }
        });
        ArrayList<Transformer> newList = new ArrayList<>();
        newList.addAll(MainActivity.autobotsList);
        newList.addAll(MainActivity.decepticonsList);

        return newList;
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

    /**
     * to allow an interaction in this fragment to be communicated
     * to MainActivity
     */
    public interface OnFragmentInteractionListener {
        void editTransformer(Transformer transformerResponse);
    }
}
