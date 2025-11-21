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


@HiltViewModel
class AddContactViewModel @Inject constructor(
    private val useCases: ContactUseCases
) : ViewModel() {


    var state by mutableStateOf(AddContactState())
        private set

    // Controls visibility of bottom sheet
    var isPhotoSheetVisible by mutableStateOf(false)
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



            AddContactEvent.SaveContact -> saveContact()
        }
    }

    private fun saveContact() {
        viewModelScope.launch {
            state = state.copy(isSaving = true)

            val newContact = Contact(
                id = 0,
                firstName = state.firstName,
                lastName = state.lastName,
                phoneNumber = state.phoneNumber,
                photoUri = null
            )

            useCases.addContact(newContact)

            state = state.copy(isSaving = false)
        }
    }

}
