package com.birendra.lensdayshop

import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.birendra.lensdayshop.notification.NotificationSender
import com.birendra.lensdayshop.ui.dashboard.DashboardFragment
import com.birendra.lensdayshop.ui.home.HomeFragment
import com.birendra.lensdayshop.ui.notifications.NotificationsFragment

class BottomActivity : AppCompatActivity() ,SensorEventListener{

    private lateinit var sensorManager: SensorManager
    private var sensor: Sensor?=null
    private var sensor2:Sensor?=null
    private var sensor3:Sensor?=null
    private val permissions = arrayOf(
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )
    var fragments :MutableList<Fragment> = mutableListOf(HomeFragment(),DashboardFragment(),NotificationsFragment())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buttom)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        if(!checkPermission())
        {
            requestPermission()
        }

//        var fragmentPoint = intent.getIntExtra("fragNumber",-1)
//        if(fragmentPoint < 0)
//        {
//            supportFragmentManager.beginTransaction().apply {
//                replace(R.id.layout,HomeFragment())
//                addToBackStack(null)
//                commit()
//            }
//        }
//        else
//        {
//            supportFragmentManager.beginTransaction().apply {
//                replace(R.id.layout,fragments[fragmentPoint])
//                addToBackStack(null)
//                commit()
//            }
//        }

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        if(!checkSensor())
        {
            return
        }
        else
        {
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
            sensor2 = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
            sensor3 = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
            sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL)
            sensorManager.registerListener(this,sensor2,SensorManager.SENSOR_DELAY_NORMAL)
            sensorManager.registerListener(this,sensor3,SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    private fun checkSensor():Boolean
    {
        var flag = true
        if(sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) == null)
        {
            flag = false
        }
        return flag
    }

    private fun requestPermission()
    {
        ActivityCompat.requestPermissions(this@BottomActivity,permissions,123)
    }


    private fun checkPermission():Boolean
    {
        var checked = true
        for(i in permissions)
        if(ActivityCompat.checkSelfPermission(this@BottomActivity,i) != PackageManager.PERMISSION_GRANTED)
        {
            checked = false
        }

        return checked
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if(event!!.sensor.type == Sensor.TYPE_PROXIMITY)
        {
            val values = event!!.values[0]
            if(values <= 4)
            {
                val logOut = Logout(this,this)
                logOut.logout()
            }
        }
        if(event!!.sensor.type == Sensor.TYPE_LIGHT)
        {
            val values = event!!.values[0]
            if(values > 20000)
            {
                NotificationSender(this,"High Light can damage your eyes","").createHighPriority()
            }
        }
//        if(event!!.sensor.type == Sensor.TYPE_GYROSCOPE)
//        {
//            val values = event!!.values[1]
//            if(values > 0)
//            {
//                val intent = Intent(this,SignUpActivity::class.java)
//                startActivity(intent)
//            }
//            else
//            {
//                val intent = Intent(this,LoginActivity::class.java)
//                startActivity(intent)
//            }
//        }

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }
}