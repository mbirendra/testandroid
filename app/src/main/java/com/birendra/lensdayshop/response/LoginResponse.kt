package com.birendra.lensdayshop.response

import com.birendra.lensdayshop.entity.User

data class LoginResponse(val success:Boolean?=null,val message:String?=null,val token:String?=null,val data:User?=null)