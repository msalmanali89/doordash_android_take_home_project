package com.jobrapp.androidinterview.Network

import com.jobrapp.androidinterview.dto.RestaurantDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {


    @GET("/v2/restaurant/?")
    abstract fun getNearByRestaurants(
            @Query("lat") lat: String,
            @Query("lng") lng: String,
            @Query("offset") offset: Int,
            @Query("limit") limit: Int
            ): Call<List<RestaurantDTO>>
}