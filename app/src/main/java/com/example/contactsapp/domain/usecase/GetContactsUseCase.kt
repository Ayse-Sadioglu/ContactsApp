package com.example.contactsapp.domain.usecase

import com.example.contactsapp.domain.model.Contact
import com.example.contactsapp.domain.repository.ContactRepository

class GetContactsUseCase(
    private val repository: ContactRepository
) {
    suspend operator fun invoke(): List<Contact> {
        return repository.getAll()
    }
}
