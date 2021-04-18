package com.birendra.furnitureos.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Furniture(
    @PrimaryKey
    val _id:String="",
    var pname:String?=null,
    var pprice:Int = 0,
    var pdesc:String?=null,
    var pimage:String?=null,
    var availableStock:Int = 0,
    var pBrand:String?=null,
    var discount:Int = 0,
    var newPrice:Int = 0,
    var discountedAmount:Int = 0,
    var category:String? = null,
    var onSale:Boolean?=null
)