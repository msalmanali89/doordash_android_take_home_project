package com.jobrapp.androidinterview

import android.app.Application
import android.arch.lifecycle.LiveData
import android.content.Context
import android.graphics.drawable.Drawable
import android.support.test.InstrumentationRegistry
import android.support.test.InstrumentationRegistry.getInstrumentation
import android.support.test.rule.ActivityTestRule
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.jobrapp.androidinterview.Network.ApiService
import com.jobrapp.androidinterview.Network.NetworkController
import com.jobrapp.androidinterview.commons.Util
import com.jobrapp.androidinterview.dto.RestaurantDTO
import com.jobrapp.androidinterview.ui.fragments.RestaurantListFragment
import junit.framework.Assert.fail
import kotlinx.android.synthetic.main.fragment_users.*
import org.junit.*

import java.net.InetAddress
import java.util.concurrent.CountDownLatch
import java.net.UnknownHostException
import com.jobrapp.androidinterview.interfaces.ImageLoader
import com.jobrapp.androidinterview.repository.AppRepository


class MainActivityTest {

    @get:Rule
    var rule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    lateinit var appRepository: AppRepository
    lateinit var restaurantList: LiveData<List<RestaurantDTO>>


    lateinit var imageLoader: ImageLoader

    var mainActivity: MainActivity? = null
    lateinit var  api: ApiService

    lateinit var instrumentationCtx: Context


    @Before
    fun setUp() {
        mainActivity =  rule.activity
        instrumentationCtx = InstrumentationRegistry.getTargetContext()

    }



    @Test
    fun activityLaunch(){

        if ( this.mainActivity!=null && mainActivity!!.findViewById<View>(R.id.fragmentContainer) !=null ){
            assert(true)
        }else{
            fail()
        }
    }

    @Test
    fun fragmentLaunch(){

        if ( this.mainActivity!=null && mainActivity!!.findViewById<View>(R.id.fragmentContainer) !=null ){

            var fragment: RestaurantListFragment = RestaurantListFragment.getInstance(this.mainActivity)
            this.mainActivity?.addFragment(fragment)

            getInstrumentation().waitForIdleSync()

            if ( fragment!=null && (fragment.recyclerView!!.findViewById<View>(R.id.recyclerView)) !=null ) {

                assert(true)
            }else{
                fail()
            }

        }else{
            fail()
        }


    }

    @Test fun callToServer() {

            api = NetworkController.networkInstance.create(ApiService::class.java)

            val response = api.getNearByRestaurants("37.422740", "-122.139956", 0, 20).execute()
            val authResponse = response.body()
            Assert.assertNotNull(authResponse)
            Assert.assertEquals(20, authResponse?.size)
    }

    @Throws(UnknownHostException::class)
    fun getLocalhostName(): String {
        try {
            return InetAddress.getLocalHost().hostName
        } catch (e: UnknownHostException) {
            throw RuntimeException(e.message)
        }

    }

    @Test
    @Throws(UnknownHostException::class)
    fun testGetLocalhostName() {
        val host = getLocalhostName()
        Assert.assertEquals("localhost", host)
    }


    @Test
    fun getNearByRestaurants() {
        appRepository = AppRepository(instrumentationCtx.applicationContext as Application)
        restaurantList = appRepository.getNearByRestaurants("37.422740", "-122.139956", 0, 20)
        Assert.assertNotNull(restaurantList)
    }

    @Test
    fun getMoreData() {
        appRepository = AppRepository(instrumentationCtx.applicationContext as Application)
        restaurantList = appRepository.getNearByRestaurants("37.422740", "-122.139956", 20, 20)
        Assert.assertNotNull(restaurantList)
    }


    @Test
    fun isOnline() {
        Assert.assertEquals(true, Util.isConnectedNetwork(instrumentationCtx.applicationContext))
    }

    @After
    fun tearDown() {
        mainActivity = null
    }
}