package com.birendra.lensdayshop.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
      private const val BASE_URL = "http://10.0.2.2:90/"
//    private  const val BASE_URL = "http://localhost:90/"
//private const val BASE_URL = "http://192.168.43.132:90/"


    var token: String? = null
    var Online:Boolean?=null
    private val okHttp = OkHttpClient.Builder()
    private val retrofitBuilder =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttp.build())

    private val retrofit = retrofitBuilder.build()
    //generic function
    fun <T> buildService(serviceType: Class<T>): T {
        return retrofit.create(serviceType)
    }

    fun loadImgPath():String
    {
        var array = BASE_URL.split("/")
        return array[0]+"/"+array[2]+"/"
    }
}