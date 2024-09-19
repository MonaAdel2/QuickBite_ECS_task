package com.mona.adel.quickbite.data

import com.mona.adel.quickbite.data.model.DayOfWeek
import com.mona.adel.quickbite.data.model.Meal
import com.mona.adel.quickbite.data.model.Order
import com.mona.adel.quickbite.data.model.User

interface LocalDataSource {

    suspend fun insertOrder(newOrder: Order)

    suspend fun getAllMealsPerDay(): List<Meal>

    suspend fun insertMeal(newMeal: Meal)

    suspend fun insertUser(newUser: User)

    suspend fun getUserByEmail(email: String, password: String): User?

    suspend fun getUserById(userId: Int): User?

    suspend fun insertDay(newDay: DayOfWeek)

}