package com.example.contactsapp.presentation.contacts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactsapp.domain.model.Contact
import com.example.contactsapp.domain.usecase.ContactUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val useCases: ContactUseCases
) : ViewModel() {

    var state by mutableStateOf(ContactsState())
        private set


    init {
        loadContacts()
    }

    fun onEvent(event: ContactsEvent) {
        when(event) {
            is ContactsEvent.LoadContacts -> loadContacts()
            is ContactsEvent.DeleteContact -> deleteContact(event.contact)
        }
    }

    private fun loadContacts() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val contacts = useCases.getContacts()
            state = state.copy(
                isLoading = false,
                contacts = contacts
            )
        }
    }

    private fun deleteContact(contact: Contact) {
        viewModelScope.launch {
            useCases.deleteContact(contact)
            loadContacts()
        }
    }
}
