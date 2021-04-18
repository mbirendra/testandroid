package com.birendra.lensdayshop.api

import com.birendra.lensdayshop.entity.BookingLensdays
import com.birendra.lensdayshop.response.BookingResponse
import retrofit2.Response
import retrofit2.http.*

interface BookingAPI {
    @POST("book/furniture")
    suspend fun bookProduct(
        @Header("Authorization") token:String,
        @Body record:BookingLensdays
    ):Response<BookingResponse>


    @GET("retrieve/myBookings")
    suspend fun retrieveBooking(
        @Header("Authorization") token:String
    ):Response<BookingResponse>

    @FormUrlEncoded
    @POST("update/booking")
    suspend fun updateBooking(
        @Header("Authorization") token:String,
        @Field("pid") id:String,
        @Field("qty") qty:Int
    ):Response<BookingResponse>

    @FormUrlEncoded
    @POST("delete/booking")
    suspend fun deleteBooking(
        @Header("Authorization") token:String,
        @Field("pid") id:String
    ):Response<BookingResponse>

}