package com.mona.adel.quickbite.ui.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.mona.adel.quickbite.R
import com.mona.adel.quickbite.data.LocalDataSourceImp
import com.mona.adel.quickbite.data.model.Meal
import com.mona.adel.quickbite.data.model.User
import com.mona.adel.quickbite.data.repository.LoginRepoImp
import com.mona.adel.quickbite.databinding.FragmentLoginBinding
import com.mona.adel.quickbite.ui.activities.AdminActivity
import com.mona.adel.quickbite.ui.factories.LoginViewModelFactory
import com.mona.adel.quickbite.ui.viewModels.LoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class LoginFragment : Fragment() {
    private val TAG = "LoginFragment"

    private lateinit var binding: FragmentLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getViewModelReady()

        // Observe the user logging state
        loginViewModel.retrievedUser.observe(requireActivity()){user->
            if (user != null){
                binding.tvErrorMessage.visibility = View.GONE
                saveUserToSharedPreference(user.userId) // Save userId in the shared preferences

                // Navigate based on the user's role
                if(user.role.equals("admin")){
                    // navigate to admin activity
                    startActivity(Intent(requireActivity(), AdminActivity::class.java))
                    requireActivity().finish()
                }else {
                    // Navigate to home fragment and clear the back stack
                    val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                    val navOptions = NavOptions.Builder()
                        .setPopUpTo(R.id.loginFragment, true)
                        .build()

                    findNavController().navigate(action, navOptions)
                }
            }else{
                // Error message
                binding.tvErrorMessage.text = "Wrong email or password."
                binding.tvErrorMessage.visibility = View.VISIBLE
                Log.d(TAG, "onViewCreated: the user is null")
            }
        }

        binding.btnLogin.setOnClickListener {

            val email = binding.txtEtEmail.editText?.text.toString()
            val password = binding.txtEtPassword.editText?.text.toString()

            // Check that all fields are filled
            if (email.isNullOrEmpty() || password.isNullOrEmpty()){
                binding.tvErrorMessage.text = "Please fill all the fields."
                binding.tvErrorMessage.visibility = View.VISIBLE
                return@setOnClickListener
            }
            // Check if the user exists
            binding.tvErrorMessage.visibility = View.GONE
            loginViewModel.getUserByEmail(email, password)

        }

        binding.tvInsertDummyData.setOnClickListener {
            insertDummyData() // To insert dummy data to the database
        }


    }

    private fun saveUserToSharedPreference(userId: Int?){
        if(userId != null){
            val sharedPref = requireActivity().getSharedPreferences("appPref", Context.MODE_PRIVATE)
            val editor = sharedPref?.edit()
            editor?.putInt("userId", userId) // Save  the userID
            editor?.apply()
        }
    }


    private fun getViewModelReady(){
        // Create the viewModel using ViewModelFactory to send a parameters in the viewModel constructor.
        val factory = LoginViewModelFactory(
            LoginRepoImp(
                LocalDataSourceImp(requireContext())
            )
        )

        loginViewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]
    }

    private fun insertDummyData() {

        var local = LocalDataSourceImp(requireContext())
        CoroutineScope(Dispatchers.IO).launch {

            // Users
            local.insertUser(
                User(
                    userName = "Mona Adel",
                    email = "admin@gmail.com",
                    password = "admin123",
                    role = "admin"
                )
            )
            local.insertUser(
                User(
                    userName = "Mona Adel",
                    email = "mona@gmail.com",
                    password = "mona123",
                    role = "user"
                )
            )
            local.insertUser(
                User(
                    userName = "Sandy Nader",
                    email = "sandy@gmail.com",
                    password = "sandy123",
                    role = "user"
                )
            )


            // Meals
            local.insertMeal(
                Meal(
                    mealName = "Cheese Burger",
                    category = "Chicken",
                    price = 120.0,
                    days = listOf("sunday", "monday")
                )
            )
            local.insertMeal(
                Meal(
                    mealName = "Grilled Salmon",
                    category = "Seafood",
                    price = 180.0,
                    days = listOf("tuesday", "thursday")
                )
            )
            local.insertMeal(
                Meal(
                    mealName = "Caesar Salad",
                    category = "Salads",
                    price = 80.0,
                    days = listOf("monday", "wednesday", "friday")
                )
            )
            local.insertMeal(
                Meal(
                    mealName = "Spaghetti Bolognese",
                    category = "Pasta",
                    price = 150.0,
                    days = listOf("tuesday", "thursday", "saturday")
                )
            )
            local.insertMeal(
                Meal(
                    mealName = "French Croissant",
                    category = "Bakery",
                    price = 50.0,
                    days = listOf("sunday", "wednesday")
                )
            )
            local.insertMeal(
                Meal(
                    mealName = "Tomato Soup",
                    category = "Soups",
                    price = 70.0,
                    days = listOf("monday", "friday")
                )
            )
            local.insertMeal(
                Meal(
                    mealName = "Chocolate Cake",
                    category = "Desserts",
                    price = 90.0,
                    days = listOf("saturday", "sunday")
                )
            )
            local.insertMeal(
                Meal(
                    mealName = "Mango Smoothie",
                    category = "Drinks",
                    price = 60.0,
                    days = listOf("tuesday", "thursday")
                )
            )
            local.insertMeal(
                Meal(
                    mealName = "Grilled Chicken Wrap",
                    category = "Chicken",
                    price = 110.0,
                    days = listOf("wednesday", "saturday")
                )
            )
            local.insertMeal(
                Meal(
                    mealName = "Shrimp Taco",
                    category = "Seafood",
                    price = 140.0,
                    days = listOf("friday", "sunday")
                )
            )

        }
    }
}