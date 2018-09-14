package com.aiyamamoto.transforemerapp.base;

import android.support.v4.app.Fragment;
import android.widget.Toast;

/**
 * Created by aiyamamoto on 2018-09-13.
 */

public class BaseFragment extends Fragment{

    protected void showToast(String message) {
        if (getContext() != null) {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }
}
