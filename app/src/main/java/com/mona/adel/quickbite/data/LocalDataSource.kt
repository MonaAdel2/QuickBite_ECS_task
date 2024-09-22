package com.mona.adel.quickbite.data

import com.mona.adel.quickbite.data.model.Meal
import com.mona.adel.quickbite.data.model.Order
import com.mona.adel.quickbite.data.model.User


interface LocalDataSource {

    // Order
    suspend fun insertOrder(newOrder: Order)

    // Home
    suspend fun getMealsByDay(day: String): List<Meal>

    // User
    suspend fun insertUser(newUser: User)
    suspend fun getUserByEmail(email: String, password: String): User?
    suspend fun getUserById(userId: Int): User?

    suspend fun hasUserOrderedToday(userId: Int, todayDate: String): Boolean

    // --------- CRUD Admin Functions: ---------

    // 1) Create new meal
    suspend fun insertMeal(newMeal: Meal): Long?

    // 2) Read all meals
    suspend fun getAllMeals(): List<Meal>

    // 3) Update existing meal
    suspend fun updateMeal(meal: Meal)

    // 4) Delete certain meal
    suspend fun deleteMeal(meal: Meal)



}