package com.aiyamamoto.transforemerapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NoNetworkFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NoNetworkFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoNetworkFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private Button retryBtn;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment NoNetworkFragment.
     */
    public static NoNetworkFragment newInstance() {
        return new NoNetworkFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_no_network, container, false);
        retryBtn = (Button) view.findViewById(R.id.retry_btn);
        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClickedRetryBtn();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
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
        void onClickedRetryBtn();
    }
}
