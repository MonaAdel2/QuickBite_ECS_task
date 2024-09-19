package com.mona.adel.quickbite.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mona.adel.quickbite.data.model.User

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM users WHERE userId = :userId")
    suspend fun getUserById(userId: Int): User
}