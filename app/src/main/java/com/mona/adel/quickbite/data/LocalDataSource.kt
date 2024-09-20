package com.mona.adel.quickbite.data

import com.mona.adel.quickbite.data.model.DayOfWeek
import com.mona.adel.quickbite.data.model.Meal
import com.mona.adel.quickbite.data.model.Order
import com.mona.adel.quickbite.data.model.User
import com.mona.adel.quickbite.data.relations.DayWithMeals
import com.mona.adel.quickbite.data.relations.MealDayCrossRef

interface LocalDataSource {

    suspend fun insertOrder(newOrder: Order)

    suspend fun getAllMealsPerDay(): List<Meal>

    suspend fun insertMeal(newMeal: Meal): Long?

    suspend fun insertUser(newUser: User)

    suspend fun getUserByEmail(email: String, password: String): User?

    suspend fun getUserById(userId: Int): User?

    suspend fun insertDay(newDay: DayOfWeek)

    suspend fun getMealsByDay(day: String): DayWithMeals

    suspend fun insertMealDay(mealDayCrossRef: MealDayCrossRef)

}