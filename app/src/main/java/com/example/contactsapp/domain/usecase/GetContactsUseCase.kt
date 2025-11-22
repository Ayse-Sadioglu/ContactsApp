package com.example.contactsapp.domain.usecase

import com.example.contactsapp.domain.repository.ContactRepository

class GetContactsUseCase(
    private val repository: ContactRepository
) {
    suspend operator fun invoke() = repository.getRemoteContacts()
}
