package com.mona.adel.quickbite.ui.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.mona.adel.quickbite.R
import com.mona.adel.quickbite.databinding.ActivityMainBinding


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

    }

}