package com.mona.adel.quickbite.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.mona.adel.quickbite.data.model.Order
import com.mona.adel.quickbite.data.model.User

data class UserWithOrders(
    @Embedded
    val user : User,
    @Relation(
        parentColumn = "userId", // in users table
        entityColumn = "userId"  // in orders table
    )
    val orders : List<Order>
)
