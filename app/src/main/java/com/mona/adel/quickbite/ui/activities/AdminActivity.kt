package com.mona.adel.quickbite.ui.activities

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.mona.adel.quickbite.R
import com.mona.adel.quickbite.databinding.ActivityAdminBinding
import com.mona.adel.quickbite.databinding.ActivityMainBinding

class AdminActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mainNavHostFragment =
            supportFragmentManager.findFragmentById(R.id.admin_nav_host) as NavHostFragment
        navController = mainNavHostFragment.navController



        binding.bottomNavigationAdmin.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.home -> {
                    navController.navigate(R.id.homeFragment2)
                    true
                }
                R.id.cart -> {
//                    navController.navigate(R.id.CartFragment)
                    true
                }
                R.id.edit -> {
                    navController.navigate(R.id.controlMealsFragment)
                    true
                }

                else -> false

            }
        }

    }

    fun hideBottomNavigationView() {
        binding.bottomNavigationAdmin.visibility = View.GONE
    }

    fun showBottomNavigationView() {
        binding.bottomNavigationAdmin.visibility = View.VISIBLE
    }
}