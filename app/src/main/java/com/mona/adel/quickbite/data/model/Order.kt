package com.mona.adel.quickbite.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "orders",
    foreignKeys = [
        ForeignKey(entity = User::class, parentColumns = ["userId"], childColumns = ["userId"]),
        ForeignKey(entity = Meal::class, parentColumns = ["mealId"], childColumns = ["mealId"])
    ])
data class Order(
    @PrimaryKey(autoGenerate = true)
    var orderId: Int,
    var userId: Int,
    var mealId: Int,
    var orderDate:String
)
