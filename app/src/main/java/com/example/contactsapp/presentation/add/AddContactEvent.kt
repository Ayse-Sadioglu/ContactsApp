package com.example.contactsapp.presentation.add

sealed class AddContactEvent {
    data class FirstNameChanged(val value: String) : AddContactEvent()
    data class LastNameChanged(val value: String) : AddContactEvent()
    data class PhoneChanged(val value: String) : AddContactEvent()
    object OpenPhotoSheet : AddContactEvent()
    object ClosePhotoSheet : AddContactEvent()
    object SaveContact : AddContactEvent()
}
