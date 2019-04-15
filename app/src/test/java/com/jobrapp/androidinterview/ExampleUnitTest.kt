package com.jobrapp.androidinterview

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.jobrapp.androidinterview.Network.ApiService
import com.jobrapp.androidinterview.Network.NetworkController
import com.jobrapp.androidinterview.dto.RestaurantDTO
import org.junit.Test

import org.junit.Assert.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test fun sample() {
        assertEquals(4, 2 + 2)
    }
}
