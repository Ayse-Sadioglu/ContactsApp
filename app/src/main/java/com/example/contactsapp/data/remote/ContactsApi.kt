package com.example.contactsapp.data.remote
import okhttp3.MultipartBody
import retrofit2.http.*
import com.example.contactsapp.data.remote.models.*

interface ContactsApi {

    @POST("api/User")// Creates a new user using a JSON request body
    suspend fun createUser(
        @Body body: CreateUserRequest
    ): UserResponseSuccessDto

    @PUT("api/User/{id}")  // Updates an existing user by ID
    suspend fun updateUser(
        @Path("id") id: String,
        @Body body: UpdateUserRequest
    ): UserResponseSuccessDto

    @DELETE("api/User/{id}")// Deletes a user by ID
    suspend fun deleteUser(
        @Path("id") id: String
    ): EmptyResponseSuccessDto

    @GET("api/User/GetAll")// Retrieves all users from the backend
    suspend fun getAllUsers(): UserListResponseSuccessDto

    @GET("api/User/{id}") // Retrieves a single user by ID
    suspend fun getUser(
        @Path("id") id: String
    ): UserResponseSuccessDto

    @Multipart// Uploads an image file
    @POST("api/User/UploadImage")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part
    ): UploadImageResponseSuccessDto
}
