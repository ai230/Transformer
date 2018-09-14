package com.aiyamamoto.transforemerapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by aiyamamoto on 2018-09-13.
 */

public class AppUtils {

    public static void navigateToFragment(FragmentManager fragmentManager, Fragment fragment) {
        fragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.fragment, fragment)
                .commit();
    }

}
