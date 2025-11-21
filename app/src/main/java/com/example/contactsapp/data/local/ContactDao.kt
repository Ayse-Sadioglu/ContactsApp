package com.example.contactsapp.data.local

import androidx.room.*

@Dao
interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContact(contact: ContactEntity)

    @Update
    suspend fun updateContact(contact: ContactEntity)

    @Delete
    suspend fun deleteContact(contact: ContactEntity)

    @Query("SELECT * FROM contacts ORDER BY firstName ASC")
    suspend fun getAllContacts(): List<ContactEntity>
}
