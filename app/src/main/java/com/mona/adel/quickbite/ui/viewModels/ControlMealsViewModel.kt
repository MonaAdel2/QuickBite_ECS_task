package com.mona.adel.quickbite.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mona.adel.quickbite.data.model.Meal
import com.mona.adel.quickbite.data.repository.ControlMealsRepo
import kotlinx.coroutines.launch

class ControlMealsViewModel(private val controlMealsRepo: ControlMealsRepo): ViewModel() {

    private var _meals = MutableLiveData<List<Meal>>()
    val meals : LiveData<List<Meal>> = _meals

    // Create
    fun insertMeal(newMeal: Meal){
        viewModelScope.launch {
            controlMealsRepo.insertMeal(newMeal)
        }

    }
    // Update
    fun updateMeal(meal: Meal){
        viewModelScope.launch {
            controlMealsRepo.updateMeal(meal)
        }
    }

    // Delete
    fun deleteMeal(meal: Meal){
        viewModelScope.launch {
            controlMealsRepo.deleteMeal(meal)
        }
    }

    // Read
    fun getAllMeals(){
        viewModelScope.launch {
            _meals.value = controlMealsRepo.getAllMeals() // Set the value of meals to be observed from the fragment
        }
    }

}