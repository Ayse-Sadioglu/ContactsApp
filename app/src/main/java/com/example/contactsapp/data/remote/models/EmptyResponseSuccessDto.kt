package com.example.contactsapp.data.remote.models

data class EmptyResponseSuccessDto(
    val success: Boolean,
    val messages: List<String>,
    val data: EmptyResponse,
    val status: Int
)
