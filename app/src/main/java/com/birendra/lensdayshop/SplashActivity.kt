package com.birendra.lensdayshop

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.birendra.lensdayshop.api.RetrofitService
import com.birendra.lensdayshop.db.LensdaysDB
import com.birendra.lensdayshop.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val pref = getSharedPreferences("credentials", Context.MODE_PRIVATE)
        val username = pref.getString("email", "")
        val password = pref.getString("password", "")
        CheckConnection(this@SplashActivity,this).checkRegisteredNetwork()
        //IO and Main
       CoroutineScope(Dispatchers.Main).launch {
           delay(1000)
           if (RetrofitService.Online == false) {

               val intent=Intent(this@SplashActivity,BottomActivity::class.java)
               startActivity(intent)
           } else {


               CoroutineScope(Dispatchers.IO).launch {
                   delay(4000)

                   if (username != "" && password != "") {
                       CoroutineScope(Dispatchers.IO).launch {
                           try {
                               val repo = UserRepository()
                               val response = repo.loginUser(username!!, password!!)
                               if (response.success == true) {
                                   var instance = LensdaysDB.getInstance(this@SplashActivity).getUserDAO()
                                   instance.deleteUser()
                                   instance.insertUser(response.data!!)
                                   RetrofitService.token = "Bearer " + response.token
                                   val intent = Intent(this@SplashActivity, BottomActivity::class.java)
                                   startActivity(intent)
                                   finish()
                               } else {
                                   val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                                   startActivity(intent)
                                   finish()
                               }
                           } catch (err: Exception) {
                               val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                               startActivity(intent)
                               finish()
                           }
                       }
                   } else {
                       val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                       startActivity(intent)
                       finish()
                   }
               }
           }
       }

    }

    override fun onDestroy() {
        super.onDestroy()
        CheckConnection(this@SplashActivity,this
        ).unregisteredNetwork()
    }

}