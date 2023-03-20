@file:OptIn(ExperimentalMaterialApi::class)

package com.example.challengetest.component

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.challengetest.data.RegisterUser
import com.example.challengetest.ui.theme.ChallengeTestTheme
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun UserRow(
    user: RegisterUser,
    onSwipeNote: (RegisterUser) -> Unit
) {
    val iconSize = (-68).dp
    val swipeableState = rememberSwipeableState(0)
    val iconPx = with(LocalDensity.current) { iconSize.toPx() }
    val anchors = mapOf(0f to 0, iconPx to 1)

    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(75.dp)
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                thresholds = { _, _ -> FractionalThreshold(0.5f) },
                orientation = Orientation.Horizontal
            )
            .background(Color(0xFFDA5D5D))
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .align(Alignment.CenterEnd)
                .padding(end = 10.dp)
                .background(Color.Red)
        ) {
            IconButton(
                modifier = Modifier.align(Alignment.Center),
                onClick = {
                    Log.d("user", "Deleted")
                    coroutineScope.launch {
                        onSwipeNote(user)
                    }
                }
            ) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Delete this note",
                    tint = Color.White
                )
            }
        }

        AnimatedVisibility(
            visible = true, //TODO user.isVisible
            exit = slideOutHorizontally(
                targetOffsetX = { -it },
                animationSpec = TweenSpec(200, 0, FastOutLinearInEasing)
            )
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                val (icon, firstText, secondText, thirdText, divider) = createRefs()

                Icon(
                    Icons.Default.Person,
                    contentDescription = "person",
                    tint = Color.White
                )

                Text(
                    modifier = Modifier.constrainAs(firstText) {
                        top.linkTo(icon.top, margin = 12.dp)
                        start.linkTo(icon.end, margin = 18.dp)
                        end.linkTo(parent.end, margin = 18.dp)
                        width = Dimension.fillToConstraints
                    },
                    text = user.firstName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight(500),
                    textAlign = TextAlign.Start,
                )

                Text(
                    modifier = Modifier.constrainAs(secondText) {
                        top.linkTo(firstText.bottom, margin = 4.dp)
                        start.linkTo(icon.end, margin = 18.dp)
                        width = Dimension.fillToConstraints
                    },
                    text = user.lastName,
                    textAlign = TextAlign.Start,
                    fontSize = 10.sp
                )

                Text(
                    modifier = Modifier.constrainAs(thirdText) {
                        bottom.linkTo(parent.bottom, margin = 12.dp)
                        start.linkTo(icon.end, margin = 18.dp)
                        end.linkTo(parent.end, margin = 18.dp)
                        width = Dimension.fillToConstraints
                    },
                    text = user.emailUser,
                    textAlign = TextAlign.Start,
                    fontSize = 8.sp
                )

                Divider(
                    modifier = Modifier.constrainAs(divider) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                    },
                    thickness = 1.dp,
                    color = Color.DarkGray
                )
            }
        }
    }
}

fun changeNoteVisibility(users: List<RegisterUser>, removedUser: RegisterUser): List<RegisterUser> {
    return users.map { currentUser ->
        if (currentUser.idUser == removedUser.idUser) {
            currentUser//.copy(isVisible = false) //TODO INVESTIGATE
        } else {
            currentUser
        }
    }
}

fun changeNoteVisibilityPrevie(user: RegisterUser, removedUser: RegisterUser) {
    return print("otot")
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Preview
@Composable
private fun RegisterScreenPreview() {
    ChallengeTestTheme {
        val user = RegisterUser(1,"Jean", "LANG", "test@gmail.com")

        UserRow(user) {
            changeNoteVisibilityPrevie(user, it)
        }
    }
}

