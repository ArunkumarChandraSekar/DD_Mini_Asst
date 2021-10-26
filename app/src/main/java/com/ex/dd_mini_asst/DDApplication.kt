package com.ex.dd_mini_asst

import android.app.Application
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build
import com.ex.dd_mini_asst.listeners.ConnectivityReceiverListener
import com.ex.dd_mini_asst.reciver.ConnectivityReceiver
import com.ex.dd_mini_asst.utils.ConnectivityCallback

class DDApplication : Application() {
    lateinit var networkCallback: ConnectivityCallback
    lateinit var connectivityReceiver: ConnectivityReceiver

    companion object {
        var appInstance: DDApplication? = null
    }

    override fun onCreate() {
        super.onCreate()
        appInstance = this
        val cm = applicationContext.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            networkCallback = ConnectivityCallback()
            cm.registerDefaultNetworkCallback(networkCallback)
        } else {
            connectivityReceiver = ConnectivityReceiver()
            registerReceiver(
                connectivityReceiver,
                IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
            )
        }
    }

    fun setNetWorkListener(connectivityReceiverListener: ConnectivityReceiverListener) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            networkCallback.connectivityReceiverListener = connectivityReceiverListener
        } else {
            connectivityReceiver.connectivityReceiverListener = connectivityReceiverListener
        }

    }

}