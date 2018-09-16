package com.aiyamamoto.transforemerapp;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

/**
 * A simple {@link Fragment} subclass.
 * {@link CreateTransformerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateTransformerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateTransformerFragment extends BaseFragment {

    public static final String EDIT_TRANSFORMER_KEY = "edit_transformer_key";

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

    /**
     * Use this factory method to create a new instance
     * of this fragment
     *
     * @return A new instance of fragment CreateTransformerFragment.
     */
    public static CreateTransformerFragment newInstance() {
        CreateTransformerFragment fragment = new CreateTransformerFragment();
        isEdit = false;
        return fragment;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters
     * to edit existing data
     *
     * @param transformer Transformer.
     * @return A new instance of fragment CreateTransformerFragment.
     */
    public static CreateTransformerFragment newInstance(Transformer transformer) {
        CreateTransformerFragment fragment = new CreateTransformerFragment();
        Bundle args = new Bundle();
        isEdit = true;
        args.putSerializable(EDIT_TRANSFORMER_KEY, transformer);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_transformer, container, false);
        if (isEdit) {
            if (getArguments() != null) {
                mTransformer = (Transformer) getArguments().getSerializable(EDIT_TRANSFORMER_KEY);
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
                    if(mBinding.nameEdittext.getText().toString().isEmpty()) {
                        showToast(getString(R.string.enter_name_msg));
                    } else {
                        if (isEdit) {
                            editTransformer();
                        } else {
                            createTransformer();
                        }
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
                    if (progress == 0) {
                        progress = 1;
                        mBinding.seekBarStrength.setProgress(progress);
                    }
                    mBinding.strengthTextview.setText("Strength " + progress);
                    strengthValue = progress;
                    break;

                case R.id.seekBar_intelligence:
                    if (progress == 0) {
                        progress = 1;
                        mBinding.seekBarIntelligence.setProgress(progress);
                    }
                    mBinding.intelligenceTextview.setText("Intelligence " + progress);
                    intelligenceValue = progress;
                    break;

                case R.id.seekBar_speed:
                    if (progress == 0) {
                        progress = 1;
                        mBinding.seekBarSpeed.setProgress(progress);
                    }
                    mBinding.speedTextview.setText("Speed " + progress);
                    speedValue = progress;
                    break;

                case R.id.seekBar_endurance:
                    if (progress == 0) {
                        progress = 1;
                        mBinding.seekBarEndurance.setProgress(progress);
                    }
                    mBinding.enduranceTextview.setText("Endurance " + progress);
                    enduranceValue = progress;
                    break;

                case R.id.seekBar_rank:
                    if (progress == 0) {
                        progress = 1;
                        mBinding.seekBarRank.setProgress(progress);
                    }
                    mBinding.rankTextview.setText("Rank " + progress);
                    rankValue = progress;
                    break;

                case R.id.seekBar_courage:
                    if (progress == 0) {
                        progress = 1;
                        mBinding.seekBarCourage.setProgress(progress);
                    }
                    mBinding.courageTextview.setText("Courage " + progress);
                    courageValue = progress;
                    break;

                case R.id.seekBar_firepower:
                    if (progress == 0) {
                        progress = 1;
                        mBinding.seekBarFirepower.setProgress(progress);
                    }
                    mBinding.firepowerTextview.setText("Firepower " + progress);
                    firepowerValue = progress;
                    break;

                case R.id.seekBar_skill:
                    if (progress == 0) {
                        progress = 1;
                        mBinding.seekBarSkill.setProgress(progress);
                    }
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


    /**
     * To create a new instance of CreateTransformerBody
     * to HTTP request
     *
     * @return A new instance of CreateTransformerBody
     */
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

    /**
     * This method is to call HTTP POST request
     * to create new Transformer.
     *
     * it will response back an updated TransformerResponse
     */
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
                        showToast(getString(R.string.failed_to_create_msg));
                        break;
                }
            }

            @Override
            public void onFailure(Call<TransformerResponse> call, Throwable t) {
                showToast(getString(R.string.failed_to_create_msg));
            }
        });
    }

    /**
     * This method is to call HTTP PUT request
     * to edit a existing transformer.
     *
     * it will response back an updated TransformerResponse
     */
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
                        showToast(getString(R.string.failed_to_create_msg));
                        break;
                }
            }

            @Override
            public void onFailure(Call<TransformerResponse> call, Throwable t) {
                showToast(getString(R.string.failed_to_create_msg));
            }
        });
    }

    /**
     * Sets the data to edit existing data.
     */
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

    /**
     * To allow an interaction in this fragment to be communicated
     * to MainActivity
     */
    public interface OnFragmentInteractionListener {
        void backToTransformerListFragment();
    }
}
