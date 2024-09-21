package com.mona.adel.quickbite.data.model

import android.hardware.biometrics.BiometricManager.Strings
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meals")
data class Meal(
    @PrimaryKey
    var mealId:Int? =null,
    var mealName: String?,
    var category: String?,
    var price: Double?,
    var lastOrderDay : String? = null,
    var saturday: Boolean = false,
    var sunday: Boolean = false,
    var monday: Boolean = false,
    var tuesday: Boolean = false,
    var wednesday: Boolean = false,
    var thursday: Boolean = false,
    var friday: Boolean = false,
)
