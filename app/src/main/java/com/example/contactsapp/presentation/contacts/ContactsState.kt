package com.example.contactsapp.presentation.contacts
import com.example.contactsapp.domain.model.Contact

data class ContactsState(
    val isLoading: Boolean = false,
    val contacts: List<Contact> = emptyList(),
    val error: String? = null
)
