package com.birendra.furnitureos

import android.content.Intent
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.birendra.furnitureos.api.RetrofitService
import com.birendra.furnitureos.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Suppress("DEPRECATION")
class MainActivity : WearableActivity(),View.OnClickListener {
    private  lateinit var  etUsername:EditText
    private lateinit var etPassword:EditText
    private lateinit var btnLogin:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        btnLogin= findViewById(R.id.btnLogin)
        btnLogin.setOnClickListener(this)
        // Enables Always-on
        setAmbientEnabled()
    }

    private fun authenticateUser()
    {
        if(TextUtils.isEmpty(etUsername.text))
        {
            etUsername.error = "Insert Username"
            etUsername.requestFocus()
        }
        else if(TextUtils.isEmpty(etPassword.text))
        {
            etPassword.error = "Insert Password"
            etPassword.requestFocus()
        }
        else
        {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val repo = UserRepository()
                    val response = repo.loginUser(etUsername.text.toString(),etPassword.text.toString())
                    if(response.success == true)
                    {

                        RetrofitService.token = "Bearer "+response.token
                        RetrofitService.user = response.data
                        withContext(Dispatchers.Main)
                        {
                            val intent = Intent(this@MainActivity,DashboardActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                    else
                    {
                        withContext(Dispatchers.Main)
                        {
                            etUsername.snackbar("${response.message}")
                        }
                    }
                }
                catch (ex:Exception)
                {
                    println(ex.printStackTrace())
                    withContext(Dispatchers.Main)
                    {
                        etUsername.snackbar("There is a maintenance break.")
                    }
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when(v!!.id)
        {
            R.id.btnLogin ->{
                authenticateUser()
            }
        }
    }
}