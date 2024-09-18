package com.mona.adel.quickbite.data

import android.content.Context
import com.mona.adel.quickbite.data.dao.OrderDao
import com.mona.adel.quickbite.data.model.Meal
import com.mona.adel.quickbite.data.model.Order

class LocalDataSourceImp(val context: Context): LocalDataSource {

    private val orderDao : OrderDao
//    private val mealsPerDay : OrderDao

    init {
        val database = AppDatabase.getDatabase(context)
        orderDao =database.orderDao()
    }

    override suspend fun insertOrder(newOrder: Order) {
        orderDao.insertOrder(newOrder)
    }

    override suspend fun getAllMealsPerDay(): List<Meal> {
        TODO("Not yet implemented")
    }
}