package com.jobrapp.androidinterview.Network;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.jobrapp.androidinterview.dto.RestaurantDTO;

import java.util.List;

import javax.inject.Singleton;

import dagger.Provides;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;




public class NetworkCaller {

    private ApiService api;


    private NetworkCaller(@NonNull Application application) {
        this.api = NetworkController.INSTANCE.getNetworkInstance().create(ApiService.class);
    }

    @Provides
    @Singleton
    public static NetworkCaller getInstance(@NonNull Application application){

        NetworkCaller networkCaller =  null;
        try {
             networkCaller = new NetworkCaller(application);
        }catch (Exception e){

        }
        return  networkCaller;
    }



    MutableLiveData<List<RestaurantDTO>> data = new MutableLiveData<>();
    public LiveData<List<RestaurantDTO>> getNearByRestaurants(String lat,String lng,int offset, int limit)
    {
              this.api.getNearByRestaurants(lat,lng,offset,limit)
                        .enqueue(new Callback<List<RestaurantDTO>>()
                        {
                            @Override
                            public void onResponse(Call<List<RestaurantDTO>> call, Response<List<RestaurantDTO>> response)
                            {
                                if (response.isSuccessful()) {
                                    if(data.getValue()!=null) {
                                        data.getValue().addAll((response.body()));
                                        data.setValue(data.getValue());
                                    }else{
                                        data.setValue(response.body());
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<List<RestaurantDTO>> call, Throwable t) {
                                data.setValue(null);
                            }
                        });
        return data;
    }
}
