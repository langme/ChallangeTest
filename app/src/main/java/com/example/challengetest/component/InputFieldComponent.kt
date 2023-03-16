package com.example.challengetest.component

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.challengetest.R
import com.example.challengetest.domain.UIEvent
import com.example.challengetest.ui.theme.ChallengeTestTheme


@Composable
fun InputFieldComponent(
    typeField: UIEvent.TypeField,
    isError: Boolean,
    onTextChanged: (String) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val label: String
    val leadingIcon: ImageVector


    var text by remember {
        mutableStateOf("")
    }

    when (typeField) {
        UIEvent.TypeField.FIRST -> {
            label = stringResource(id = R.string.firstName)
            leadingIcon = Icons.Filled.Person
        }
        UIEvent.TypeField.LAST -> {
            label = stringResource(id = R.string.lastName)
            leadingIcon = Icons.Default.Person
        }
        UIEvent.TypeField.EMAIL -> {
            label = stringResource(id = R.string.email)
            leadingIcon =  Icons.Default.Email
        }
    }

    OutlinedTextField(
        value = text,
        onValueChange = {
            text = it
            onTextChanged(it)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(80.dp, 10.dp)
            .border(
                2.dp,
                color = MaterialTheme.colors.primaryVariant,
                shape = RoundedCornerShape(2.dp)
            ),
        singleLine = true,
        label = { Text(text = label)},
        placeholder = { Text(text = label) },
        leadingIcon =  { Icon(leadingIcon,
            tint = MaterialTheme.colors.primary,
            contentDescription = "Back button"
        )},
        colors =  TextFieldDefaults.textFieldColors(
            backgroundColor = Color(0xFFFFFFFF),
            cursorColor = MaterialTheme.colors.primaryVariant
        ),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        shape = MaterialTheme.shapes.small.copy(
            bottomEnd = ZeroCornerSize,
            bottomStart = ZeroCornerSize
        ),
        isError = isError
    )
}

@Preview(
    name = "Night Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    name = "Day Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)

@Composable
fun PreviewInputFieldComponent() {
    ChallengeTestTheme {
        Surface {
        }
    }
}