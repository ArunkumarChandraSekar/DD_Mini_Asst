package com.ex.dd_mini_asst.utils

import android.net.ConnectivityManager
import android.net.Network
import com.ex.dd_mini_asst.listeners.ConnectivityReceiverListener

class ConnectivityCallback() : ConnectivityManager.NetworkCallback() {
    var connectivityReceiverListener: ConnectivityReceiverListener? = null
    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        connectivityReceiverListener?.let {
            it.onNetworkConnectionChanged(true)
        }
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        connectivityReceiverListener?.let {
            it.onNetworkConnectionChanged(false)
        }
    }

    override fun onUnavailable() {
        super.onUnavailable()
        connectivityReceiverListener?.let {
            it.onNetworkConnectionChanged(false)
        }
    }
}