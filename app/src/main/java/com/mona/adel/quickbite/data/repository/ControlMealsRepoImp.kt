package com.mona.adel.quickbite.data.repository

import com.mona.adel.quickbite.data.LocalDataSource
import com.mona.adel.quickbite.data.LocalDataSourceImp
import com.mona.adel.quickbite.data.model.Meal
import com.mona.adel.quickbite.data.relations.MealDayCrossRef
import com.mona.adel.quickbite.data.relations.MealWithDays

class ControlMealsRepoImp(val localDataSource: LocalDataSource): ControlMealsRepo {

    // create
    override suspend fun insertMeal(newMeal: Meal): Long? {
        return localDataSource.insertMeal(newMeal)
    }

//    override suspend fun insertMealDay(mealDayCrossRef: MealDayCrossRef) {
//        localDataSource.insertMealDay(mealDayCrossRef)
//    }


    // delete
    override suspend fun deleteMeal(meal: Meal) {
        localDataSource.deleteMeal(meal)
    }

    override suspend fun getAllMeals(): List<Meal> {
        return localDataSource.getAllMeals()
    }

    // update
    override suspend fun updateMeal(meal: Meal) {
        localDataSource.updateMeal(meal)
    }

//    override suspend fun getDaysByMeal(mealId: Int): MealWithDays {
//        return localDataSource.getDaysByMeal(mealId)
//    }
//
//    override suspend fun getAllMealsWithDays(): List<MealWithDays> {
//        return localDataSource.getAllMealsWithDays()
//    }


}