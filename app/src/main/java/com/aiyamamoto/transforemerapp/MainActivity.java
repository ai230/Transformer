package com.aiyamamoto.transforemerapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.aiyamamoto.transforemerapp.network.TransformerService;
import com.aiyamamoto.transforemerapp.network.response.TransformerResponse;
import com.aiyamamoto.transforemerapp.utils.AppUtils;
import com.aiyamamoto.transforemerapp.utils.SharedPreferencesUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements TransformersListFragment.TransformersListFragmentListener,
        CreateTransformerFragment.CreateTransformerFragmentListner{

    Callback<TransformerResponse> createTransforCallback;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        AppUtils.navigateToFragment(getSupportFragmentManager(), TransformersListFragment.newInstance());
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Fragment f = AppUtils.findFragmentByTag(getSupportFragmentManager(),"createTFFragment");
        AppUtils.removeFragment(getSupportFragmentManager(), f);
    }

    @Override
    public void addCreateTransformerFragment() {
        AppUtils.navigateToFragment(getSupportFragmentManager(),CreateTransformerFragment.newInstance());
    }

    @Override
    public void backToTransformerListFragment() {
        AppUtils.navigateToFragment(getSupportFragmentManager(),TransformersListFragment.newInstance());
    }
}
