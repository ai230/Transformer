package com.aiyamamoto.transforemerapp;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.aiyamamoto.transforemerapp.base.BaseFragment;
import com.aiyamamoto.transforemerapp.databinding.FragmentCreateTransformerBinding;
import com.aiyamamoto.transforemerapp.network.TransformerService;
import com.aiyamamoto.transforemerapp.network.body.CreateTransformerBody;
import com.aiyamamoto.transforemerapp.network.response.TransformerResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CreateTransformerFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Callback<TransformerResponse> createTransforCallback;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private int strengthValue;
    private int intelligenceValue;
    private int speedValue;
    private int enduranceValue;
    private int rankValue;
    private int courageValue;
    private int firepowerValue;
    private int skillValue;

    private FragmentCreateTransformerBinding mBinding;
    private CreateTransformerFragmentListner createTransformerFragmentListner;

    public interface CreateTransformerFragmentListner {
        void backToTransformerListFragment();
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
        mBinding.createTransformerBtn.setOnClickListener(mOnClickListener);
        mBinding.seekBarStrength.setOnSeekBarChangeListener(mOnSeekBarChangeListener);
        mBinding.seekBarIntelligence.setOnSeekBarChangeListener(mOnSeekBarChangeListener);
        return mBinding.getRoot();
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.createTransformerBtn:
                    createTransformer();
                    break;

                default:
                    break;
            }
        }
    };

    SeekBar.OnSeekBarChangeListener mOnSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            switch (seekBar.getId()) {
                case R.id.seekBar_strength:
                    mBinding.strengthTextview.setText("Strength " + progress);
                    strengthValue = progress;
                    break;

                case R.id.seekBar_intelligence:
                    mBinding.intelligenceTextview.setText("Intelligence " + progress);
                    intelligenceValue = progress;
                    break;

                case R.id.seekBar_speed:
                    mBinding.strengthTextview.setText("Speed " + progress);
                    speedValue = progress;
                    break;

                case R.id.seekBar_endurance:
                    mBinding.intelligenceTextview.setText("Endurance " + progress);
                    enduranceValue = progress;
                    break;
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private CreateTransformerBody createTransformerBody() {

        String name = mBinding.nameEdittext.getText().toString();
        String team = "";
        if (mBinding.autobotsRadio.isChecked()) {
            team = "A";
        } else {
            team = "D";
        }


        CreateTransformerBody body = new CreateTransformerBody(name, team,2, 3,4,5,6,7, 10, 9);

        return body;
    }

    private void createTransformer(){
        TransformerService.createTransformer(createTransformerBody(), new Callback<TransformerResponse>() {
            @Override
            public void onResponse(Call<TransformerResponse> call, Response<TransformerResponse> response) {
                switch (response.code()) {
                    case 201:
                        createTransformerFragmentListner.backToTransformerListFragment();
                        break;
                    case 401:
                        showToast("401 Failed to create a Transfermer, Try again.");
                        break;
                    default:
                        showToast("Failed to create a Transfermer, Try again.");
                        break;
                }
            }

            @Override
            public void onFailure(Call<TransformerResponse> call, Throwable t) {
                showToast("Failed to create a Transfermer, Try again.");
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CreateTransformerFragmentListner) {
            createTransformerFragmentListner = (CreateTransformerFragmentListner) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        createTransformerFragmentListner = null;
    }
}
