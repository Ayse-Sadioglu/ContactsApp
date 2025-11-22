package com.example.contactsapp.data.remote.models

data class UserResponse(
    val id: String,
    val createdAt: String,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val profileImageUrl: String
)
