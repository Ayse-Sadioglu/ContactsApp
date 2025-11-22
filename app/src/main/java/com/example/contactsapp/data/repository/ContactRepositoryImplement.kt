package com.example.contactsapp.data.repository

import android.content.ContentResolver
import android.net.Uri
import androidx.core.net.toUri
import com.example.contactsapp.data.remote.ContactsApi
import com.example.contactsapp.data.remote.models.CreateUserRequest
import com.example.contactsapp.data.remote.models.UpdateUserRequest
import com.example.contactsapp.data.remote.models.UserResponse
import com.example.contactsapp.domain.model.Contact
import com.example.contactsapp.domain.repository.ContactRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class ContactRepositoryImplement @Inject constructor(
    private val api: ContactsApi,
    private val contentResolver: ContentResolver
) : ContactRepository {

    override suspend fun getRemoteContacts(): List<Contact> {  // Fetch all contacts from API
        val response = api.getAllUsers()
        return response.data.users.map { it.toDomain() }
    }

    override suspend fun getRemoteContactById(id: String): Contact { // Fetch a single contact by id
        val response = api.getUser(id)
        return response.data.toDomain()
    }

    override suspend fun createRemoteContact( // Create a new contact
        firstName: String,
        lastName: String,
        phoneNumber: String,
        photoUri: Uri?//image upload is optional
    ): Contact {

        val imageUrl: String? = uploadImageIfExists(photoUri) // Upload image only if provided

        val body = CreateUserRequest( // Build request body for API
            firstName = firstName,
            lastName = lastName,
            phoneNumber = phoneNumber,
            profileImageUrl = imageUrl
        )

        val response = api.createUser(body)  // Send create request
        return response.data.toDomain()
    }

    override suspend fun updateRemoteContact(// Update an existing contact
        id: String,
        firstName: String,
        lastName: String,
        phoneNumber: String,
        photoUri: Uri?
    ): Contact {

        val imageUrl: String = uploadImageIfExists(photoUri) ?: "" // Upload image if provided otherwise leave empty

        val body = UpdateUserRequest(
            firstName = firstName,
            lastName = lastName,
            phoneNumber = phoneNumber,
            profileImageUrl = imageUrl
        )

        api.updateUser(id, body) // Perform update request
        // Fetch the updated user from server
        val updated = api.getUser(id)
        return updated.data.toDomain()
    }
    // Delete contact by id
    override suspend fun deleteRemoteContact(id: String) {
        api.deleteUser(id)
    }
    // Upload image only when a valid Uri exists
    private suspend fun uploadImageIfExists(uri: Uri?): String? {
        if (uri == null) return null
        val part = createImagePart(uri) ?: return null
        return api.uploadImage(part).data.imageUrl
    }

    // Convert Uri into MultipartBody.Part for file upload
    private suspend fun createImagePart(uri: Uri): MultipartBody.Part? {
        return withContext(Dispatchers.IO) {
            val input = contentResolver.openInputStream(uri) ?: return@withContext null
            val bytes = input.readBytes()
            val requestBody = bytes.toRequestBody("image/*".toMediaTypeOrNull())

            MultipartBody.Part.createFormData(
                name = "image",
                filename = "photo.jpg",//TODO: add randomized id to photo_id
                body = requestBody
            )
        }
    }
}

private fun UserResponse.toDomain(): Contact {// Maps API user model into domain Contact model
    return Contact(
        id = id.toIntOrNull() ?: 0,
        firstName = firstName,
        lastName = lastName,
        phoneNumber = phoneNumber,
        photoUri = profileImageUrl?.toUri()
    )
}
