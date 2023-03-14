package com.example.challengetest.viewmodels

import android.app.Application
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import com.example.challengetest.R
import com.example.challengetest.data.AppDatabase
import kotlinx.coroutines.launch
import com.example.challengetest.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import com.example.challengetest.data.RegisterUser as RegisterUser


class UserRegisterViewModel (
    application: Application,
    context : Context
) : ViewModel(){
    private val userRegisterDao = AppDatabase.getInstance(application).userDao()
    private val userItemRepository = UserRepository(userRegisterDao)

    var firstName = MutableStateFlow(context.resources.getString(R.string.firstName))
    var lastName = MutableStateFlow(context.resources.getString(R.string.lastName))
    var email = MutableStateFlow(context.resources.getString(R.string.email))
    var user = RegisterUser(firstName.value, lastName.value, email.value)
    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

    init {

    }

    fun addUser() {
        viewModelScope.launch(Dispatchers.IO) {
            user.firstName = firstName.value
            user.lastName = lastName.value
            user.emailUser = email.value

            if (!user.isNotValid)
                userItemRepository.addUserRepo(user)
            //else
                //state.error
        }
    }

    fun sendMessage(message: String) {
        viewModelScope.launch {
            _toastMessage.emit(message)
        }
    }
}

class UserRegisterViewModelFactory(
    private val application: Application,
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(UserRegisterViewModel::class.java)) {
            return UserRegisterViewModel(application, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}