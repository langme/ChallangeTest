package com.example.challengetest.ui

import android.app.Application
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.example.challengetest.ui.theme.ChallengeTestTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.challengetest.R
import com.example.challengetest.viewmodels.UserRegisterViewModel
import com.example.challengetest.viewmodels.UserRegisterViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.challengetest.component.InputFieldComponent
import com.example.challengetest.data.RegisterUser
import com.example.challengetest.domain.UIEvent
import com.example.challengetest.domain.ValidationEvent

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val userRegisterViewModel: UserRegisterViewModel = viewModel(
        factory = UserRegisterViewModelFactory(
            context.applicationContext as Application
        )
    )
    val state = userRegisterViewModel.uiState.value

    LaunchedEffect(key1 = context) {
        userRegisterViewModel.validationEvent.collect { event ->
            when(event) {
                is ValidationEvent.Success -> {
                    userRegisterViewModel.addUser(
                        RegisterUser(
                            userRegisterViewModel.uiState.value.firstName,
                            userRegisterViewModel.uiState.value.lastName,
                            userRegisterViewModel.uiState.value.email,
                        ))
                    Toast
                        .makeText(context,"All inputs are valid", Toast.LENGTH_SHORT)
                        .show()
                }

                is ValidationEvent.Error -> {
                    Toast
                        .makeText(context, "All inputs are not valid", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    Surface(
        modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column {
            Spacer(modifier = Modifier.height(60.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
            ) {
                // first name extField
                InputFieldComponent(
                    UIEvent.TypeField.FIRST, state.hasFirstNameError
                ) { userRegisterViewModel.onEvent(UIEvent.FirstNameChanged(it))}
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
            ) {
                // last name extField
                InputFieldComponent(
                    UIEvent.TypeField.LAST, state.hasLastNameError
                ) { userRegisterViewModel.onEvent(UIEvent.LastNameChanged(it))}
            }

            Spacer(modifier = Modifier.height(20.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
            ) {
                // email name extField
                InputFieldComponent(
                    UIEvent.TypeField.EMAIL, state.hasEmailError
                ) { userRegisterViewModel.onEvent(UIEvent.EmailChanged(it))}
            }

            Spacer(modifier = Modifier.height(20.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
            ) {
                Button(
                    onClick = {
                        userRegisterViewModel.onEvent(UIEvent.Submit)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(80.dp, 20.dp)
                ) {
                    Text(text = stringResource(id = R.string.register))
                }
            }
        }
    }

}


@Preview
@Composable
private fun RegisterScreenPreview() {
    ChallengeTestTheme {
        //RegisterScreen()
    }
}