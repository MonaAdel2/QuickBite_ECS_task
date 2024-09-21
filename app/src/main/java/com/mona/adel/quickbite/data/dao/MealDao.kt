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

//    @Query("SELECT * FROM day_of_week WHERE dayName = :day")
//    suspend fun getMealsByDay(day: String): DayWithMeals

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

    @Query("SELECT * FROM meals WHERE " +
            "(:day = 'saturday' AND saturday = 1) OR " +
            "(:day = 'sunday' AND sunday = 1) OR " +
            "(:day = 'sonday' AND monday = 1) OR " +
            "(:day = 'tuesday' AND tuesday = 1) OR " +
            "(:day = 'wednesday' AND wednesday = 1) OR " +
            "(:day = 'thursday' AND thursday = 1) OR " +
            "(:day = 'friday' AND friday = 1)")
    suspend fun getMealsByDay(day: String): List<Meal>



}