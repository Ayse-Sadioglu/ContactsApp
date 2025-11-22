package com.example.contactsapp.data.remote.models

data class UserResponseSuccessDto(
    val success: Boolean,
    val messages: List<String>,
    val data: UserResponse,
    val status: Int
)
