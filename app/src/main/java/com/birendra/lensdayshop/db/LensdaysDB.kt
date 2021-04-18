package com.birendra.lensdayshop.db

import android.content.Context
import androidx.room.*
import com.birendra.lensdayshop.converter.FurnitureConverter
import com.birendra.lensdayshop.dao.BookingDAO
import com.birendra.lensdayshop.dao.LensdaysDAO
import com.birendra.lensdayshop.dao.UserDAO
import com.birendra.lensdayshop.entity.BookingLensdays
import com.birendra.lensdayshop.entity.Lensdays
import com.birendra.lensdayshop.entity.User

@Database(
    entities = [(User::class),(Lensdays::class),(BookingLensdays::class)],
    version=5,
    exportSchema = false
)
@TypeConverters(FurnitureConverter::class)
abstract class LensdaysDB(): RoomDatabase() {
    abstract fun getUserDAO():UserDAO
    abstract fun getLensdaysDAO():LensdaysDAO
    abstract fun getBookingDAO():BookingDAO
    companion object{
        private var instance : LensdaysDB? = null

        fun getInstance(context: Context):LensdaysDB
        {
            synchronized(LensdaysDB::class){
                instance = buildDatabase(context)
            }

            return instance!!
        }

        private fun buildDatabase(context:Context):LensdaysDB
        {
            return Room.databaseBuilder(context.applicationContext,LensdaysDB::class.java,"LensdaysDB").fallbackToDestructiveMigration().build()
        }
    }
}