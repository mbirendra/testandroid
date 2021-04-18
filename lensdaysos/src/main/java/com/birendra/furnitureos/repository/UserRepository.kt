package com.birendra.furnitureos.repository

import com.birendra.furnitureos.api.ApiRequest
import com.birendra.furnitureos.api.RetrofitService
import com.birendra.furnitureos.api.UserAPI
import com.birendra.furnitureos.response.BookingResponse
import com.birendra.furnitureos.response.LoginResponse

class UserRepository():ApiRequest() {
    val userAPI = RetrofitService.buildService(UserAPI::class.java)


    suspend fun loginUser(username:String,password:String): LoginResponse
    {
        return apiRequest {
            userAPI.loginUser(username,password)
        }
    }

    suspend fun retrieveBooking(): BookingResponse
    {
        return apiRequest {
            userAPI.retrieveBooking(RetrofitService.token!!)
        }
    }


}