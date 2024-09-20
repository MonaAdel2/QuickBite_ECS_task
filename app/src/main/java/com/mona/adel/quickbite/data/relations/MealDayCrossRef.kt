package com.mona.adel.quickbite.data.relations

import androidx.room.Entity


@Entity(
    tableName = "meal_day_mapping",
    primaryKeys = ["mealId", "dayId"]
)
data class MealDayCrossRef(
    var mealId: Int,
    var dayId: Int
)