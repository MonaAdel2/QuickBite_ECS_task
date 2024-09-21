package com.mona.adel.quickbite.ui.viewModels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mona.adel.quickbite.data.model.DayOfWeek
import com.mona.adel.quickbite.data.model.Meal
import com.mona.adel.quickbite.data.relations.MealDayCrossRef
import com.mona.adel.quickbite.data.relations.MealWithDays
import com.mona.adel.quickbite.data.repository.ControlMealsRepo
import kotlinx.coroutines.launch

class ControlMealsViewModel(val controlMealsRepo: ControlMealsRepo): ViewModel() {

    private var _newMeal = MutableLiveData<Long>()
    val newMeal : LiveData<Long> = _newMeal

    private var _meals = MutableLiveData<List<Meal>>()
    val meals : LiveData<List<Meal>> = _meals

    private var _days = MutableLiveData<MealWithDays>()
    val days : LiveData<MealWithDays> = _days

//    private var _allMealsWithDays = MutableLiveData<List<MealWithDays>>()
//    val allMealsWithDays : LiveData<List<MealWithDays>> = _allMealsWithDays

    // create
    fun insertMeal(newMeal: Meal){
        viewModelScope.launch {
            _newMeal.value = controlMealsRepo.insertMeal(newMeal)
        }

    }
//    fun insertDayOfNewMeal(mealDayCrossRef: MealDayCrossRef){
//        viewModelScope.launch {
//            controlMealsRepo.insertMealDay(mealDayCrossRef)
//        }
//    }

    // update

    fun updateMeal(meal: Meal){
        viewModelScope.launch {
            controlMealsRepo.updateMeal(meal)
        }
    }

    // delete
    fun deleteMeal(meal: Meal){
        viewModelScope.launch {
            controlMealsRepo.deleteMeal(meal)
        }
    }

    // retrieve
    fun getAllMeals(){
        viewModelScope.launch {
            _meals.value = controlMealsRepo.getAllMeals()
        }
    }

//    fun getDaysByMeal(mealId: Int){
//        viewModelScope.launch {
//            _days.value = controlMealsRepo.getDaysByMeal(mealId)
//        }
//
//    }
//
//    fun getALlMealsAndDays(){
//       viewModelScope.launch {
//            _allMealsWithDays.value = controlMealsRepo.getAllMealsWithDays()
//       }
//    }

}