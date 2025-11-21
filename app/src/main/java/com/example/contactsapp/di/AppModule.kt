package com.example.contactsapp.di

import android.app.Application
import androidx.room.Room
import com.example.contactsapp.data.local.ContactDatabase
import com.example.contactsapp.data.repository.ContactRepositoryImplement
import com.example.contactsapp.domain.repository.ContactRepository
import com.example.contactsapp.domain.usecase.ContactUseCases
import com.example.contactsapp.domain.usecase.AddContactUseCase
import com.example.contactsapp.domain.usecase.DeleteContactUseCase
import com.example.contactsapp.domain.usecase.GetContactsUseCase
import com.example.contactsapp.domain.usecase.UpdateContactUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): ContactDatabase =
        Room.databaseBuilder(
            app,
            ContactDatabase::class.java,
            "contacts_db"
        ).build()

    @Provides
    @Singleton
    fun provideRepository(db: ContactDatabase): ContactRepository =
        ContactRepositoryImplement(db.dao)

    @Provides
    @Singleton
    fun provideUseCases(repo: ContactRepository): ContactUseCases =
        ContactUseCases(
            getContacts = GetContactsUseCase(repo),
            addContact = AddContactUseCase(repo),
            deleteContact = DeleteContactUseCase(repo),
            updateContact = UpdateContactUseCase(repo)
        )

}
