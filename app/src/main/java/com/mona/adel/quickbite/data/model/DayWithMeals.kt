package com.mona.adel.quickbite.data.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class DayWithMeals(
    @Embedded
    var dayOfWeek: DayOfWeek,
    @Relation(
        parentColumn = "dayId",
        entityColumn = "mealId",
        associateBy = Junction(MealDayCrossRef::class)
    )
    var meals : List<Meal>
)
