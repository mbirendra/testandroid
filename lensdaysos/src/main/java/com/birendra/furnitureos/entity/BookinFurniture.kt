package com.birendra.furnitureos.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BookingFurniture(
    @PrimaryKey
    val _id:String = "",
    var product_id:Furniture? = null,
    var quantity:Int = 0,
    var price:Int = 0,
    var booked_At:String?=null,
    var delivery_address:String?=null,
    var delivery_number:String?=null,
    var productName:String?=null,
    var productImage:String?=null,
    var productBrand:String?=null,
    var offlineAvailableStock:Int=0,
    var newPrice:Int=0,
    var pprice:Int =0,
    var offlineDiscount:Int=0


)