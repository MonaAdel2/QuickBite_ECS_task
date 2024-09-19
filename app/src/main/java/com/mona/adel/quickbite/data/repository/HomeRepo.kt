package com.mona.adel.quickbite.data.repository

import com.mona.adel.quickbite.data.model.Meal

interface HomeRepo {

    suspend fun getAllMealsPerDay(): List<Meal>
}