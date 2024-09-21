package com.mona.adel.quickbite.data

import com.mona.adel.quickbite.data.model.DayOfWeek
import com.mona.adel.quickbite.data.model.Meal
import com.mona.adel.quickbite.data.model.Order
import com.mona.adel.quickbite.data.model.User
import com.mona.adel.quickbite.data.relations.DayWithMeals
import com.mona.adel.quickbite.data.relations.MealDayCrossRef
import com.mona.adel.quickbite.data.relations.MealWithDays

interface LocalDataSource {

    suspend fun insertOrder(newOrder: Order)

    // for home
//    suspend fun getMealsByDay(day: String): DayWithMeals
    suspend fun getMealsByDay(day: String): List<Meal>


    // for user
    suspend fun insertUser(newUser: User)
    suspend fun getUserByEmail(email: String, password: String): User?
    suspend fun getUserById(userId: Int): User?


//    suspend fun insertDay(newDay: DayOfWeek)



    // for create new meals
//    suspend fun insertMealDay(mealDayCrossRef: MealDayCrossRef)
    suspend fun insertMeal(newMeal: Meal): Long?

    // delete
    suspend fun deleteMeal(meal: Meal)

    // retrieve all meals
    suspend fun getAllMeals(): List<Meal>

    // update
    suspend fun updateMeal(meal: Meal)

//    suspend fun getDaysByMeal(mealId: Int): MealWithDays
//
//    suspend fun getAllMealsWithDays(): List<MealWithDays>
}