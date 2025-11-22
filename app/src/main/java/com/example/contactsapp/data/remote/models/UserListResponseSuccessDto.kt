package com.example.contactsapp.data.remote.models

data class UserListResponseSuccessDto(
    val success: Boolean,
    val messages: List<String>,
    val data: UserListResponse,
    val status: Int
)
