package com.birendra.lensdayshop

import android.app.Activity
import android.content.Context
import android.content.Intent

class Logout(val activity: Activity, val context: Context) {
    fun logout()
    {
        var pref = activity.getSharedPreferences("credentials",Context.MODE_PRIVATE)
        var editor = pref.edit()
        editor.putString("username","")
        editor.putString("password","")
        editor.apply()
        val intent = Intent(context,LoginActivity::class.java)
        context.startActivity(intent)
        activity.finish()
    }
}