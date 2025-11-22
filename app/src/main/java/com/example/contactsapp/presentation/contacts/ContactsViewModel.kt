package com.example.contactsapp.presentation.contacts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactsapp.domain.model.Contact
import com.example.contactsapp.domain.usecase.ContactUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import kotlinx.coroutines.launch
@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val useCases: ContactUseCases
) : ViewModel() {

    var state by mutableStateOf(ContactsState())
        private set

    var deleteMessage by mutableStateOf<String?>(null)
        private set

    init {
        loadContacts()
    }

    fun onEvent(event: ContactsEvent) {
        when (event) {
            is ContactsEvent.LoadContacts -> loadContacts()
            is ContactsEvent.DeleteContact -> deleteContact(event.contact)
        }
    }

    private fun loadContacts() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)

            val contacts = useCases.getContacts()

            state = state.copy(
                contacts = contacts,
                groupedContacts = contacts.groupBy { it.firstName.first().uppercaseChar() },
                isLoading = false
            )
        }
    }

    private fun deleteContact(contact: Contact) {
        viewModelScope.launch {
            useCases.deleteContact(contact.id.toString())
            deleteMessage = "User deleted"
            loadContacts()
        }
    }

    fun clearMessage() {
        deleteMessage = null
    }
}
