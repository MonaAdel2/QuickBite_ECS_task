package com.mona.adel.quickbite.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    var userId:Int? =null,
    var userName: String?,
    var email: String?,
    var password: String?,
    var role: String?,
    val lastOrderId: Int? = null
)
