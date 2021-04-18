package com.birendra.lensdayshop.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.birendra.lensdayshop.entity.User

@Dao
interface UserDAO {
    @Insert
    suspend fun insertUser(user: User)

    @Query("select * from User")
    suspend fun retrieveUser():User

    @Query("delete from User")
    suspend fun deleteUser()
}