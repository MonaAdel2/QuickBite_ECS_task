package com.mona.adel.quickbite.data.repository

import com.mona.adel.quickbite.data.model.User

interface LoginRepo {
    suspend fun getUserByEmail(email: String, password: String): User?
}