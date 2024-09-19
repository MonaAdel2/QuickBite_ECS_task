package com.mona.adel.quickbite.ui.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mona.adel.quickbite.data.repository.LoginRepo
import com.mona.adel.quickbite.ui.viewModels.LoginViewModel


class LoginViewModelFactory(val repo: LoginRepo): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(LoginViewModel::class.java)){
            LoginViewModel(repo) as T

        }else{
            throw IllegalArgumentException("LoginViewModel class not found")
        }
    }
}