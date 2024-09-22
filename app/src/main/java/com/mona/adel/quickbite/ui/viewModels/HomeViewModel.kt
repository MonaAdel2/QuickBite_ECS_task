package com.mona.adel.quickbite.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mona.adel.quickbite.data.model.Meal
import com.mona.adel.quickbite.data.model.Order
import com.mona.adel.quickbite.data.repository.HomeRepo
import kotlinx.coroutines.launch

class HomeViewModel(val homeRepo: HomeRepo): ViewModel() {

    private var _mealsWithDay = MutableLiveData<List<Meal>>()
    val mealsWithDay : LiveData<List<Meal>> = _mealsWithDay

    private var _isUserOrdered = MutableLiveData<Boolean>()
    val isUserOrdered : LiveData<Boolean> = _isUserOrdered

    // Retrieve the list of meals for this day
    fun getMealsByDay(day: String){

        viewModelScope.launch {
            _mealsWithDay.value = homeRepo.getMealsByDay(day)
        }
    }

    // Check the user ability to order
    fun checkUserLastOrder(userId: Int, todayOrder: String){
        viewModelScope.launch {
            _isUserOrdered.value = homeRepo.hasUserOrderedToday(userId, todayOrder)
        }
    }

    // Create new order for certain user
    fun insertOrder(order: Order){
        viewModelScope.launch {
            homeRepo.insertOrder(order)
        }
    }

}