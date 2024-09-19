package com.mona.adel.quickbite.data.dao

import androidx.room.Dao
import androidx.room.Insert
import com.mona.adel.quickbite.data.model.DayOfWeek

@Dao
interface DayOfWeekDao {

    @Insert
    suspend fun insertDay(newDay: DayOfWeek)
}