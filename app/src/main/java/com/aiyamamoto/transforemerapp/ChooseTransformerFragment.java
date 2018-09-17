package com.aiyamamoto.transforemerapp;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aiyamamoto.transforemerapp.base.BaseFragment;
import com.aiyamamoto.transforemerapp.databinding.FragmentChooseTransformerBinding;
import com.aiyamamoto.transforemerapp.network.TransformerService;
import com.aiyamamoto.transforemerapp.network.body.CreateTransformerBody;
import com.aiyamamoto.transforemerapp.network.response.TransformerResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChooseTransformerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChooseTransformerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChooseTransformerFragment extends BaseFragment {

    private OnFragmentInteractionListener mListener;

    private FragmentChooseTransformerBinding mBinding;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ChooseTransformerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChooseTransformerFragment newInstance() {
        ChooseTransformerFragment fragment = new ChooseTransformerFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_choose_transformer, container, false);
        mBinding.btn1.setOnClickListener(mOnClickListener);
        mBinding.btn2.setOnClickListener(mOnClickListener);
        mBinding.btn3.setOnClickListener(mOnClickListener);
        mBinding.btn4.setOnClickListener(mOnClickListener);
        mBinding.btn5.setOnClickListener(mOnClickListener);
        mBinding.backBtn.setOnClickListener(mOnClickListener);
        return mBinding.getRoot();
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn1:
                    CreateTransformerBody body1 = new CreateTransformerBody("0", "BLUESTREAK", "A", 6,6,7,9,5,2,9,7);
                    setButtonDisabled(false);
                    createTransformer(body1);
                    break;
                case R.id.btn2:
                    CreateTransformerBody body2 = new CreateTransformerBody("0", "HUBCAP", "A", 4,4,4,4,4,4,4,4);
                    setButtonDisabled(false);
                    createTransformer(body2);
                    break;
                case R.id.btn3:
                    CreateTransformerBody body3 = new CreateTransformerBody("0", "SOUNDWAVE", "D", 8,9,2,6,7,5,6,10);
                    setButtonDisabled(false);
                    createTransformer(body3);
                    break;
                case R.id.btn4:
                    CreateTransformerBody body4 = new CreateTransformerBody("0", "PREDAKING", "D", 10, 5,1,8,7,9,9,8);
                    setButtonDisabled(false);
                    createTransformer(body4);
                    break;
                case R.id.btn5:
                    CreateTransformerBody body5 = new CreateTransformerBody("0", "OPTIMUS PRIME", "A", 10,10,8,10,10,10,8,10);
                    setButtonDisabled(false);
                    createTransformer(body5);
                    break;
                case R.id.back_btn:
                    mListener.backToTransformerListFragment();
                    break;

            }
        }
    };

    /**
     * This method is to call HTTP POST request
     * to create new Transformer.
     *
     * it will response back an updated TransformerResponse
     */
    private void createTransformer(CreateTransformerBody body){
        TransformerService.createTransformer(body, new Callback<TransformerResponse>() {
            @Override
            public void onResponse(Call<TransformerResponse> call, Response<TransformerResponse> response) {

                switch (response.code()) {
                    case 201:
//                        mListener.backToTransformerListFragment();
                        if (response.body() != null) {
                            showToast(response.body().getName() + " is Created!");
                        }
                        break;
                    case 401:
                        showToast("401 " + getString(R.string.failed_to_create_msg));
                        break;
                    default:
                        showToast(getString(R.string.failed_to_create_msg));
                        break;
                }
                setButtonDisabled(true);
            }

            @Override
            public void onFailure(Call<TransformerResponse> call, Throwable t) {
                setButtonDisabled(true);
                showToast(getString(R.string.failed_to_create_msg));
            }
        });
    }

    private void setButtonDisabled(boolean boo) {
        mBinding.btn1.setClickable(boo);
        mBinding.btn1.setEnabled(boo);
        mBinding.btn2.setClickable(boo);
        mBinding.btn2.setEnabled(boo);
        mBinding.btn3.setClickable(boo);
        mBinding.btn3.setEnabled(boo);
        mBinding.btn4.setClickable(boo);
        mBinding.btn4.setEnabled(boo);
        mBinding.btn5.setClickable(boo);
        mBinding.btn5.setEnabled(boo);
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
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void backToTransformerListFragment();
    }
}
