package com.mona.adel.quickbite.data.repository

import com.mona.adel.quickbite.data.LocalDataSource
import com.mona.adel.quickbite.data.model.User

class LoginRepoImp(val localDataSource: LocalDataSource): LoginRepo {

    override suspend fun getUserByEmail(email: String, password: String): User? {
        return localDataSource.getUserByEmail(email, password)
    }
}