package com.example.contactsapp.domain.usecase
import com.example.contactsapp.domain.repository.ContactRepository

import com.example.contactsapp.domain.model.Contact

class UpdateContactUseCase(
    private val repository: ContactRepository
) {
    suspend operator fun invoke(contact: Contact) {
        repository.update(contact)
    }
}
