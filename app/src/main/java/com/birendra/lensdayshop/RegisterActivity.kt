package com.birendra.lensdayshop

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.hardware.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.birendra.lensdayshop.entity.User
import com.birendra.lensdayshop.notification.NotificationSender
import com.birendra.lensdayshop.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class RegisterActivity : AppCompatActivity(),SensorEventListener {

    lateinit var cirRegisterButton : Button
    private lateinit var etFname : EditText
    private lateinit var etEmail : EditText
    private lateinit var etMobile : EditText
    private lateinit var etUsername : EditText
    private lateinit var etLname : EditText
    private lateinit var etAddress: EditText
    private lateinit var etPassword: EditText
    private lateinit var sensorManager: SensorManager
    var sensor: Sensor? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        binding()
        sensorManager=getSystemService(SENSOR_SERVICE) as SensorManager
        if(!checkSensor()) {
            return
        }
        else
        {
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
            sensorManager.registerListener(this,sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
        listener()
    }
    private fun checkSensor():Boolean
    {
        var flag = true
        if(sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) == null)
        {
            flag = false
        }
        return flag
    }

    private fun listener() {
        cirRegisterButton.setOnClickListener {
            var obj = User(fname = etFname.text.toString(),lname=etLname.text.toString(),username = etUsername.text.toString(),email = etEmail.text.toString(),password = etPassword.text.toString(),phone_number = etMobile.text.toString(),address = etAddress.text.toString())

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val userRepository = UserRepository()
                    val response = userRepository.registerUser(obj)
                    if(response.success == true)
                    {
                        withContext(Main)
                        {
                            val notificationChannel = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                NotificationChannel("Furniture","My notification",
                                    NotificationManager.IMPORTANCE_DEFAULT)
                            } else {
                                TODO("VERSION.SDK_INT < O")
                            }
                            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                            notificationManager.createNotificationChannel(notificationChannel)
                            val notificationInstance = NotificationCompat.Builder(this@RegisterActivity,"Furniture")
                                .setSmallIcon(android.R.drawable.arrow_up_float)
                                .setContentTitle("Furniture")
                                .setContentText("Registration Success!!")
                                .setAutoCancel(true)
                            notificationManager.notify(1,notificationInstance.build())
                            Toast.makeText(this@RegisterActivity, "${response.message}", Toast.LENGTH_SHORT).show()

                            val intent = Intent(this@RegisterActivity,LoginActivity::class.java)
                            startActivity(intent)
                        }
                    }
                    else
                    {

                        withContext(Main)
                        {
                            Toast.makeText(this@RegisterActivity,"${response.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                catch (ex:Exception)
                {
                    println(ex.printStackTrace())
                    withContext(Main)
                    {
                        Toast.makeText(this@RegisterActivity, "${ex.toString()}", Toast.LENGTH_SHORT).show()

                    }
                }


            }


        }
    }

    private fun binding() {
        cirRegisterButton= findViewById(R.id.cirRegisterButton)
        etFname = findViewById(R.id.etFname)
        etLname = findViewById(R.id.etLname)
        etEmail = findViewById(R.id.etEmail)
        etMobile = findViewById(R.id.etMobile)
        etPassword = findViewById(R.id.etPassword)
        etUsername = findViewById(R.id.etUsername)
        etAddress = findViewById(R.id.etAddress)

    }

    override fun onSensorChanged(event: SensorEvent?) {
        val values = event!!.values[0]
        if(values > 20000)
        {
            println(values)
            NotificationSender(this,"High brightness can damage your eyes","").createHighPriority()
        }

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }


}