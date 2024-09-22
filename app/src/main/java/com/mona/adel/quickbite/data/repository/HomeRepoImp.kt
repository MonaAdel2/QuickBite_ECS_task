package com.mona.adel.quickbite.data.repository

import com.mona.adel.quickbite.data.LocalDataSource
import com.mona.adel.quickbite.data.model.Meal
import com.mona.adel.quickbite.data.model.Order

class HomeRepoImp(val localDataSource: LocalDataSource): HomeRepo {

    override suspend fun getMealsByDay(day: String): List<Meal> {
       return localDataSource.getMealsByDay(day)
    }

    override suspend fun hasUserOrderedToday(userId: Int, todayDate: String): Boolean {
        return localDataSource.hasUserOrderedToday(userId, todayDate)
    }

    override suspend fun insertOrder(order: Order) {
        localDataSource.insertOrder(order)
    }

}