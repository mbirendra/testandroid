package com.birendra.lensdayshop.connection

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.birendra.lensdayshop.Connection
import com.birendra.lensdayshop.api.RetrofitService

class ConnectionReceiver(val activity : Activity) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val conn = Connection(context)
        if(conn.getConnection())
        {
            RetrofitService.Online = true
            var user = AutoLogin(activity)
            user.userLogin()
            Toast.makeText(context, "You are online", Toast.LENGTH_SHORT).show()
        }
        else
        {
            RetrofitService.Online = false
            Toast.makeText(context, "You are offline", Toast.LENGTH_SHORT).show()

        }
    }
}