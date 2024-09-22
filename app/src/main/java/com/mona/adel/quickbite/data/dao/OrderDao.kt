package com.mona.adel.quickbite.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mona.adel.quickbite.data.model.Order
import com.mona.adel.quickbite.data.model.User
import com.mona.adel.quickbite.data.relations.UserWithOrders

@Dao
interface OrderDao {
    @Insert
    suspend fun insertOrder(order: Order)

    @Query("SELECT EXISTS(SELECT 1 FROM orders WHERE userId = :userId AND orderDate = :todayDate)")
    suspend fun hasUserOrderedToday(userId: Int, todayDate: String): Boolean

    @Query("SELECT * FROM users WHERE userId = :userId" )
    suspend fun getOrdersWithUser(userId: Int): List<UserWithOrders>
}