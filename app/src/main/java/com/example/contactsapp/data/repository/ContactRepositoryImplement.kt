package com.example.contactsapp.data.repository

import com.example.contactsapp.data.local.ContactDao
import com.example.contactsapp.data.local.ContactEntity
import com.example.contactsapp.data.local.toEntity
import com.example.contactsapp.data.local.toContact
import com.example.contactsapp.domain.model.Contact
import com.example.contactsapp.domain.repository.ContactRepository


class ContactRepositoryImplement(
    private val dao: ContactDao
) : ContactRepository {

    override suspend fun insert(contact: Contact) {
        val entity = ContactEntity(
            id = contact.id,
            firstName = contact.firstName,
            lastName = contact.lastName,
            phoneNumber = contact.phoneNumber,
            photoUri = contact.photoUri
        )
        dao.insertContact(entity)
    }


    override suspend fun update(contact: Contact) {
        dao.updateContact(contact.toEntity())
    }

    override suspend fun delete(contact: Contact) {
        dao.deleteContact(contact.toEntity())
    }

    override suspend fun getAll(): List<Contact> {
        return dao.getAllContacts().map { it.toContact() }
    }
}

//this class contacts with room