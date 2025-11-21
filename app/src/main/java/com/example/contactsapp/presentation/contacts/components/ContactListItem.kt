package com.example.contactsapp.presentation.contacts.components
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.contactsapp.domain.model.Contact
import androidx.compose.ui.Alignment




@Composable
fun ContactListItem(
    contact: Contact,
    onClick: () -> Unit
) {
    // single contact row
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // profile placeholder
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
        )

        Spacer(modifier = Modifier.width(16.dp))

        // contact info
        Column {
            Text("${contact.firstName} ${contact.lastName}", fontWeight = FontWeight.Bold)
            Text(contact.phoneNumber, color = Color.Gray)
        }
    }
}
