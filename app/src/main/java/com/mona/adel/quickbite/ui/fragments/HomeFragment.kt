package com.mona.adel.quickbite.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.mona.adel.quickbite.R
import com.mona.adel.quickbite.data.LocalDataSourceImp
import com.mona.adel.quickbite.data.repository.HomeRepoImp
import com.mona.adel.quickbite.data.repository.LoginRepoImp
import com.mona.adel.quickbite.databinding.FragmentHomeBinding
import com.mona.adel.quickbite.ui.activities.MainActivity
import com.mona.adel.quickbite.ui.adapters.HomeMealsAdapter
import com.mona.adel.quickbite.ui.factories.HomeViewModelFactory
import com.mona.adel.quickbite.ui.factories.LoginViewModelFactory
import com.mona.adel.quickbite.ui.viewModels.HomeViewModel
import com.mona.adel.quickbite.ui.viewModels.LoginViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Locale


class HomeFragment : Fragment() {

    private val TAG = "HomeFragment"
    private lateinit var binding: FragmentHomeBinding

    private lateinit var homeMealsAdapter: HomeMealsAdapter
    private lateinit var homeViewModel: HomeViewModel

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

        (activity as? MainActivity)?.showBottomNavigationView()

        getViewModelReady()

        homeViewModel.getMealsByDay(getTodayDayName().lowercase().trim())

        homeViewModel.mealsWithDay.observe(requireActivity()){it->
            if (it != null){
                Log.d(TAG, "onViewCreated: the meals for today are: ${it.meals}")
                // set the adapter and recyclerview
                homeMealsAdapter = HomeMealsAdapter()
                homeMealsAdapter.setData(it.meals)

                binding.rvMealsHome.adapter = homeMealsAdapter
                binding.rvMealsHome.layoutManager = LinearLayoutManager(requireContext())

            }else{
                Log.d(TAG, "onViewCreated: the meals for this day is null ")
            }
        }



    }

    private fun getTodayDayName(): String{
        val calendar = Calendar.getInstance()
        val dayFormat = SimpleDateFormat("EEEE", Locale.getDefault()) // "EEEE" gives full day name
        return dayFormat.format(calendar.time)
    }

    private fun getViewModelReady(){

        val factory = HomeViewModelFactory(
            HomeRepoImp(
                LocalDataSourceImp(requireContext())
            )
        )

        homeViewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]
    }



}