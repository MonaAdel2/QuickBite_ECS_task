package com.mona.adel.quickbite.data.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class MealWithDays(
    @Embedded
    var meal: Meal,
    @Relation(
        parentColumn = "mealId",
        entityColumn = "dayId",
        associateBy = Junction(MealDayCrossRef::class)
    )
    var days : List<DayOfWeek>
)
