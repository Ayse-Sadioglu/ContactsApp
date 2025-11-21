package com.example.contactsapp.presentation.add

data class AddContactState(
    val firstName: String = "",
    val lastName: String = "",
    val phoneNumber: String = "",
    val isSaving: Boolean = false,
    val isPhotoSheetVisible: Boolean = false
)
