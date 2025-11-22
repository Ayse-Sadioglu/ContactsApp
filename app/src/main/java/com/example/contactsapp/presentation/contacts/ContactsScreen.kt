package com.example.contactsapp.presentation.contacts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.contactsapp.presentation.contacts.components.ContactListItem

@Composable
fun ContactsScreen(
    viewModel: ContactsViewModel = hiltViewModel(),
    onAddClick: () -> Unit = {},
    onContactClick: (Int) -> Unit = {},
    onSearchClick: () -> Unit = {}
) {
    val state = viewModel.state// UI state from the ViewModel

    Column(modifier = Modifier.fillMaxSize()) {
// Top bar with title and "Add" button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Contacts",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            IconButton(onClick = onAddClick) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "add"
                )
            }
        }

        OutlinedTextField(
            value = "",
            onValueChange = {},
            enabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clickable { onSearchClick() },
            placeholder = { Text("Search by name") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (state.isLoading) {  // Loading state
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            return
        }

        if (state.contacts.isEmpty()) {// Empty list state
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "No contacts available",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            return
        }
// Contacts list with swipe actions
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp)
        ) {
            items(state.contacts) { contact ->
                ContactListItem(
                    contact = contact,
                    onClick = { onContactClick(contact.id) },
                    onEdit = { onContactClick(contact.id) },
                    onDelete = { viewModel.onEvent(ContactsEvent.DeleteContact(contact)) }
                )
            }
        }
    }
}
