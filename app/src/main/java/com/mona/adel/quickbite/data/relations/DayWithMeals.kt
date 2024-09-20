package com.mona.adel.quickbite.data.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.mona.adel.quickbite.data.model.DayOfWeek
import com.mona.adel.quickbite.data.model.Meal

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
