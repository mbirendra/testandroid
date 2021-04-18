package com.birendra.lensdayshop

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

@Suppress("Deprecation")
class Connection(val context: Context) {

    fun getConnection():Boolean
    {
        var connection = false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val wifiConnection = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI) as NetworkInfo
        val mobileConnection = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE) as NetworkInfo
        println(connectivityManager.activeNetworkInfo)
        if((wifiConnection != null && wifiConnection.isConnected()) || (mobileConnection != null && mobileConnection.isConnected()))
        {
            connection = true
        }
        return connection
    }

}