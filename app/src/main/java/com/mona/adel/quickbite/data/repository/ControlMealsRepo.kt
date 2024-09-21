package com.mona.adel.quickbite.data.repository

import com.mona.adel.quickbite.data.model.Meal
import com.mona.adel.quickbite.data.relations.MealDayCrossRef
import com.mona.adel.quickbite.data.relations.MealWithDays

interface ControlMealsRepo {

    // create
    suspend fun insertMeal(newMeal: Meal): Long?
//    suspend fun insertMealDay(mealDayCrossRef: MealDayCrossRef)

    // delete
    suspend fun deleteMeal(meal: Meal)

    // retrieve all meals
    suspend fun getAllMeals(): List<Meal>

    // update
    suspend fun updateMeal(meal: Meal)

//    suspend fun getDaysByMeal(mealId: Int): MealWithDays
//
//    suspend fun getAllMealsWithDays(): List<MealWithDays>
}