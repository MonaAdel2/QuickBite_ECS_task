package com.mona.adel.quickbite.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mona.adel.quickbite.data.dao.MealDao
import com.mona.adel.quickbite.data.dao.OrderDao
import com.mona.adel.quickbite.data.dao.UserDao
import com.mona.adel.quickbite.data.model.Meal
import com.mona.adel.quickbite.data.model.Order
import com.mona.adel.quickbite.data.model.User

@Database(
    entities = [User::class, Meal::class, Order::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun orderDao(): OrderDao
    abstract fun mealDao(): MealDao

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