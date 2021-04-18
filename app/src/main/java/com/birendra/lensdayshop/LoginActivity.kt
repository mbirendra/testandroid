package com.birendra.lensdayshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.birendra.lensdayshop.api.RetrofitService
import com.birendra.lensdayshop.db.LensdaysDB
import com.birendra.lensdayshop.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class LoginActivity : AppCompatActivity() {

    lateinit var tvRegister : TextView
    lateinit var face : ImageView
    lateinit var goog : ImageView
    private lateinit var cirLoginButton:Button
    private lateinit var etEmail : EditText
    private lateinit var etPassword : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        binding()
        listener()
        face.translationY= 1000F
        goog.translationY= 1000F

        face.animate().translationY(0F).setDuration(1000).setStartDelay(200)
      goog.animate().translationY(0F).setDuration(1000).setStartDelay(300)
    }

    private fun listener() {
        tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        cirLoginButton . setOnClickListener {
            var email = etEmail.text.toString()
            var password = etPassword.text.toString()

            CoroutineScope(Dispatchers.IO).launch {

                    try {
                        val userRepository = UserRepository()
                        val response = userRepository.loginUser(etEmail.text.toString(),etPassword.text.toString())
                        if(response.success == true)
                        {
                            RetrofitService.token = "Bearer "+response.token
                            var instance = LensdaysDB.getInstance(this@LoginActivity).getUserDAO()
                            instance.deleteUser()
                            instance.insertUser(response.data!!)
                            var getPref = getSharedPreferences("credentials", MODE_PRIVATE)
                            var editor = getPref.edit()
                            editor.putString("email",email)
                            editor.putString("password",password)
                            editor.apply()
                            val intent = Intent(this@LoginActivity,BottomActivity::class.java)
                            startActivity(intent)

                        }
                        else
                        {
                            withContext(Main)
                            {
                                Toast.makeText(this@LoginActivity, "Invalid credentials", Toast.LENGTH_SHORT).show()
                            }

                        }
                    }
                    catch (ex:Exception)
                    {
                        withContext(Main)
                        {
                            Toast.makeText(this@LoginActivity, "${ex.toString()}", Toast.LENGTH_SHORT).show()
                            println(ex.printStackTrace())
                        }
                    }



            }
        }
    }

    private fun binding() {
        tvRegister = findViewById(R.id.tvRegister)
        face = findViewById(R.id.face)
        goog = findViewById(R.id.goog)
        cirLoginButton=findViewById(R.id.cirLoginButton)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
    }

}