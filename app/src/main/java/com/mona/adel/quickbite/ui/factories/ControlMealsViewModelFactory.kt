package com.mona.adel.quickbite.ui.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mona.adel.quickbite.data.repository.ControlMealsRepo
import com.mona.adel.quickbite.ui.viewModels.ControlMealsViewModel


class ControlMealsViewModelFactory(val repo: ControlMealsRepo): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(ControlMealsViewModel::class.java)){
            ControlMealsViewModel(repo) as T

        }else{
            throw IllegalArgumentException("ControlMealsViewModel class not found")
        }
    }
}