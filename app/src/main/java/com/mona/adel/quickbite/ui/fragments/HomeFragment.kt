package com.mona.adel.quickbite.ui.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mona.adel.quickbite.data.LocalDataSourceImp
import com.mona.adel.quickbite.data.model.Order
import com.mona.adel.quickbite.data.repository.HomeRepoImp
import com.mona.adel.quickbite.databinding.FragmentHomeBinding
import com.mona.adel.quickbite.ui.adapters.HomeMealsAdapter
import com.mona.adel.quickbite.ui.factories.HomeViewModelFactory
import com.mona.adel.quickbite.ui.viewModels.HomeViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class HomeFragment : Fragment() {

    private val TAG = "HomeFragment"
    private lateinit var binding: FragmentHomeBinding

    private lateinit var homeMealsAdapter: HomeMealsAdapter
    private lateinit var homeViewModel: HomeViewModel

    private lateinit var sharedPreferences: SharedPreferences
    private var currentUserId = -1
    private var orderMealId = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve the current user logged in from the shared preferences
        sharedPreferences = requireActivity().getSharedPreferences("appPref", Context.MODE_PRIVATE)
        currentUserId = sharedPreferences.getInt("userId", -1)


        getViewModelReady()

        homeViewModel.getMealsByDay(getTodayDayName().lowercase().trim())

        // Observe the changes in the meals for today
        homeViewModel.mealsWithDay.observe(requireActivity()){
            if (it != null){
                if (it.isEmpty()){
                    binding.tvNoMeals.visibility = View.VISIBLE

                }else{
                    binding.tvNoMeals.visibility = View.GONE
                    homeMealsAdapter = HomeMealsAdapter{meal -> // the callback from the adapter

                        // check whether the current user can order a meal of not
                        orderMealId = meal.mealId ?: -1
                        checkUserOrder()
                    }
                    homeMealsAdapter.setData(it) // send the list of meals for adapter

                    // Setup the RecyclerView
                    binding.rvMealsHome.adapter = homeMealsAdapter
                    binding.rvMealsHome.layoutManager = LinearLayoutManager(requireContext())
                }

            }else{
                Log.d(TAG, "onViewCreated: the meals for this day is null ")
            }
        }

        // Observe the ability of current user to order for today
        homeViewModel.isUserOrdered.observe(requireActivity()){ isOrdered->
            if (isOrdered!=null){

                if (isOrdered){
                    Toast.makeText(requireContext(), "Sorry, you have already ordered today.", Toast.LENGTH_SHORT).show()

                }else{
                    // Perform the order process
                    insertOrder()
                    Toast.makeText(requireContext(), "The order is confirmed.", Toast.LENGTH_SHORT).show()

                }
            }else{
                Log.d(TAG, "onViewCreated: isOrdered is null")
            }

        }


    }

    private fun insertOrder(){
        if (orderMealId != -1){
            // Create the new order
            val order = Order(userId = currentUserId, mealId = orderMealId, orderDate = getTodayDate())
            homeViewModel.insertOrder(order)

        }else{
            Log.d(TAG, "insertOrder: no value for the meal id")
        }

    }

    private fun checkUserOrder(){
        homeViewModel.checkUserLastOrder(currentUserId, getTodayDate())
    }

    private fun getTodayDayName(): String{
        val calendar = Calendar.getInstance()
        val dayFormat = SimpleDateFormat("EEEE", Locale.getDefault()) // EEEE -> full day name
        return dayFormat.format(calendar.time) // Return the full today's day name
    }

    private fun getTodayDate(): String {
        val calendar = Calendar.getInstance()
        val fullDateFormat = SimpleDateFormat("EEEE,MMMM,dd,yyyy", Locale.getDefault())
        return fullDateFormat.format(calendar.time) // Return today's date in the given format
    }

    private fun getViewModelReady(){
        // Create the viewModel using ViewModelFactory to send a parameters in the viewModel constructor.
        val factory = HomeViewModelFactory(
            HomeRepoImp(
                LocalDataSourceImp(requireContext())
            )
        )

        homeViewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]
    }



}