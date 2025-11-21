package com.example.contactsapp.domain.usecase

data class ContactUseCases(
    val addContact: AddContactUseCase,
    val deleteContact: DeleteContactUseCase,
    val updateContact: UpdateContactUseCase,
    val getContacts: GetContactsUseCase
)
