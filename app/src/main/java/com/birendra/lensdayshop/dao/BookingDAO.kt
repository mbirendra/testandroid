package com.birendra.lensdayshop.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.birendra.lensdayshop.entity.BookingLensdays

@Dao
interface BookingDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookedProducts(bookings:MutableList<BookingLensdays>)

    @Query("delete from BookingLensdays")
    suspend fun deleteProducts()

    @Query("select * from BookingLensdays")
    suspend fun retrieveProducts():MutableList<BookingLensdays>

    @Query("update BookingLensdays set quantity = (:qty),price=(:price) where _id=(:id)")
    suspend fun updateBookings(qty:Int,price:Int,id:String)
}