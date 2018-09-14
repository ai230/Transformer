package com.aiyamamoto.transforemerapp.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.aiyamamoto.transforemerapp.R;

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

    public static void addToFragment(FragmentManager fragmentManager, Fragment fragment, String tag) {
        fragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .add(R.id.fragment, fragment, tag)
                .commit();
    }

    public static Fragment findFragmentByTag(FragmentManager fragmentManager, String tag) {
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        return fragment;
    }

    public static void removeFragment(FragmentManager fragmentManager, Fragment fragment) {
        fragmentManager.beginTransaction()
                .remove(fragment)
                .commit();
    }

    public static void navigateToFragmentBackStack(FragmentManager fragmentManager, Fragment fragment) {
        fragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.fragment, fragment)
                .addToBackStack(null)
                .commit();
    }

}
