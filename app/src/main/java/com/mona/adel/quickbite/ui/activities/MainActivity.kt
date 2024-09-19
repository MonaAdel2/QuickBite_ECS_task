package com.mona.adel.quickbite.ui.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.mona.adel.quickbite.R
import com.mona.adel.quickbite.data.LocalDataSourceImp
import com.mona.adel.quickbite.data.model.DayOfWeek
import com.mona.adel.quickbite.data.model.Meal
import com.mona.adel.quickbite.data.model.Order
import com.mona.adel.quickbite.data.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


    }

    private fun insertDummyData(){
        val days = listOf("Sat", "Sun", "Mon", "Tues", "Wed", "Thur", "Fri")

        var local = LocalDataSourceImp(this)
        CoroutineScope(Dispatchers.IO).launch {
            for (day in days){
                local.insertDay(DayOfWeek( dayName = day))
            }

            local.insertUser(User(userName="Mona Adel", email="admin@gmail.com", password = "admin123", role = "admin"))
            local.insertUser(User(userName="Noha Ali", email="noha@gmail.com", password = "noha123", role = "user"))
            local.insertUser(User(userName="Ahmed Samir", email="ahmed@gmail.com", password = "ahmed123", role = "user"))
            local.insertUser(User(userName="Sara Mostafa", email="sara@gmail.com", password = "sara123", role = "user"))
            local.insertUser(User(userName="Mohamed Zaki", email="mohamed@gmail.com", password = "mohamed123", role = "user"))
            local.insertUser(User(userName="Fatma Hany", email="fatma@gmail.com", password = "fatma123", role = "user"))

//            local.insertMeal(Meal(2, "Chicken", "mmmmmmmmm", "Chicken"))
//            local.insertOrder(Order(2,1,1,"0000"))

        }
    }
}