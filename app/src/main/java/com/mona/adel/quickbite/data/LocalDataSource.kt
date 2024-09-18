package com.mona.adel.quickbite.data

import com.mona.adel.quickbite.data.model.Meal
import com.mona.adel.quickbite.data.model.Order

interface LocalDataSource {

    suspend fun insertOrder(newOrder: Order)

    suspend fun getAllMealsPerDay(): List<Meal>

}