package com.example.contactsapp.presentation.add
import android.net.Uri

sealed class AddContactEvent {
    data class FirstNameChanged(val value: String) : AddContactEvent()
    data class LastNameChanged(val value: String) : AddContactEvent()
    data class PhoneChanged(val value: String) : AddContactEvent()
    object OpenPhotoSheet : AddContactEvent()
    object ClosePhotoSheet : AddContactEvent()
    data class PhotoSelected(val uri: Uri) : AddContactEvent()

    object SaveContact : AddContactEvent()
}
