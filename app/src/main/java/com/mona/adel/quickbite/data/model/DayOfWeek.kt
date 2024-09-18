package com.mona.adel.quickbite.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "day_of_week")
data class DayOfWeek(
    @PrimaryKey(autoGenerate = true)
    var dayId: Int,
    var dayName: String
)