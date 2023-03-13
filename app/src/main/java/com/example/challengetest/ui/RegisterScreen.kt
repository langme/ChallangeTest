package com.example.challengetest.ui

import android.app.Application
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.example.challengetest.ui.theme.ChallengeTestTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.challengetest.R
import com.example.challengetest.data.RegisterUser
import com.example.challengetest.viewmodels.UserRegisterViewModel
import com.example.challengetest.viewmodels.UserRegisterViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.challengetest.component.InputFieldComponent
import kotlin.coroutines.coroutineContext

enum class TypeField {
    FIRST, LAST , EMAIL
}

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier
) {
    val  context = LocalContext.current
    var userRegisterViewModel: UserRegisterViewModel = viewModel(
        factory = UserRegisterViewModelFactory(
            context.applicationContext as Application
        )
    )

    var firstName by remember { mutableStateOf(context.resources.getString(R.string.firstName)) }
    var lastName by remember { mutableStateOf(context.resources.getString(R.string.lastName))}
    var email by remember { mutableStateOf(context.resources.getString(R.string.email))}


    fun onFirstNameTextChange(_firstName : String){
        firstName  = _firstName
    }
    fun onLastNameTextChange(_lastName : String){
        lastName = _lastName
    }
    fun onEmailNameTextChange(_emailName : String){
        email = _emailName
    }

    Surface(
        modifier.fillMaxSize(),
        color = MaterialTheme.colors.background) {
        Column() {
            Spacer(modifier = Modifier.height(60.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
            ) {
                // first name extField
                myTextField(
                    firstName, TypeField.FIRST,
                ) { onFirstNameTextChange(it)}
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
            ) {
                // last name extField
                myTextField(
                    lastName, TypeField.LAST,
                ) { onLastNameTextChange(it) }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
            ) {
                // email name extField
                myTextField(
                    email, TypeField.EMAIL,
                ) { onEmailNameTextChange(it) }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
            ) {
                Button(
                    onClick = {
                        val user = RegisterUser(firstName,lastName,email)
                        if (user.isInValid)  userRegisterViewModel.addUser(user) else Toast.makeText(context, "Invalidate registration!", Toast.LENGTH_SHORT).show()
                        },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(80.dp, 20.dp)
                ){
                    Text(text = stringResource(id = R.string.register))
                }
            }
        }
    }
}

@Preview
@Composable
private fun RegisterScreenPreview() {
    ChallengeTestTheme{
        //RegisterScreen()
    }
}

@Composable
private fun myTextField(
    name: String,
    typeField : TypeField,
    onValChange: ((String) -> Unit)?
    )
{

    val focusManager = LocalFocusManager.current
    if (onValChange != null) {
        InputFieldComponent(
            text = name,
            onChange = onValChange,
            label = "",
            modifier = Modifier
                .fillMaxWidth()
                .padding(80.dp, 20.dp)
                .border(
                    2.dp,
                    color = MaterialTheme.colors.primaryVariant,
                    shape = RoundedCornerShape(2.dp)
                )
            ,
            leadingIcon = {
                when (typeField) {
                    TypeField.EMAIL -> Icon(Icons.Default.Email, "email Icon")
                    else -> Icon(Icons.Default.Person, "people Icon")
                }
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color(0xFFFFFF),
                cursorColor = MaterialTheme.colors.primaryVariant,
            ),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            singleLine = true,
            shape = MaterialTheme.shapes.small.copy(
                bottomEnd = ZeroCornerSize,
                bottomStart = ZeroCornerSize
            ),

        )
    }
}