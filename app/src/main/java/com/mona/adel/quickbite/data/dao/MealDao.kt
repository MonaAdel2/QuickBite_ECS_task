package com.mona.adel.quickbite.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.mona.adel.quickbite.data.model.Meal
import com.mona.adel.quickbite.data.relations.DayWithMeals
import com.mona.adel.quickbite.data.relations.MealDayCrossRef

@Dao
interface MealDao {

    @Insert
    suspend fun insertMeal(newMeal: Meal): Long

    @Delete
    suspend fun deleteMeal(meal: Meal)

    @Query("SELECT * FROM day_of_week WHERE dayName = :day")
    suspend fun getMealsByDay(day: String): DayWithMeals

    @Insert
    suspend fun insertDayMeal(mealDayCrossRef: MealDayCrossRef)


}