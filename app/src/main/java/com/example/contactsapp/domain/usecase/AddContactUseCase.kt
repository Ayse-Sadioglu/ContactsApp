package com.example.contactsapp.domain.usecase

import android.net.Uri
import com.example.contactsapp.domain.repository.ContactRepository

class AddContactUseCase(
    private val repository: ContactRepository
) {
    suspend operator fun invoke(
        firstName: String,
        lastName: String,
        phoneNumber: String,
        photoUri: Uri?
    ) = repository.createRemoteContact(
        firstName,
        lastName,
        phoneNumber,
        photoUri
    )
}

