package com.example.contactsapp.presentation.contacts
import com.example.contactsapp.domain.model.Contact

sealed class ContactsEvent {
    object LoadContacts : ContactsEvent()
    data class DeleteContact(val contact: Contact) : ContactsEvent()
}
