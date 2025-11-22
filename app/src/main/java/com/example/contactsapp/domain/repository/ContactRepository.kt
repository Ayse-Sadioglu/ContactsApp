package com.example.contactsapp.domain.repository

import android.net.Uri
import com.example.contactsapp.domain.model.Contact

interface ContactRepository {

    suspend fun getRemoteContacts(): List<Contact>

    suspend fun getRemoteContactById(id: String): Contact

    suspend fun createRemoteContact(
        firstName: String,
        lastName: String,
        phoneNumber: String,
        photoUri: Uri?
    ): Contact

    suspend fun updateRemoteContact(
        id: String,
        firstName: String,
        lastName: String,
        phoneNumber: String,
        photoUri: Uri?
    ): Contact

    suspend fun deleteRemoteContact(id: String)
}
