package com.mona.adel.quickbite.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mona.adel.quickbite.data.model.Order

@Dao
interface OrderDao {
    @Insert
    suspend fun insertOrder(order: Order)

//    @Query("SELECT * FROM `orders` WHERE userId = :userId")
//    suspend fun getOrdersForUser(userId: Int): List<Order>
}