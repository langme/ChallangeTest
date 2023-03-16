package com.example.challengetest.domain

sealed class ValidationEvent{
    object Success: ValidationEvent()
    object Error: ValidationEvent()
}
