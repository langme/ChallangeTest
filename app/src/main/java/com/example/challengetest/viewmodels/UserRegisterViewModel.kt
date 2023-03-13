package com.example.challengetest.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.challengetest.data.AppDatabase
import com.example.challengetest.data.RegisterUser
import kotlinx.coroutines.launch
import com.example.challengetest.repository.UserRepository
import kotlinx.coroutines.Dispatchers


class UserRegisterViewModel (
    application: Application
) : ViewModel(){

    private val userRegisterDao = AppDatabase.getInstance(application).userDao()
    private val userItemRepository = UserRepository(userRegisterDao)

    fun addUser(userItem: RegisterUser) {
        viewModelScope.launch(Dispatchers.IO) {
            userItemRepository.addUserRepo(userItem)
        }
    }
}

class UserRegisterViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(UserRegisterViewModel::class.java)) {
            return UserRegisterViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}