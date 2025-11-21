package com.example.contactsapp.presentation.contacts

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.contactsapp.presentation.contacts.components.ContactListItem

@Composable
fun ContactsScreen(
    viewModel: ContactsViewModel = hiltViewModel(),
    onAddClick: () -> Unit = {},
    onContactClick: (Int) -> Unit = {}
) {
    val state = viewModel.state

    Column(modifier = Modifier.fillMaxSize()) {

        // top bar
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
                    contentDescription = "add contact"
                )
            }
        }


        OutlinedTextField(
            value = "",
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            placeholder = { Text("Search by name") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // loading state
        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            return
        }

        // empty state
        if (state.contacts.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "No contacts available",
                    color = Color.Gray,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            return
        }

        // contacts list
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(state.contacts) { contact ->
                ContactListItem(
                    contact = contact,
                    onClick = { onContactClick(contact.id) }
                )
            }
        }
    }
}
