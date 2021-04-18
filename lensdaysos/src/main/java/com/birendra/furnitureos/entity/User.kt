package com.birendra.furnitureos.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(var _id:String?=null,var fname:String?=null,var lname:String?=null,var phone_number:String?=null,var address:String?=null,var username:String?=null,var email:String?=null,var password:String?=null,var profileImg:String?=null) {

    @PrimaryKey(autoGenerate = true)
    var id : Int = 0

}