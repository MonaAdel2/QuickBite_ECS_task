package com.mona.adel.quickbite.data.repository

import com.mona.adel.quickbite.data.LocalDataSource
import com.mona.adel.quickbite.data.model.Meal

class HomeRepoImp(val localDataSource: LocalDataSource): HomeRepo {
    override suspend fun getAllMealsPerDay(): List<Meal> {
        return localDataSource.getAllMealsPerDay()
    }
}