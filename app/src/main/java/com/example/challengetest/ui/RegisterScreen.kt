package com.example.challengetest.ui

import android.app.Application
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview
import com.example.challengetest.ui.theme.ChallengeTestTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
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
    val userList: List<RegisterUser> by userRegisterViewModel.listUser.observeAsState(initial = listOf())

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
                    userRegisterViewModel.getUsers()
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
            Spacer(modifier = Modifier.height(20.dp))
            LazyColumn(
                Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                item {
                    TitleRow(head1 = "ID", head2 = "Name", head3 = "Last", head4 = "email")
                }

                items(userList) { user ->
                    ProductRow(id = user.idUser,
                        firstName = user.firstName,
                        lastName = user.lastName,
                        email = user.emailUser
                    )
                }
            }
        }
    }

}

@Composable
fun TitleRow(head1: String, head2: String, head3: String, head4: String) {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colors.primary)
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Text(head1, color = Color.White,
            modifier = Modifier
                .weight(0.1f))
        Text(head2, color = Color.White,
            modifier = Modifier
                .weight(0.2f))
        Text(head3, color = Color.White,
            modifier = Modifier.weight(0.2f))
        Text(head4, color = Color.White,
            modifier = Modifier.weight(0.2f))
    }
}

@Composable
fun ProductRow(id: Int, firstName: String, lastName: String, email: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Text(id.toString(), modifier = Modifier
            .weight(0.1f))
        Text(firstName, modifier = Modifier.weight(0.2f))
        Text(lastName, modifier = Modifier.weight(0.2f))
        Text(email, modifier = Modifier.weight(0.2f))
    }
}


@Preview
@Composable
private fun RegisterScreenPreview() {
    ChallengeTestTheme {
        //RegisterScreen()
    }
}