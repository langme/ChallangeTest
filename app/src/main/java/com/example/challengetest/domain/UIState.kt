package com.example.challengetest.domain

data class UIState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",

    val hasFirstNameError: Boolean = false,
    val hasLastNameError: Boolean = false,
    val hasEmailError: Boolean = false,

)
