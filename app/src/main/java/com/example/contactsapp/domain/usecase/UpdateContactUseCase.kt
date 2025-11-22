package com.example.contactsapp.domain.usecase

import android.net.Uri
import com.example.contactsapp.domain.repository.ContactRepository

class UpdateContactUseCase(
    private val repository: ContactRepository
) {
    suspend operator fun invoke(
        id: String,
        firstName: String,
        lastName: String,
        phoneNumber: String,
        photoUri: Uri?
    ) = repository.updateRemoteContact(
        id,
        firstName,
        lastName,
        phoneNumber,
        photoUri
    )
}
