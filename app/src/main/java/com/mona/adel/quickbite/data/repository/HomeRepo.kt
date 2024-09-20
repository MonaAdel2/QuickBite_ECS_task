package com.mona.adel.quickbite.data.repository

import com.mona.adel.quickbite.data.model.Meal
import com.mona.adel.quickbite.data.relations.DayWithMeals

interface HomeRepo {

    suspend fun getMealsByDay(day: String): DayWithMeals
}