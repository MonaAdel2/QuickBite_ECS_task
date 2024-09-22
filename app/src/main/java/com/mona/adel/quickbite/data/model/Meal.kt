package com.mona.adel.quickbite.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meals")
data class Meal(
    @PrimaryKey
    var mealId:Int? =null,
    var mealName: String?,
    var category: String?,
    var price: Double?,
    var days: List<String> = emptyList()
)
