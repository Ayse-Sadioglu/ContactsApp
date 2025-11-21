package com.example.contactsapp.domain.model

data class Contact(
    val id: Int = 0,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val photoUri: String? = null
)
//for ui