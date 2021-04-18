package com.birendra.furnitureos

import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.view.View
import android.widget.TextView
import com.birendra.furnitureos.api.RetrofitService
import com.birendra.furnitureos.repository.UserRepository
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

@Suppress("DEPRECATION")
class DashboardActivity : WearableActivity() {
    private lateinit var tvCart:TextView
    private lateinit var username:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        tvCart = findViewById(R.id.tvCart)
        username=findViewById(R.id.username)
        username.text=RetrofitService.user!!.username

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val bookingRepository = UserRepository()
                val response = bookingRepository.retrieveBooking()
                if(response.success == true)
                {
                  withContext(Dispatchers.Main)
                  {

                      tvCart.text = "${response.data!!.size} items on cart."
                  }
                }
                else
                {
                    withContext(Dispatchers.Main)
                    {

                        tvCart.text = "Cart is Empty"
                    }
                }
            }
            catch (ex: Exception)
            {
                withContext(Dispatchers.Main)
                {
                    val snk = Snackbar.make(tvCart,"${ex.toString()}", Snackbar.LENGTH_LONG)
                    snk.setAction("Cancel", View.OnClickListener {
                        snk.dismiss()
                    })
                    snk.show()
                    println(ex.printStackTrace())
                }
            }
        }
        // Enables Always-on
        setAmbientEnabled()
    }
}