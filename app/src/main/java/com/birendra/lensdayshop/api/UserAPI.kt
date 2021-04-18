package com.birendra.lensdayshop.api

import com.birendra.lensdayshop.entity.User
import com.birendra.lensdayshop.response.LoginResponse
import com.birendra.lensdayshop.response.RegisterResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface UserAPI {
    @POST("added/insert")
    suspend fun registerUser(@Body user: User):Response<RegisterResponse>

    @FormUrlEncoded
    @POST("funfurnish/login")
    suspend fun loginUser(@Field("username") username:String,@Field("password") password:String):Response<LoginResponse>


    @FormUrlEncoded
    @POST("update/details")
    suspend fun editDetails(
        @Header("Authorization") token:String,
        @Field("fname") fn:String,
        @Field("lname") ln:String,
        @Field("email") em:String,
        @Field("username") un:String,
        @Field("address") ad:String,
    ):Response<LoginResponse>

    @Multipart
    @PUT("change/profilePicture")
    suspend fun changeImage(
        @Header("Authorization") token:String,
        @Part profileImg:MultipartBody.Part
    ):Response<LoginResponse>
}