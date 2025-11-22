package com.example.contactsapp.presentation.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactsapp.domain.usecase.ContactUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.contactsapp.domain.model.Contact
import com.example.contactsapp.domain.repository.ContactRepository
import kotlinx.coroutines.delay


@HiltViewModel
class AddContactViewModel @Inject constructor(
    private val useCases: ContactUseCases
) : ViewModel() {


    var state by mutableStateOf(
        AddContactState(
            isPhotoSheetVisible = false,
            photoUri = null
        )
    )
        private set




    fun onEvent(event: AddContactEvent) {
        when (event) {

            is AddContactEvent.FirstNameChanged -> {
                state = state.copy(firstName = event.value)
            }

            is AddContactEvent.LastNameChanged -> {
                state = state.copy(lastName = event.value)
            }

            is AddContactEvent.PhoneChanged -> {
                state = state.copy(phoneNumber = event.value)
            }
            is AddContactEvent.OpenPhotoSheet -> {
                state = state.copy(isPhotoSheetVisible = true)
            }

            is AddContactEvent.ClosePhotoSheet -> {
                state = state.copy(isPhotoSheetVisible = false)
            }
            is AddContactEvent.PhotoSelected -> {
                state = state.copy(
                    photoUri = event.uri,
                    isPhotoSheetVisible = false
                )
            }



            AddContactEvent.SaveContact -> saveContact()
        }
    }

    private fun saveContact() {
        viewModelScope.launch {
            state = state.copy(isSaving = true)

            useCases.addContact(
                firstName = state.firstName,
                lastName = state.lastName,
                phoneNumber = state.phoneNumber,
                photoUri = state.photoUri
            )

            delay(1500)

            state = state.copy(
                isSaving = false,
                isSaved = true
            )
        }
    }

// ViewModel responsible for handling all UI logic of the Add Contact screen
// Manages user inputs photo selection state, and triggers the add-contact use case
// Central event handler that updates UI state or performs actions based on events coming from the UI layer
// Saves a new contact by calling the appropriate domain use case




}
