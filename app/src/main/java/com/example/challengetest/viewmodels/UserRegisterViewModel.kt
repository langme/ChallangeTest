package com.example.challengetest.viewmodels

import android.app.Application
import androidx.compose.runtime.*
import androidx.lifecycle.*
import com.example.challengetest.data.AppDatabase
import com.example.challengetest.domain.UIEvent
import com.example.challengetest.domain.UIState
import com.example.challengetest.domain.ValidationEvent
import com.example.challengetest.domain.Validator
import kotlinx.coroutines.launch
import com.example.challengetest.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import com.example.challengetest.data.RegisterUser as RegisterUser


class UserRegisterViewModel (
    application: Application
) : ViewModel(){

    private val userRegisterDao = AppDatabase.getInstance(application).userDao()
    private val userItemRepository = UserRepository(userRegisterDao)

    private var _uiState = mutableStateOf(UIState())
    val uiState: State<UIState> = _uiState
    val validationEvent = MutableSharedFlow<ValidationEvent>()

    //val users:  LiveData<List<RegisterUser>> = userItemRepository.allUser
    //private val _users = MutableLiveData<MutableList<RegisterUser>>()
    var listUser: LiveData<List<RegisterUser>> = userItemRepository.allUser.asLiveData()


    init {
    }

    fun onEvent(event: UIEvent) {
        when(event) {
            is UIEvent.FirstNameChanged -> {
                _uiState.value = _uiState.value.copy(
                    firstName = event.firstName
                )
            }
            is UIEvent.LastNameChanged -> {
                _uiState.value = _uiState.value.copy(
                    lastName = event.lastName
                )
            }
            is UIEvent.EmailChanged -> {
                _uiState.value = _uiState.value.copy(
                    email = event.email
                )
            }

            is UIEvent.Submit -> {
                validateInputs()
            }

            is UIEvent.AllUsers -> {
                //TODO
            }
        }
    }

    private fun validateInputs() {
        val firstNameResult = Validator.validateFirstName(_uiState.value.firstName)
        val lastNameResult = Validator.validateLastName(_uiState.value.lastName)
        val emailResult = Validator.validateEmail(_uiState.value.email)

        _uiState.value = _uiState.value.copy(
            hasFirstNameError = !firstNameResult.status,
            hasLastNameError = !lastNameResult.status,
            hasEmailError = !emailResult.status,
        )

        val hasError = listOf(
            firstNameResult,
            lastNameResult,
            emailResult
        ).any { !it.status }
        viewModelScope.launch {
            if (!hasError) {
                validationEvent.emit(ValidationEvent.Success)
            } else {
                validationEvent.emit(ValidationEvent.Error)
            }
        }
    }

    fun addUser(user : RegisterUser) {
        viewModelScope.launch(Dispatchers.IO) {
                userItemRepository.addUserRepo(user)
        }
    }

    fun getUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            userItemRepository.getAllUsersRepo()
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