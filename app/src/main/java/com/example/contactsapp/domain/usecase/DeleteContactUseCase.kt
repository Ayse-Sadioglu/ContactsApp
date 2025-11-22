package com.example.contactsapp.domain.usecase

import com.example.contactsapp.domain.repository.ContactRepository

class DeleteContactUseCase(
    private val repository: ContactRepository
) {
    suspend operator fun invoke(id: String) =
        repository.deleteRemoteContact(id)
}
