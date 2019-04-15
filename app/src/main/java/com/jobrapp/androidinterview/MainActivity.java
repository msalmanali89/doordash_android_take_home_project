package com.jobrapp.androidinterview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.jobrapp.androidinterview.ui.fragments.RestaurantListFragment;

public class MainActivity extends AppCompatActivity {

    RestaurantListFragment restaurantListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null) {
            restaurantListFragment = RestaurantListFragment.getInstance(this);
            addFragment(restaurantListFragment);

        }else{
            restaurantListFragment = (RestaurantListFragment) getSupportFragmentManager().findFragmentByTag(RestaurantListFragment.TAG);
        }
    }


    public void addFragment(Fragment fragment){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =  fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.add(R.id.fragmentContainer, fragment, RestaurantListFragment.TAG);
        fragmentTransaction.commitAllowingStateLoss();

    }


    @Override
    public void onBackPressed() {
        finish();
    }
}