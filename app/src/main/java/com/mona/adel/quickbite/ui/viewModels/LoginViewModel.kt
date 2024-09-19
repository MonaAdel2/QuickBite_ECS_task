package com.mona.adel.quickbite.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mona.adel.quickbite.data.model.User
import com.mona.adel.quickbite.data.repository.LoginRepo
import kotlinx.coroutines.launch

class LoginViewModel(val loginRepo: LoginRepo) : ViewModel() {

    private val _retrievedUser = MutableLiveData<User>()
    val retrievedUser: LiveData<User> = _retrievedUser

    fun getUserByEmail(email: String, password: String){
        viewModelScope.launch {
            _retrievedUser.value = loginRepo.getUserByEmail(email, password)
        }
    }
}