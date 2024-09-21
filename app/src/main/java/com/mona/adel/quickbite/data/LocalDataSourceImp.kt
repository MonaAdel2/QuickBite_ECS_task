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
import com.mona.adel.quickbite.data.relations.DayWithMeals
import com.mona.adel.quickbite.data.relations.MealDayCrossRef
import com.mona.adel.quickbite.data.relations.MealWithDays

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




//    override suspend fun insertDay(newDay: DayOfWeek) {
//        dayOfWeekDao.insertDay(newDay)
//    }



    // for user
    override suspend fun insertUser(newUser: User) {
        userDao.insertUser(newUser)
    }

    override suspend fun getUserByEmail(email: String, password: String): User? {
        return userDao.getUserByEmail(email, password)
    }

    override suspend fun getUserById(userId: Int): User? {
        return userDao.getUserById(userId)
    }


    // for home
//    override suspend fun getMealsByDay(day: String): DayWithMeals {
//        return mealDao.getMealsByDay(day)
//    }

    override suspend fun getMealsByDay(day: String): List<Meal> {
        return mealDao.getMealsByDay(day)
    }


    // for new meals
//    override suspend fun insertMealDay(mealDayCrossRef: MealDayCrossRef) {
//        mealDao.insertDayMeal(mealDayCrossRef)
//    }
    override suspend fun insertMeal(newMeal: Meal): Long? {
        return mealDao.insertMeal(newMeal)
    }


    // delete meals
    override suspend fun deleteMeal(meal: Meal) {
        mealDao.deleteMeal(meal)
    }

    // retreive all meals
    override suspend fun getAllMeals(): List<Meal> {
        return mealDao.getAllMeals()
    }

    // update
    override suspend fun updateMeal(meal: Meal) {
        mealDao.updateMeal(meal)
    }

//    override suspend fun getDaysByMeal(mealId: Int): MealWithDays {
//        return mealDao.getDaysByMeal(mealId)
//    }
//
//    override suspend fun getAllMealsWithDays(): List<MealWithDays> {
//        return mealDao.getAllMealsWithDays()
//    }

}