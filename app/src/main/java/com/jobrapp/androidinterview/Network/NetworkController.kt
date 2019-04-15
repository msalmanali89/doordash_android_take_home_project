package com.jobrapp.androidinterview.Network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object NetworkController {
    private var retrofit: Retrofit? = null
    private val BASE_URL = "https://api.doordash.com/"

    val networkInstance: Retrofit
        get() {
            if (retrofit == null) {

                retrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
            }
            return retrofit!!
        }


}