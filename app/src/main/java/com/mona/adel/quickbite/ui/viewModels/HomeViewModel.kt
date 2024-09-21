package com.mona.adel.quickbite.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mona.adel.quickbite.data.model.DayOfWeek
import com.mona.adel.quickbite.data.model.Meal
import com.mona.adel.quickbite.data.relations.DayWithMeals
import com.mona.adel.quickbite.data.repository.HomeRepo
import kotlinx.coroutines.launch

class HomeViewModel(val homeRepo: HomeRepo): ViewModel() {

    private var _mealsWithDay = MutableLiveData<List<Meal>>()
    val mealsWithDay : LiveData<List<Meal>> = _mealsWithDay

//    private var _mealsWithDay = MutableLiveData<DayWithMeals>()
//    val mealsWithDay : LiveData<DayWithMeals> = _mealsWithDay

//    fun getMealsByDay(day: String){
//
//        viewModelScope.launch {
//            _mealsWithDay.value = homeRepo.getMealsByDay(day)
//        }
//
//
//    }

    fun getMealsByDay(day: String){

        viewModelScope.launch {
            _mealsWithDay.value = homeRepo.getMealsByDay(day)
        }


    }


}