package com.example.contactsapp.domain.usecase

import com.example.contactsapp.domain.model.Contact
import com.example.contactsapp.domain.repository.ContactRepository


class AddContactUseCase(
    private val repository: ContactRepository
) {
    suspend operator fun invoke(contact: Contact) {
        repository.insert(contact)
    }
}

