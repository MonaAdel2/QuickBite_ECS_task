package com.mona.adel.quickbite.data

import android.content.Context
import com.mona.adel.quickbite.data.dao.MealDao
import com.mona.adel.quickbite.data.dao.OrderDao
import com.mona.adel.quickbite.data.dao.UserDao
import com.mona.adel.quickbite.data.model.Meal
import com.mona.adel.quickbite.data.model.Order
import com.mona.adel.quickbite.data.model.User

class LocalDataSourceImp(val context: Context): LocalDataSource {

    private val orderDao : OrderDao
    private val mealDao : MealDao
    private val userDao : UserDao

    init {
        val database = AppDatabase.getDatabase(context)
        orderDao =database.orderDao()
        mealDao =database.mealDao()
        userDao =database.userDao()
    }

    // Order
    override suspend fun insertOrder(newOrder: Order) {
        orderDao.insertOrder(newOrder)
    }


    // User
    override suspend fun insertUser(newUser: User) {
        userDao.insertUser(newUser)
    }

    override suspend fun getUserByEmail(email: String, password: String): User? {
        return userDao.getUserByEmail(email, password)
    }

    override suspend fun getUserById(userId: Int): User? {
        return userDao.getUserById(userId)
    }

    override suspend fun hasUserOrderedToday(userId: Int, todayDate: String): Boolean {
        return orderDao.hasUserOrderedToday(userId, todayDate)
    }


    // Home
    override suspend fun getMealsByDay(day: String): List<Meal> {
        return mealDao.getMealsByDay(day)
    }


    // --------- CRUD Admin Functions: ---------

    // 1) Create new meal
    override suspend fun insertMeal(newMeal: Meal): Long? {
        return mealDao.insertMeal(newMeal)
    }

    // 2) Read all meals
    override suspend fun getAllMeals(): List<Meal> {
        return mealDao.getAllMeals()
    }

    // 3) Update existing meal
    override suspend fun updateMeal(meal: Meal) {
        mealDao.updateMeal(meal)
    }

    // 4) Delete certain meal
    override suspend fun deleteMeal(meal: Meal) {
        mealDao.deleteMeal(meal)
    }



}