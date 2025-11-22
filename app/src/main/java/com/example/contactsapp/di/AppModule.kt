package com.example.contactsapp.di

import android.content.Context
import com.example.contactsapp.data.remote.ContactsApi
import com.example.contactsapp.data.repository.ContactRepositoryImplement
import com.example.contactsapp.domain.repository.ContactRepository
import com.example.contactsapp.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "http://146.59.52.68:11235/"

    // API KEY Interceptor
    @Provides
    @Singleton
    fun provideApiKeyInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("ApiKey", "a3a45192-fb91-4bf8-a005-452547acc5c6")
                .build()
            chain.proceed(request)
        }
    }

    // OkHttp
    @Provides
    @Singleton
    fun provideOkHttpClient(apiKeyInterceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    // Retrofit
    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    // API
    @Provides
    @Singleton
    fun provideContactsApi(retrofit: Retrofit): ContactsApi {
        return retrofit.create(ContactsApi::class.java)
    }

    // Repository
    @Provides
    @Singleton
    fun provideContactRepository(
        api: ContactsApi,
        @ApplicationContext context: Context
    ): ContactRepository {
        return ContactRepositoryImplement(api, context.contentResolver)
    }

    // UseCases
    @Provides
    @Singleton
    fun provideContactUseCases(
        repo: ContactRepository
    ): ContactUseCases {
        return ContactUseCases(
            getContacts = GetContactsUseCase(repo),
            addContact = AddContactUseCase(repo),
            deleteContact = DeleteContactUseCase(repo),
            updateContact = UpdateContactUseCase(repo)
        )
    }
}
// This module wires the entire data layer: networking, repository, and use cases
// Retrofit + OkHttp handle API communication with automatic API key injection
// All dependencies are provided as singletons to ensure consistent and optimized appwide usage
