package com.mona.adel.quickbite.ui.activities

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.mona.adel.quickbite.R
import com.mona.adel.quickbite.databinding.ActivityAdminBinding

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



        // Navigation between the admin fragments
        binding.bottomNavigationAdmin.setOnItemSelectedListener { item ->

            // To remove the current fragment from the backstack
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.admin_nav_graph, true)
                .build()

            when (item.itemId) {
                R.id.home -> {
                    navController.navigate(R.id.homeFragment2, null, navOptions)
                    true
                }

                R.id.edit -> {
                    navController.navigate(R.id.controlMealsFragment, null, navOptions)
                    true
                }

                else -> false

            }
        }

    }

}