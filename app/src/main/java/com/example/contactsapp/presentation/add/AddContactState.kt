package com.example.contactsapp.presentation.add

import android.net.Uri

data class AddContactState(
    val firstName: String = "",
    val lastName: String = "",
    val phoneNumber: String = "",
    val isSaving: Boolean = false,
    val isPhotoSheetVisible: Boolean = false,
    val photoUri: Uri? = null,
    val isSaved: Boolean = false



)
