package com.example.contactsapp.data.local
import com.example.contactsapp.domain.model.Contact

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class ContactEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val photoUri: String? = null
)
fun ContactEntity.toContact(): Contact {
    return Contact(
        id = id,
        firstName = firstName,
        lastName = lastName,
        phoneNumber = phoneNumber,
        photoUri = photoUri
    )
}

fun Contact.toEntity(): ContactEntity {
    return ContactEntity(
        id = id,
        firstName = firstName,
        lastName = lastName,
        phoneNumber = phoneNumber,
        photoUri = photoUri
    )
}
