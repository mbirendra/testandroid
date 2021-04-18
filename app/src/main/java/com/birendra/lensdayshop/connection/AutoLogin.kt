package com.birendra.lensdayshop.connection

import android.app.Activity
import android.content.Context
import com.birendra.lensdayshop.api.RetrofitService
import com.birendra.lensdayshop.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AutoLogin(val activity: Activity) {
    fun userLogin()
    {
        var pref = activity.getSharedPreferences("credentials", Context.MODE_PRIVATE)
        var username = pref.getString("username","")
        var password = pref.getString("password","")
        if(username != "" && password != "")
        {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val repo = UserRepository()
                    val response = repo.loginUser(username!!,password!!)
                    if(response.success == true)
                    {
                        RetrofitService.token = "Bearer "+response.token
                    }
                    else
                    {
                        println(response.message)
                    }
                }
                catch (ex:Exception)
                {
                    println(ex.toString())
                }
            }
        }
    }
}