package com.mona.adel.quickbite.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class Order(
    @PrimaryKey(autoGenerate = true)
    var orderId: Int? =null,
    var userId: Int,
    var mealId: Int,
    var orderDate:String
)
