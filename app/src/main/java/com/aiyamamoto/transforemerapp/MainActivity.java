package com.aiyamamoto.transforemerapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.aiyamamoto.transforemerapp.model.Result;
import com.aiyamamoto.transforemerapp.model.Transformer;
import com.aiyamamoto.transforemerapp.utils.AppUtils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements TransformersListFragment.OnFragmentInteractionListener,
        CreateTransformerFragment.OnFragmentInteractionListener,
        BattleListFragment.OnFragmentInteractionListener,
        ResultFragment.OnFragmentInteractionListener,
        NoNetworkFragment.OnFragmentInteractionListener,
        ChooseTransformerFragment.OnFragmentInteractionListener {

    // tag for fragments
    private final String CREATE_TRANSFORMER_FRAGMENT = "create_transformer_fragment";
    private final String BATTLE_FRAGMENT = "battle_fragment";
    private final String RESULT_FRAGMENT = "result_fragment";
    private final String CHOOSE_FRAGMENT = "choose_fragment";

    public static  ArrayList<Transformer> autobotsList;
    public static ArrayList<Transformer> decepticonsList;

    private FloatingActionButton fab;
    private FloatingActionButton fabBattle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setFab();
        checkNetworkAvailable();
    }

    private void setFab() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fabBattle = (FloatingActionButton) findViewById(R.id.fab_battle);
        fab.setOnClickListener(mOnClickListener);
        fab.setVisibility(View.VISIBLE);
        fabBattle.setOnClickListener(mOnClickListener);
        fabBattle.setVisibility(View.VISIBLE);
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.fab:
                    AppUtils.addToFragment(getSupportFragmentManager(),CreateTransformerFragment.newInstance(), CREATE_TRANSFORMER_FRAGMENT);
                    fab.setVisibility(View.INVISIBLE);
                    fabBattle.setVisibility(View.INVISIBLE);
                    break;
                case R.id.fab_battle:
                    AppUtils.addToFragment(getSupportFragmentManager(), BattleListFragment.newInstance(), BATTLE_FRAGMENT);
                    fab.setVisibility(View.INVISIBLE);
                    fabBattle.setVisibility(View.INVISIBLE);
                    break;

                default:
                    break;
            }
        }
    };

    /**
     * To check any network available.
     *
     * @return {@code true} if there is available network, otherwise {@code false}
     */
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * To check any network available.
     * If {@code true} it takes you to TransformersListFragment
     * if {@code false} it takes you to NoNetworkFragment
     */
    private void checkNetworkAvailable() {
        if (isNetworkAvailable()) {
            AppUtils.navigateToFragment(getSupportFragmentManager(), TransformersListFragment.newInstance());
            fab.setVisibility(View.VISIBLE);
            fabBattle.setVisibility(View.VISIBLE);
        } else {
            AppUtils.navigateToFragment(getSupportFragmentManager(), NoNetworkFragment.newInstance());
            fab.setVisibility(View.INVISIBLE);
            fabBattle.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Remove all fragments to be back to TransformersListFragment.
     */
    private void navigateToTransformerListFragment() {
        Fragment fragment = AppUtils.findFragmentByTag(getSupportFragmentManager(), CHOOSE_FRAGMENT);
        if (fragment != null) {
            AppUtils.removeFragment(getSupportFragmentManager(), fragment);
        }
        Fragment rf = AppUtils.findFragmentByTag(getSupportFragmentManager(), RESULT_FRAGMENT);
        if (rf != null) {
            AppUtils.removeFragment(getSupportFragmentManager(), rf);
        }

        Fragment bf = AppUtils.findFragmentByTag(getSupportFragmentManager(), BATTLE_FRAGMENT);
        if (bf != null) {
            AppUtils.removeFragment(getSupportFragmentManager(), bf);
        }

        Fragment cf = AppUtils.findFragmentByTag(getSupportFragmentManager(), CREATE_TRANSFORMER_FRAGMENT);
        if (cf != null) {
            AppUtils.removeFragment(getSupportFragmentManager(), cf);
        }
        if(cf != null || bf != null || fragment != null) {
            AppUtils.navigateToFragment(getSupportFragmentManager(), TransformersListFragment.newInstance());
            fab.setVisibility(View.VISIBLE);
            fabBattle.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            fab.setVisibility(View.GONE);
            fabBattle.setVisibility(View.GONE);
            AppUtils.addToFragment(getSupportFragmentManager(), ChooseTransformerFragment.newInstance(), CHOOSE_FRAGMENT);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // TransformerListFragment
    @Override
    public void editTransformer(Transformer transformer) {
        AppUtils.addToFragment(getSupportFragmentManager(),CreateTransformerFragment.newInstance(transformer), CREATE_TRANSFORMER_FRAGMENT);
        fab.setVisibility(View.GONE);
        fabBattle.setVisibility(View.GONE);
    }

    // TransformerListFragment
    @Override
    public void addTransformer() {
        AppUtils.addToFragment(getSupportFragmentManager(),CreateTransformerFragment.newInstance(), CREATE_TRANSFORMER_FRAGMENT);
        fab.setVisibility(View.INVISIBLE);
        fabBattle.setVisibility(View.INVISIBLE);
    }

    @Override
    public void toggleBattleBtn(int visibility) {
        fabBattle.setVisibility(visibility);
    }


    // CreateTransformerFragment
    // ResultFragment
    @Override
    public void backToTransformerListFragment() {
        navigateToTransformerListFragment();
    }

    // BattleListFragment
    @Override
    public void navigateToResult(Result result) {
        AppUtils.addToFragment(getSupportFragmentManager(), ResultFragment.newInstance(result), RESULT_FRAGMENT);
    }

    // BattleListFragment
    @Override
    public void navigateDirectlyToResult(Result result) {
        Fragment bf = AppUtils.findFragmentByTag(getSupportFragmentManager(),BATTLE_FRAGMENT);
        if (bf != null) {
            AppUtils.removeFragment(getSupportFragmentManager(), bf);
            AppUtils.addToFragment(getSupportFragmentManager(), ResultFragment.newInstance(result), RESULT_FRAGMENT);
        }
    }

    // ResultFragment
    @Override
    public void backToBattleListFragment() {
        // ResultFragment to BattleListFragment
        Fragment rf = AppUtils.findFragmentByTag(getSupportFragmentManager(), RESULT_FRAGMENT);
        if (rf != null) {
            AppUtils.removeFragment(getSupportFragmentManager(), rf);
        }
    }

    // when device back button is clicked
    @Override
    public void onBackPressed() {
        // ResultFragment to BattleListFragment
        Fragment rf = AppUtils.findFragmentByTag(getSupportFragmentManager(), RESULT_FRAGMENT);
        if (rf != null) {
            AppUtils.removeFragment(getSupportFragmentManager(), rf);
            Fragment battleFragment = AppUtils.findFragmentByTag(getSupportFragmentManager(), BATTLE_FRAGMENT);
            if (battleFragment == null) {
                // on TransformerListFragment
                AppUtils.navigateToFragment(getSupportFragmentManager(), TransformersListFragment.newInstance());
                fab.setVisibility(View.VISIBLE);
                fabBattle.setVisibility(View.VISIBLE);
            }
        } else {
            // ChooseTransformerFragment to TransformersListFragment
            Fragment chooseFragment = AppUtils.findFragmentByTag(getSupportFragmentManager(), CHOOSE_FRAGMENT);
            if (chooseFragment != null) {
                AppUtils.removeFragment(getSupportFragmentManager(), chooseFragment);
            }

            // BattleListFragment to TransformersListFragment
            Fragment bf = AppUtils.findFragmentByTag(getSupportFragmentManager(), BATTLE_FRAGMENT);
            if (bf != null) {
                AppUtils.removeFragment(getSupportFragmentManager(), bf);
            }

            // CreateTransformerFragment to TransformersListFragment
            Fragment cf = AppUtils.findFragmentByTag(getSupportFragmentManager(), CREATE_TRANSFORMER_FRAGMENT);
            if (cf != null) {
                AppUtils.removeFragment(getSupportFragmentManager(), cf);
            }
            if(cf != null || bf != null || chooseFragment != null) {
                // on TransformerListFragment
                AppUtils.navigateToFragment(getSupportFragmentManager(), TransformersListFragment.newInstance());
                fab.setVisibility(View.VISIBLE);
                fabBattle.setVisibility(View.VISIBLE);
            }
        }
    }

    // NoNetworkFragment
    @Override
    public void onClickedRetryBtn() {
        checkNetworkAvailable();
    }
}
