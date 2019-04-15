package com.jobrapp.androidinterview.ui.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jobrapp.androidinterview.R;
import com.jobrapp.androidinterview.adapters.RestaurantListAdapter;
import com.jobrapp.androidinterview.commons.Util;
import com.jobrapp.androidinterview.dto.RestaurantDTO;
import com.jobrapp.androidinterview.interfaces.ItemClick;
import com.jobrapp.androidinterview.interfaces.OnBottomReachedListener;
import com.jobrapp.androidinterview.viewmodels.RestaurantListViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class RestaurantListFragment extends  Fragment implements OnBottomReachedListener, ItemClick {

    public static final String TAG = "RestaurantListFragment";

    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    private Unbinder unbinder;


    ProgressBar progressBar;
    View rootView;
    GridLayoutManager  gridLayoutManager;
    RestaurantListAdapter restaurantListAdapter;
    RestaurantListViewModel usersViewModel;


    public static RestaurantListFragment getInstance(Context context){
        RestaurantListFragment usersFragment = new RestaurantListFragment();
        return usersFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_users,null,false);
        unbinder = ButterKnife.bind(this, rootView);

        progressBar = Util.createProgressDialog(getContext(),container);
        init();
        loadViewModel();
        return rootView;
    }


    public void showDialog(){
        if(progressBar!=null) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }


    public void hideDialog(){
        if(progressBar!=null) {
            progressBar.setVisibility(View.GONE);
        }
    }

    public void removeDialog(){
        if(progressBar!=null) {
            progressBar.setVisibility(View.GONE);
            progressBar = null;
        }
    }



    private void loadViewModel() {
        showDialog();
        usersViewModel = ViewModelProviders.of(this).get(RestaurantListViewModel.class);
        usersViewModel.getNearByRestaurants("37.422740","-122.139956",0,20).observe(this, new Observer<List<RestaurantDTO>>() {

            @Override
            public void onChanged(@Nullable List<RestaurantDTO> restaurantDTOList) {

                //Update RecyclerView

                if(restaurantDTOList !=  null) {
                    restaurantListAdapter.setDataSet(restaurantDTOList);
                    recyclerView.post(new Runnable()
                    {
                        @Override
                        public void run() {
                            restaurantListAdapter.notifyDataSetChanged();
                        }
                    });
                }else{

                    Toast.makeText(getActivity().getApplicationContext(),"Not connected to any network!",Toast.LENGTH_SHORT).show();
                }

                hideDialog();
            }

        });


        /*userByNameViewModel = ViewModelProviders.of(this).get(UserByNameViewModel.class);
        userByNameViewModel.getUserByName("").observe(this, new Observer<User>() {

            @Override
            public void onChanged(@Nullable User user) {

                //Update RecyclerView

                if(user !=  null) {

                    Toast.makeText(getActivity().getApplicationContext(),user.getName(),Toast.LENGTH_SHORT).show();

                }else{

                    Toast.makeText(getActivity().getApplicationContext(),"Not connected to any network!",Toast.LENGTH_SHORT).show();
                }
                //removeDialog();
            }

        });*/

    }

    private void init() {

        if(restaurantListAdapter == null){
            restaurantListAdapter =  new RestaurantListAdapter(getContext(), new ArrayList<RestaurantDTO>());
           recyclerView.setAdapter(restaurantListAdapter);
            restaurantListAdapter.setOnBottomReachedListener(this);
            restaurantListAdapter.setItemClick(this);
        }

        if(gridLayoutManager == null){
            gridLayoutManager = new GridLayoutManager(getActivity(),1);
            int currentOrientation = getResources().getConfiguration().orientation;
            if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                gridLayoutManager.setSpanCount(2);
            }
            else {
                gridLayoutManager.setSpanCount(1);
            }
            //gridLayoutManager.setSpanCount(1);
            recyclerView.setLayoutManager(gridLayoutManager);
        }
    }

    @Override
    public void onBottomReached(int position, int offset, int limit) {

        showDialog();
        usersViewModel.getMoreData("37.422740","-122.139956",offset,limit);
    }

    @Override
    public void onItemClick(RestaurantDTO restaurantDTO) {
        }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gridLayoutManager.setSpanCount(2);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            gridLayoutManager.setSpanCount(1);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        removeDialog();
    }
}
