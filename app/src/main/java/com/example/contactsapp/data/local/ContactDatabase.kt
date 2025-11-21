package com.example.contactsapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ContactEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ContactDatabase : RoomDatabase() {
    abstract val dao: ContactDao

    companion object {
        const val DATABASE_NAME = "contacts_db"
    }
}
