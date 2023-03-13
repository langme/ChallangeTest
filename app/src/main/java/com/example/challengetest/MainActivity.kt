package com.example.challengetest

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.challengetest.data.AppDatabase
import com.example.challengetest.data.RegisterUser
import com.example.challengetest.repository.UserRepository
import com.example.challengetest.ui.RegisterScreen
import com.example.challengetest.ui.theme.ChallengeTestTheme
import com.example.challengetest.viewmodels.UserRegisterViewModel
import com.example.challengetest.viewmodels.UserRegisterViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChallengeTestTheme {
                RegisterScreen()
            }
        }
    }
}