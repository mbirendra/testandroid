package com.birendra.lensdayshop.response

import com.birendra.lensdayshop.entity.User

data class RegisterResponse(val success:Boolean?=null,val data: User?=null,val message:String?=null)