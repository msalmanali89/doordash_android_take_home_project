package com.jobrapp.androidinterview.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.jobrapp.androidinterview.dto.RestaurantDTO;
import com.jobrapp.androidinterview.repository.AppRepository;

import java.util.List;

public class RestaurantListViewModel extends AndroidViewModel {

    AppRepository appRepository;
    LiveData<List<RestaurantDTO>> restaurantList;

    public RestaurantListViewModel(@NonNull Application application){
        super(application);
        appRepository = new AppRepository(application);
    }

    public LiveData<List<RestaurantDTO>> getNearByRestaurants(String lat,String lng,int offset, int limit) {

        if(restaurantList == null){
            restaurantList = appRepository.getNearByRestaurants(lat, lng, offset, limit);
        }
        return restaurantList;
    }

    public void getMoreData(String lat,String lng,int offset, int limit) {
        appRepository.getMoreData(lat, lng, offset, limit);
    }



}
