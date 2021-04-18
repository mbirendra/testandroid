package com.birendra.lensdayshop.repository

import com.birendra.lensdayshop.api.ApiRequest
import com.birendra.lensdayshop.api.RetrofitService
import com.birendra.lensdayshop.api.UserAPI
import com.birendra.lensdayshop.entity.User
import com.birendra.lensdayshop.response.LoginResponse
import com.birendra.lensdayshop.response.RegisterResponse
import okhttp3.MultipartBody

class UserRepository():ApiRequest() {
    val userAPI = RetrofitService.buildService(UserAPI::class.java)

    suspend fun registerUser(user: User):RegisterResponse
    {
        return apiRequest {
            userAPI.registerUser(user)
        }
    }

    suspend fun loginUser(username:String,password:String):LoginResponse
    {
        return apiRequest {
            userAPI.loginUser(username,password)
        }
    }

    suspend fun userEdit(fn:String,ln:String,em:String,un:String,ad:String):LoginResponse
    {
        return apiRequest {
            userAPI.editDetails(RetrofitService.token!!,fn,ln,em,un,ad)
        }
    }


    suspend fun editImage(profileImg:MultipartBody.Part):LoginResponse
    {
        return  apiRequest {
            userAPI.changeImage(RetrofitService.token!!,profileImg)
        }
    }


}