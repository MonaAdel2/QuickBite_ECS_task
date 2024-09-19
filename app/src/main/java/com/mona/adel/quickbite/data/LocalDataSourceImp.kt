package com.mona.adel.quickbite.data

import android.content.Context
import android.widget.Toast
import com.mona.adel.quickbite.data.dao.DayOfWeekDao
import com.mona.adel.quickbite.data.dao.MealDao
import com.mona.adel.quickbite.data.dao.OrderDao
import com.mona.adel.quickbite.data.dao.UserDao
import com.mona.adel.quickbite.data.model.DayOfWeek
import com.mona.adel.quickbite.data.model.Meal
import com.mona.adel.quickbite.data.model.Order
import com.mona.adel.quickbite.data.model.User

class LocalDataSourceImp(val context: Context): LocalDataSource {

    private val orderDao : OrderDao
//    private val mealsPerDay : OrderDao
    private val mealDao : MealDao
    private val userDao : UserDao
    private val dayOfWeekDao : DayOfWeekDao

    init {
        val database = AppDatabase.getDatabase(context)
        orderDao =database.orderDao()
        mealDao =database.mealDao()
        userDao =database.userDao()
        dayOfWeekDao =database.dayOfWeekDao()
        Toast.makeText(context, "db is created", Toast.LENGTH_SHORT).show()
    }

    override suspend fun insertOrder(newOrder: Order) {
        orderDao.insertOrder(newOrder)
    }

    override suspend fun getAllMealsPerDay(): List<Meal> {
        TODO("Not yet implemented")
    }

    override suspend fun insertMeal(newMeal: Meal) {
        mealDao.insertMeal(newMeal)
    }

    override suspend fun insertUser(newUser: User) {
        userDao.insertUser(newUser)
    }

    override suspend fun getUserByEmail(email: String, password: String): User? {
        return userDao.getUserByEmail(email, password)
    }

    override suspend fun getUserById(userId: Int): User? {
        return userDao.getUserById(userId)
    }

    override suspend fun insertDay(newDay: DayOfWeek) {
        dayOfWeekDao.insertDay(newDay)
    }
}