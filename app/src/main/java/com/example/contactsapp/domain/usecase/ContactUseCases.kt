package com.example.contactsapp.domain.usecase

data class ContactUseCases(
    val getContacts: GetContactsUseCase,
    val addContact: AddContactUseCase,
    val updateContact: UpdateContactUseCase,
    val deleteContact: DeleteContactUseCase
)
