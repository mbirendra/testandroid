package com.birendra.lensdayshop.response

import com.birendra.lensdayshop.entity.Lensdays

data class LensdaysResponse(val success:Boolean?=null, var data:MutableList<Lensdays>?=null, var message:String?=null)