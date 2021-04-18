package com.birendra.lensdayshop.dao

import androidx.room.*
import com.birendra.lensdayshop.entity.Lensdays

@Dao
interface LensdaysDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLensdays(lstLensdays:MutableList<Lensdays>)

    @Query("delete from Lensdays where category = (:category)")
    suspend fun deleteLensdays(category: String)

    @Query("select * from Lensdays as f where f.category = (:category)")
    suspend fun retrieveLensdays(category: String):MutableList<Lensdays>
}