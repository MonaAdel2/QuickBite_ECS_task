package com.mona.adel.quickbite.data.repository

import com.mona.adel.quickbite.data.model.Meal
import com.mona.adel.quickbite.data.model.Order

interface HomeRepo {

    suspend fun getMealsByDay(day: String): List<Meal>

    suspend fun hasUserOrderedToday(userId: Int, todayDate: String): Boolean

    suspend fun insertOrder(order: Order)

}