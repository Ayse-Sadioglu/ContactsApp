package com.example.contactsapp.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactsapp.domain.model.Contact
import com.example.contactsapp.domain.usecase.ContactUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchViewModel(
    private val useCases: ContactUseCases
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _searchHistory = MutableStateFlow<List<String>>(emptyList())
    val searchHistory: StateFlow<List<String>> = _searchHistory

    private val _results = MutableStateFlow<List<Contact>>(emptyList())
    val results: StateFlow<List<Contact>> = _results

    fun onQueryChange(query: String) {
        _searchQuery.value = query

        if (query.isBlank()) {
            _results.value = emptyList()
            return
        }

        viewModelScope.launch {
            val allContacts = useCases.getContacts()
            _results.value = allContacts.filter {
                it.fullName().contains(query, ignoreCase = true)
            }
        }
    }

    fun onSubmitSearch() {
        val q = searchQuery.value
        if (q.isNotBlank() && !_searchHistory.value.contains(q)) {
            _searchHistory.value = listOf(q) + _searchHistory.value
        }
    }

    fun clearHistory() {
        _searchHistory.value = emptyList()
    }
}

private fun Contact.fullName(): String {
    return "$firstName $lastName"
}
