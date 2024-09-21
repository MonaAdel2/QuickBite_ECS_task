package com.mona.adel.quickbite.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.mona.adel.quickbite.data.model.Meal
import com.mona.adel.quickbite.data.relations.DayWithMeals
import com.mona.adel.quickbite.data.relations.MealDayCrossRef
import com.mona.adel.quickbite.data.relations.MealWithDays

@Dao
interface MealDao {

    @Insert
    suspend fun insertMeal(newMeal: Meal): Long

    @Delete
    suspend fun deleteMeal(meal: Meal)

    @Query("SELECT * FROM day_of_week WHERE dayName = :day")
    suspend fun getMealsByDay(day: String): DayWithMeals

    @Query("SELECT * FROM meals WHERE mealId = :mealId")
    suspend fun getDaysByMeal(mealId: Int): MealWithDays

    @Query("SELECT * FROM meals")
    suspend fun getAllMeals(): List<Meal>

    @Insert
    suspend fun insertDayMeal(mealDayCrossRef: MealDayCrossRef)


    @Update
    suspend fun updateMeal(updatedMeal:Meal)

    @Query("SELECT * FROM meals")
    suspend fun getAllMealsWithDays(): List<MealWithDays>




}