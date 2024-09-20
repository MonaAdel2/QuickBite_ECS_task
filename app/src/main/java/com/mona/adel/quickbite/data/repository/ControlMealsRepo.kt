package com.mona.adel.quickbite.data.repository

import com.mona.adel.quickbite.data.model.Meal
import com.mona.adel.quickbite.data.relations.MealDayCrossRef

interface ControlMealsRepo {

    suspend fun insertMeal(newMeal: Meal): Long?

    suspend fun insertMealDay(mealDayCrossRef: MealDayCrossRef)
}