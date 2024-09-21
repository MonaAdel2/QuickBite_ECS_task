package com.mona.adel.quickbite.data.repository

import com.mona.adel.quickbite.data.LocalDataSource
import com.mona.adel.quickbite.data.model.Meal
import com.mona.adel.quickbite.data.relations.DayWithMeals

class HomeRepoImp(val localDataSource: LocalDataSource): HomeRepo {

    //    override suspend fun getMealsByDay(day: String): DayWithMeals {
//        return localDataSource.getMealsByDay(day)
//    }
    override suspend fun getMealsByDay(day: String): List<Meal> {
       return localDataSource.getMealsByDay(day)
    }

}