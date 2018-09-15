package com.aiyamamoto.transforemerapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.aiyamamoto.transforemerapp.model.Transformer;
import com.aiyamamoto.transforemerapp.utils.AppUtils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements TransformersListFragment.TransformersListFragmentListener,
        CreateTransformerFragment.CreateTransformerFragmentListner{

    private final String CREATE_TRANSFORMER_FRAGMENT = "create_transformer_fragment";
    private final String BATTLE_FRAGMENT = "battle_fragment";

//    public static  ArrayList<TransformerResponse> autobotsList;
//    public static ArrayList<TransformerResponse> decepticonsList;

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
        AppUtils.navigateToFragment(getSupportFragmentManager(), TransformersListFragment.newInstance());
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
                    AppUtils.addToFragment(getSupportFragmentManager(), BattleFragmentList.newInstance(), BATTLE_FRAGMENT);
                    fab.setVisibility(View.INVISIBLE);
                    fabBattle.setVisibility(View.INVISIBLE);
                    break;

                default:
                    break;
            }
        }
    };

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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        Fragment fragment = AppUtils.findFragmentByTag(getSupportFragmentManager(), BATTLE_FRAGMENT);

        if (fragment == null) {
            fragment = AppUtils.findFragmentByTag(getSupportFragmentManager(), CREATE_TRANSFORMER_FRAGMENT);
        }
        if(fragment != null) {
            fab.setVisibility(View.VISIBLE);
            fabBattle.setVisibility(View.VISIBLE);
            AppUtils.removeFragment(getSupportFragmentManager(), fragment);
        }
    }

    @Override
    public void editTransformer(Transformer transformer) {
        AppUtils.addToFragment(getSupportFragmentManager(),CreateTransformerFragment.newInstance(transformer), CREATE_TRANSFORMER_FRAGMENT);
        fab.setVisibility(View.GONE);
        fabBattle.setVisibility(View.GONE);
    }

    @Override
    public void backToTransformerListFragment() {
        fab.setVisibility(View.VISIBLE);
        fabBattle.setVisibility(View.VISIBLE);
        AppUtils.navigateToFragment(getSupportFragmentManager(),TransformersListFragment.newInstance());
    }
}
