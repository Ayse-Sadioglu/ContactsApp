package com.example.contactsapp.domain.model
import android.net.Uri

data class Contact(
    val id: Int = 0,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val photoUri: Uri?
)
//for UI