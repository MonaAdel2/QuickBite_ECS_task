package com.mona.adel.quickbite.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import com.mona.adel.quickbite.data.model.Meal

@Dao
interface MealDao {

    @Insert
    suspend fun insertMeal(newMeal: Meal)

    @Delete
    suspend fun deleteMeal(meal: Meal)


}