package com.mona.adel.quickbite.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mona.adel.quickbite.data.model.Meal
import com.mona.adel.quickbite.data.relations.MealDayCrossRef
import com.mona.adel.quickbite.data.repository.ControlMealsRepo
import kotlinx.coroutines.launch

class ControlMealsViewModel(val controlMealsRepo: ControlMealsRepo): ViewModel() {

    private var _newMeal = MutableLiveData<Long>()
    val newMeal : LiveData<Long> = _newMeal


    // create
    fun insertMeal(newMeal: Meal){
        viewModelScope.launch {
            _newMeal.value = controlMealsRepo.insertMeal(newMeal)
        }

    }
    fun insertDayOfNewMeal(mealDayCrossRef: MealDayCrossRef){
        viewModelScope.launch {
            controlMealsRepo.insertMealDay(mealDayCrossRef)
        }
    }

    // update

    // delete

}