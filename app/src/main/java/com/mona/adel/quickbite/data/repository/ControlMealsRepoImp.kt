package com.mona.adel.quickbite.data.repository

import com.mona.adel.quickbite.data.LocalDataSource
import com.mona.adel.quickbite.data.model.Meal


class ControlMealsRepoImp(val localDataSource: LocalDataSource): ControlMealsRepo {

    // Create
    override suspend fun insertMeal(newMeal: Meal): Long? {
        return localDataSource.insertMeal(newMeal)
    }
    // Read
    override suspend fun getAllMeals(): List<Meal> {
        return localDataSource.getAllMeals()
    }

    // Update
    override suspend fun updateMeal(meal: Meal) {
        localDataSource.updateMeal(meal)
    }

    // Delete
    override suspend fun deleteMeal(meal: Meal) {
        localDataSource.deleteMeal(meal)
    }

}