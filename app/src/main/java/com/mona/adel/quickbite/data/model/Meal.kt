package com.mona.adel.quickbite.data.model

import android.hardware.biometrics.BiometricManager.Strings
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meals")
data class Meal(
    @PrimaryKey
    var mealId:Int? =null,
    var mealName: String?,
    var mealDescription: String?,
    var category: String?
)
