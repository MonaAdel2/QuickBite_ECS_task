package com.mona.adel.quickbite.data.relations

import androidx.room.Entity
import androidx.room.ForeignKey
import com.mona.adel.quickbite.data.model.DayOfWeek
import com.mona.adel.quickbite.data.model.Meal


@Entity(
    primaryKeys = ["mealId", "dayId"],
    foreignKeys = [
        ForeignKey(
            entity = Meal::class,
            parentColumns = ["mealId"],
            childColumns = ["mealId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = DayOfWeek::class,
            parentColumns = ["dayId"],
            childColumns = ["dayId"]
        )
    ]
)
data class MealDayCrossRef(
    var mealId: Long,
    var dayId: Int
)