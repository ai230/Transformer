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

    public static final String EDIT_TRANSFOREMER_KEY = "edit_transformer_key";

    private int strengthValue;
    private int intelligenceValue;
    private int speedValue;
    private int enduranceValue;
    private int rankValue;
    private int courageValue;
    private int firepowerValue;
    private int skillValue;

    private TransformerResponse mTransformerResponse;
    private static boolean isEdit;

    private FragmentCreateTransformerBinding mBinding;
    private CreateTransformerFragmentListner createTransformerFragmentListner;

    public interface CreateTransformerFragmentListner {
        void backToTransformerListFragment();
    }

    public static CreateTransformerFragment newInstance() {
        CreateTransformerFragment fragment = new CreateTransformerFragment();
        isEdit = false;
        return fragment;
    }

    public static CreateTransformerFragment newInstance(TransformerResponse transformerResponse) {
        CreateTransformerFragment fragment = new CreateTransformerFragment();
        Bundle args = new Bundle();
        isEdit = true;
        args.putSerializable(EDIT_TRANSFOREMER_KEY, transformerResponse);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_transformer, container, false);
        if (isEdit) {
            mTransformerResponse = (TransformerResponse) getArguments().getSerializable(EDIT_TRANSFOREMER_KEY);
            insertData();
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
            id = mTransformerResponse.getId();
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

    private void editTransformer() {
        TransformerService.editTransformer(createTransformerBody(), new Callback<TransformerResponse>() {
            @Override
            public void onResponse(Call<TransformerResponse> call, Response<TransformerResponse> response) {
                switch (response.code()) {
                    case 200:
                        showToast("Updated.");
                        createTransformerFragmentListner.backToTransformerListFragment();
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

    private void insertData() {
        mBinding.newEdit.setText("Edit");
        mBinding.nameEdittext.setText(mTransformerResponse.getName());
        if (mTransformerResponse.getTeam().equals("A")) {
            mBinding.autobotsRadio.setChecked(true);
        } else {
            mBinding.decepticonRadio.setChecked(true);
        }
        mBinding.strengthTextview.setText("Strength " + mTransformerResponse.getStrength());
        mBinding.seekBarStrength.setProgress(mTransformerResponse.getStrength());
        mBinding.intelligenceTextview.setText("Inteligence " + mTransformerResponse.getIntelligence());
        mBinding.seekBarIntelligence.setProgress(mTransformerResponse.getIntelligence());
        mBinding.speedTextview.setText("Speed " + mTransformerResponse.getSpeed());
        mBinding.seekBarSpeed.setProgress(mTransformerResponse.getSpeed());
        mBinding.enduranceTextview.setText("Endurance " + mTransformerResponse.getEndurance());
        mBinding.seekBarEndurance.setProgress(mTransformerResponse.getEndurance());
        mBinding.rankTextview.setText("Rank " + mTransformerResponse.getRank());
        mBinding.seekBarRank.setProgress(mTransformerResponse.getRank());
        mBinding.courageTextview.setText("Courage " + mTransformerResponse.getCourage());
        mBinding.seekBarCourage.setProgress(mTransformerResponse.getCourage());
        mBinding.firepowerTextview.setText("Firepower " + mTransformerResponse.getFirepower());
        mBinding.seekBarFirepower.setProgress(mTransformerResponse.getFirepower());
        mBinding.skillTextview.setText("Skill " + mTransformerResponse.getSkill());
        mBinding.seekBarSkill.setProgress(mTransformerResponse.getSkill());
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
