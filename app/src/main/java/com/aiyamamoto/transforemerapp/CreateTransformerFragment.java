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
import com.aiyamamoto.transforemerapp.model.Transformer;
import com.aiyamamoto.transforemerapp.network.TransformerService;
import com.aiyamamoto.transforemerapp.network.body.CreateTransformerBody;
import com.aiyamamoto.transforemerapp.network.response.TransformerResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CreateTransformerFragment extends BaseFragment {

    public static final String EDIT_TRANSFOREMER_KEY = "edit_transformer_key";

    private int strengthValue;
    private int intelligenceValue;
    private int speedValue;
    private int enduranceValue;
    private int rankValue;
    private int courageValue;
    private int firepowerValue;
    private int skillValue;

    private Transformer mTransformer;
    private static boolean isEdit;

    private FragmentCreateTransformerBinding mBinding;
    private OnFragmentInteractionListener mListener;

    public static CreateTransformerFragment newInstance() {
        CreateTransformerFragment fragment = new CreateTransformerFragment();
        isEdit = false;
        return fragment;
    }

    public static CreateTransformerFragment newInstance(Transformer transformer) {
        CreateTransformerFragment fragment = new CreateTransformerFragment();
        Bundle args = new Bundle();
        isEdit = true;
        args.putSerializable(EDIT_TRANSFOREMER_KEY, transformer);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_transformer, container, false);
        if (isEdit) {
            if (getArguments() != null) {
                mTransformer = (Transformer) getArguments().getSerializable(EDIT_TRANSFOREMER_KEY);
                initData();
            }
        }
        setListener();
        return mBinding.getRoot();
    }

    private void setListener() {
        mBinding.createTransformerBtn.setOnClickListener(mOnClickListener);
        mBinding.seekBarStrength.setOnSeekBarChangeListener(mOnSeekBarChangeListener);
        mBinding.seekBarIntelligence.setOnSeekBarChangeListener(mOnSeekBarChangeListener);
        mBinding.seekBarSpeed.setOnSeekBarChangeListener(mOnSeekBarChangeListener);
        mBinding.seekBarEndurance.setOnSeekBarChangeListener(mOnSeekBarChangeListener);
        mBinding.seekBarRank.setOnSeekBarChangeListener(mOnSeekBarChangeListener);
        mBinding.seekBarCourage.setOnSeekBarChangeListener(mOnSeekBarChangeListener);
        mBinding.seekBarFirepower.setOnSeekBarChangeListener(mOnSeekBarChangeListener);
        mBinding.seekBarSkill.setOnSeekBarChangeListener(mOnSeekBarChangeListener);
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.createTransformerBtn:
                    if (isEdit) {
                        editTransformer();
                    } else {
                        createTransformer();
                    }
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
                    mBinding.speedTextview.setText("Speed " + progress);
                    speedValue = progress;
                    break;

                case R.id.seekBar_endurance:
                    mBinding.enduranceTextview.setText("Endurance " + progress);
                    enduranceValue = progress;
                    break;

                case R.id.seekBar_rank:
                    mBinding.rankTextview.setText("Rank " + progress);
                    rankValue = progress;
                    break;

                case R.id.seekBar_courage:
                    mBinding.courageTextview.setText("Courage " + progress);
                    courageValue = progress;
                    break;

                case R.id.seekBar_firepower:
                    mBinding.firepowerTextview.setText("Firepower " + progress);
                    firepowerValue = progress;
                    break;

                case R.id.seekBar_skill:
                    mBinding.skillTextview.setText("Skill " + progress);
                    skillValue = progress;
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

        String id = "";
        if (isEdit) {
            id = mTransformer.getId();
        } else {
            id = "0";
        }
        String name = mBinding.nameEdittext.getText().toString();
        String team = "";
        if (mBinding.autobotsRadio.isChecked()) {
            team = "A";
        } else {
            team = "D";
        }
        strengthValue = mBinding.seekBarStrength.getProgress();
        intelligenceValue = mBinding.seekBarIntelligence.getProgress();
        speedValue = mBinding.seekBarSpeed.getProgress();
        enduranceValue = mBinding.seekBarEndurance.getProgress();
        rankValue = mBinding.seekBarRank.getProgress();
        courageValue = mBinding.seekBarCourage.getProgress();
        firepowerValue = mBinding.seekBarFirepower.getProgress();
        skillValue = mBinding.seekBarSkill.getProgress();

        CreateTransformerBody body = new CreateTransformerBody(
                id, name, team,strengthValue, intelligenceValue, speedValue,enduranceValue, rankValue, courageValue, firepowerValue, skillValue);

        return body;
    }

    private void createTransformer(){
        TransformerService.createTransformer(createTransformerBody(), new Callback<TransformerResponse>() {
            @Override
            public void onResponse(Call<TransformerResponse> call, Response<TransformerResponse> response) {
                switch (response.code()) {
                    case 201:
                        mListener.backToTransformerListFragment();
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

    private void editTransformer() {
        TransformerService.editTransformer(createTransformerBody(), new Callback<TransformerResponse>() {
            @Override
            public void onResponse(Call<TransformerResponse> call, Response<TransformerResponse> response) {
                switch (response.code()) {
                    case 200:
                        showToast("Updated.");
                        mListener.backToTransformerListFragment();
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

    private void initData() {
        mBinding.newEdit.setText("Edit");
        mBinding.nameEdittext.setText(mTransformer.getName());
        if (mTransformer.getTeam().equals("A")) {
            mBinding.autobotsRadio.setChecked(true);
        } else {
            mBinding.decepticonRadio.setChecked(true);
        }
        mBinding.strengthTextview.setText("Strength " + mTransformer.getStrength());
        mBinding.seekBarStrength.setProgress(mTransformer.getStrength());
        mBinding.intelligenceTextview.setText("Inteligence " + mTransformer.getIntelligence());
        mBinding.seekBarIntelligence.setProgress(mTransformer.getIntelligence());
        mBinding.speedTextview.setText("Speed " + mTransformer.getSpeed());
        mBinding.seekBarSpeed.setProgress(mTransformer.getSpeed());
        mBinding.enduranceTextview.setText("Endurance " + mTransformer.getEndurance());
        mBinding.seekBarEndurance.setProgress(mTransformer.getEndurance());
        mBinding.rankTextview.setText("Rank " + mTransformer.getRank());
        mBinding.seekBarRank.setProgress(mTransformer.getRank());
        mBinding.courageTextview.setText("Courage " + mTransformer.getCourage());
        mBinding.seekBarCourage.setProgress(mTransformer.getCourage());
        mBinding.firepowerTextview.setText("Firepower " + mTransformer.getFirepower());
        mBinding.seekBarFirepower.setProgress(mTransformer.getFirepower());
        mBinding.skillTextview.setText("Skill " + mTransformer.getSkill());
        mBinding.seekBarSkill.setProgress(mTransformer.getSkill());

        mBinding.createTransformerBtn.setText("Update Transformer");
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
        void backToTransformerListFragment();
    }
}
