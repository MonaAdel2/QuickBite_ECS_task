package com.mona.adel.quickbite.data.repository

import com.mona.adel.quickbite.data.model.Meal

interface ControlMealsRepo {

    // Create
    suspend fun insertMeal(newMeal: Meal): Long?

    // Read
    suspend fun getAllMeals(): List<Meal>

    // Update
    suspend fun updateMeal(meal: Meal)

    // Delete
    suspend fun deleteMeal(meal: Meal)

}