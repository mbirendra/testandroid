package com.birendra.lensdayshop.repository

import com.birendra.lensdayshop.api.ApiRequest
import com.birendra.lensdayshop.api.LensdaysAPI
import com.birendra.lensdayshop.api.RetrofitService
import com.birendra.lensdayshop.response.LensdaysResponse

class LensdaysRepository():ApiRequest() {
    var furnitureAPI = RetrofitService.buildService(LensdaysAPI::class.java)

    suspend fun retrieveLensdays(category:String):LensdaysResponse
    {
        return apiRequest {
            furnitureAPI.retrieveProducts(category)
        }
    }
}