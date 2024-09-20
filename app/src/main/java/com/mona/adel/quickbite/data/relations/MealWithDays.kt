package com.mona.adel.quickbite.data.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.mona.adel.quickbite.data.model.DayOfWeek
import com.mona.adel.quickbite.data.model.Meal

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
