package com.jobrapp.androidinterview.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.jobrapp.androidinterview.Network.NetworkCaller;
import com.jobrapp.androidinterview.dto.RestaurantDTO;

import java.util.List;

public class AppRepository {

    LiveData<List<RestaurantDTO>> restaurantList;
    Application application;
    NetworkCaller networkCaller;

    public AppRepository(@NonNull Application application) {
        this.application = application;
        networkCaller = NetworkCaller.getInstance(application);
    }

    public LiveData<List<RestaurantDTO>> getNearByRestaurants(String lat,String lng,int offset, int limit) {
        if (restaurantList == null) {
            restaurantList = networkCaller.getNearByRestaurants(lat, lng, offset, limit);
        }
        return restaurantList;
    }

    public void getMoreData(String lat,String lng,int offset, int limit) {
        restaurantList = networkCaller.getNearByRestaurants(lat, lng, offset, limit);
    }
    /*
    public LiveData<User> getUserByName(String name) {
        return networkCaller.getUserByName(name);
    }
    */



}
