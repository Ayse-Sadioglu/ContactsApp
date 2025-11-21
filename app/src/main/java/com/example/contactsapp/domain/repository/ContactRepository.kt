package com.example.contactsapp.domain.repository

import com.example.contactsapp.domain.model.Contact

interface ContactRepository {

    suspend fun insert(contact: Contact)

    suspend fun update(contact: Contact)

    suspend fun delete(contact: Contact)

    suspend fun getAll(): List<Contact>
}
//UI → UseCase → Repository Interface