package com.birendra.furnitureos.response

import com.birendra.furnitureos.entity.User

data class LoginResponse(val success:Boolean?=null,val message:String?=null,val token:String?=null,val data: User?=null)