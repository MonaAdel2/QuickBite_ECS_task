package com.mona.adel.quickbite.ui.activities

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.mona.adel.quickbite.R
import com.mona.adel.quickbite.data.LocalDataSourceImp
import com.mona.adel.quickbite.data.model.DayOfWeek
import com.mona.adel.quickbite.data.model.Meal
import com.mona.adel.quickbite.data.model.Order
import com.mona.adel.quickbite.data.model.User
import com.mona.adel.quickbite.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val mainNavHostFragment =
            supportFragmentManager.findFragmentById(R.id.app_nav_host) as NavHostFragment
        navController = mainNavHostFragment.navController


//        insertDummyData()

        binding.bottomNavigationMain.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.home -> {
                    navController.navigate(R.id.homeFragment)
                    true
                }
                R.id.cart -> {
//                    navController.navigate(R.id.CartFragment)
                    true
                }

                else -> false

            }
        }
    }

    private fun insertDummyData(){
        val days = listOf("saturday", "sunday", "monday", "tuesday", "wednesday", "thursday", "friday")

        var local = LocalDataSourceImp(this)
        CoroutineScope(Dispatchers.IO).launch {
            for (day in days){
                local.insertDay(DayOfWeek( dayName = day.lowercase().trim()))
            }

            local.insertUser(User(userName="Mona Adel", email="admin@gmail.com", password = "admin123", role = "admin"))
            local.insertUser(User(userName="Noha Ali", email="noha@gmail.com", password = "noha123", role = "user"))
            local.insertUser(User(userName="Ahmed Samir", email="ahmed@gmail.com", password = "ahmed123", role = "user"))
            local.insertUser(User(userName="Sara Mostafa", email="sara@gmail.com", password = "sara123", role = "user"))
            local.insertUser(User(userName="Mohamed Zaki", email="mohamed@gmail.com", password = "mohamed123", role = "user"))
            local.insertUser(User(userName="Fatma Hany", email="fatma@gmail.com", password = "fatma123", role = "user"))

            local.insertMeal(Meal(mealName = "Chicken Burger", category = "Chicken", price = 20.0))


        }
    }

    fun hideBottomNavigationView() {
        binding.bottomNavigationMain.visibility = View.GONE
    }

    fun showBottomNavigationView() {
        binding.bottomNavigationMain.visibility = View.VISIBLE
    }
}