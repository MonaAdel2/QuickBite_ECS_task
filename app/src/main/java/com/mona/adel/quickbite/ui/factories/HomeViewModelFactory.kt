package com.mona.adel.quickbite.ui.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mona.adel.quickbite.data.repository.HomeRepo
import com.mona.adel.quickbite.data.repository.LoginRepo
import com.mona.adel.quickbite.ui.viewModels.HomeViewModel
import com.mona.adel.quickbite.ui.viewModels.LoginViewModel


class HomeViewModelFactory(val repo: HomeRepo): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(HomeViewModel::class.java)){
            HomeViewModel(repo) as T

        }else{
            throw IllegalArgumentException("HomeViewModel class not found")
        }
    }
}