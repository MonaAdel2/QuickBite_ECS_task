package com.mona.adel.quickbite.data.repository

import com.mona.adel.quickbite.data.LocalDataSource
import com.mona.adel.quickbite.data.LocalDataSourceImp
import com.mona.adel.quickbite.data.model.Meal
import com.mona.adel.quickbite.data.relations.MealDayCrossRef

class ControlMealsRepoImp(val localDataSource: LocalDataSource): ControlMealsRepo {

    override suspend fun insertMeal(newMeal: Meal): Long? {
        return localDataSource.insertMeal(newMeal)
    }

    override suspend fun insertMealDay(mealDayCrossRef: MealDayCrossRef) {
        localDataSource.insertMealDay(mealDayCrossRef)
    }

}