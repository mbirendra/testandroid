package com.birendra.lensdayshop.api

import com.birendra.lensdayshop.response.LensdaysResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LensdaysAPI {
    @FormUrlEncoded
    @POST("product/showall")
    suspend fun retrieveProducts(
        @Field("category") category:String
    ):Response<LensdaysResponse>
}