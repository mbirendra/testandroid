package com.birendra.lensdayshop.converter

import androidx.room.TypeConverter
import com.birendra.lensdayshop.entity.Lensdays

class FurnitureConverter {
    companion object{
        @TypeConverter
        @JvmStatic
        fun fromBookingFurniture(record:Lensdays):String
        {
            return record._id
        }

        @TypeConverter
        @JvmStatic
        fun toBookingFurniture(recordId:String):Lensdays
        {
            return recordId.let { Lensdays(it) }
        }
    }
}