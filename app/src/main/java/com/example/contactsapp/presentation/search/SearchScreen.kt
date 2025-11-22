package com.example.contactsapp.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.res.painterResource
import com.example.contactsapp.R
import com.example.contactsapp.domain.model.Contact

@Composable
fun SearchScreen(
    onBack: () -> Unit,
    allContacts: List<Contact>
) {
    var query by remember { mutableStateOf("") }
    var history by remember { mutableStateOf(listOf("Ayse", "Ali")) }//placeholder

    val filtered = remember(query, allContacts) {
        allContacts.filter {
            val fullName = "${it.firstName} ${it.lastName}"
            fullName.contains(query, ignoreCase = true)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F8FA))
            .padding(16.dp)
    ) {

        // Top bar
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .size(28.dp)
                    .clickable { onBack() }
            )

            Spacer(Modifier.width(8.dp))

            Text(
                "Contacts",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(Modifier.height(12.dp))

        // Search bar
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search by name") },
            singleLine = true
        )

        Spacer(Modifier.height(16.dp))

        // State: query empty -> show history
        if (query.isEmpty()) {

            Text(
                "SEARCH HISTORY",
                style = MaterialTheme.typography.labelMedium,
                color = Color.Gray
            )

            Spacer(Modifier.height(8.dp))

            LazyColumn {
                items(history) { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { query = item }
                            .padding(vertical = 12.dp)
                    ) {
                        Text(item)
                    }
                }
            }

            Text(
                "Clear All",
                color = Color.Blue,
                modifier = Modifier
                    .clickable { history = emptyList() }
                    .padding(top = 12.dp)
            )

        } else if (filtered.isNotEmpty()) {

            // Grouped results
            val grouped = filtered.groupBy { it.firstName.first().uppercase() }

            LazyColumn {
                grouped.keys.sorted().forEach { letter ->
                    item {
                        Text(
                            text = letter,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }

                    items(grouped[letter]!!) { contact ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(Color.LightGray, shape = CircleShape)
                            )
                            Spacer(Modifier.width(10.dp))
                            Column {
                                Text("${contact.firstName} ${contact.lastName}")
                                Text(contact.phoneNumber, color = Color.Gray)
                            }
                        }
                    }
                }
            }

        } else {

            // No results
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_no_results),
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(80.dp)
                )

                Spacer(Modifier.height(16.dp))

                Text("No Results", fontWeight = FontWeight.Bold)
                Text("The user you are looking for could not be found.")
            }
        }
    }
}
