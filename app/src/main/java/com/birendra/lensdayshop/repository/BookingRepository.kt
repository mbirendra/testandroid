package com.birendra.lensdayshop.repository

import com.birendra.lensdayshop.api.ApiRequest
import com.birendra.lensdayshop.api.BookingAPI
import com.birendra.lensdayshop.api.RetrofitService
import com.birendra.lensdayshop.entity.BookingLensdays
import com.birendra.lensdayshop.response.BookingResponse

class BookingRepository():ApiRequest() {
    val bookingAPI = RetrofitService.buildService(BookingAPI::class.java)

    suspend fun bookFurniture(booking:BookingLensdays):BookingResponse
    {
        return apiRequest {
            bookingAPI.bookProduct(RetrofitService.token!!,booking)
        }
    }

    suspend fun retrieveBooking():BookingResponse
    {
        return apiRequest {
            bookingAPI.retrieveBooking(RetrofitService.token!!)
        }
    }

    suspend fun updateBooking(id:String,qty:Int):BookingResponse
    {
        return apiRequest {
            bookingAPI.updateBooking(RetrofitService.token!!,id,qty)
        }
    }

    suspend fun deleteBooking(id:String):BookingResponse
    {
        return apiRequest {
            bookingAPI.deleteBooking(RetrofitService.token!!,id)
        }
    }
}