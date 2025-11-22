package com.example.contactsapp.data.remote.models

data class UploadImageResponseSuccessDto(
    val success: Boolean,
    val messages: List<String>,
    val data: UploadImageResponse,
    val status: Int
)
