package com.birendra.lensdayshop.response

import com.birendra.lensdayshop.entity.BookingLensdays

data class BookingResponse(val success:Boolean?=null,val message:String?=null,val data:MutableList<BookingLensdays>?=null)