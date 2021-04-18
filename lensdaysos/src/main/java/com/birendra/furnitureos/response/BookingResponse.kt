package com.birendra.furnitureos.response

import com.birendra.furnitureos.entity.BookingFurniture

data class BookingResponse(val success:Boolean?=null,val message:String?=null,val data:MutableList<BookingFurniture>?=null)