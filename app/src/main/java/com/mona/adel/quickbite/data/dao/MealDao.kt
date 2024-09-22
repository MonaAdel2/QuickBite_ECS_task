package com.mona.adel.quickbite.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.mona.adel.quickbite.data.model.Meal

@Dao
interface MealDao {

    @Insert
    suspend fun insertMeal(newMeal: Meal): Long

    @Delete
    suspend fun deleteMeal(meal: Meal)

    @Query("SELECT * FROM meals")
    suspend fun getAllMeals(): List<Meal>

    @Update
    suspend fun updateMeal(updatedMeal:Meal)

    @Query("SELECT * FROM meals WHERE days LIKE '%' || :day || '%'")
    suspend fun getMealsByDay(day: String): List<Meal>


}