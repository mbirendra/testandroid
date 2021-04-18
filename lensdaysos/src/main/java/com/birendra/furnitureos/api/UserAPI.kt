package com.birendra.furnitureos.api

import com.birendra.furnitureos.response.BookingResponse
import com.birendra.furnitureos.response.LoginResponse
import retrofit2.Response
import retrofit2.http.*

interface UserAPI {
    @FormUrlEncoded
    @POST("funfurnish/login")
    suspend fun loginUser(@Field("username") username:String, @Field("password") password:String): Response<LoginResponse>

    @GET("retrieve/myBookings")
    suspend fun retrieveBooking(
        @Header("Authorization") token:String
    ):Response<BookingResponse>
}