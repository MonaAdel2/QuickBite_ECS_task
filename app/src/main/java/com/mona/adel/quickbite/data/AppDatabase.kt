package com.mona.adel.quickbite.data

import android.content.Context
import androidx.room.RoomDatabase
import androidx.room.Database
import androidx.room.Room
import com.mona.adel.quickbite.data.dao.DayOfWeekDao
import com.mona.adel.quickbite.data.dao.MealDao
import com.mona.adel.quickbite.data.dao.OrderDao
import com.mona.adel.quickbite.data.dao.UserDao
import com.mona.adel.quickbite.data.model.DayOfWeek
import com.mona.adel.quickbite.data.model.Meal
import com.mona.adel.quickbite.data.model.MealDayCrossRef
import com.mona.adel.quickbite.data.model.Order
import com.mona.adel.quickbite.data.model.User

@Database(
    entities = [User::class, Meal::class, DayOfWeek::class, MealDayCrossRef::class, Order::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun orderDao(): OrderDao
    abstract fun mealDao(): MealDao
    abstract fun dayOfWeekDao(): DayOfWeekDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "meal_ordering_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}