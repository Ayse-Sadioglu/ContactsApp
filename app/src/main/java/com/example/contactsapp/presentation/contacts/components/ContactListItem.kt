package com.example.contactsapp.presentation.contacts.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import com.example.contactsapp.R
import com.example.contactsapp.domain.model.Contact
import kotlin.math.roundToInt

@Composable
fun ContactListItem(
    contact: Contact,
    onClick: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    var offsetX by remember { mutableStateOf(0f) }  // Tracks horizontal swipe position for the foreground layer
// Defines how far the item can be swiped to reveal action buttons
    val maxOffset = -160f      // 2 button
    val minOffset = 0f

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
    ) {

         // Background layer containing Edit and Delete action buttons
        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {

            Box(
                modifier = Modifier
                    .width(80.dp)
                    .fillMaxHeight()
                    .background(Color(0xFF1E90FF))
                    .clickable { onEdit() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.pencil),
                    contentDescription = "edit",
                    tint = Color.White
                )
            }

            Box(
                modifier = Modifier
                    .width(80.dp)
                    .fillMaxHeight()
                    .background(Color(0xFFFF3B30))
                    .clickable { onDelete() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.trash_can_outline),
                    contentDescription = "delete",
                    tint = Color.White
                )
            }
        }

        // Foreground swipe layer
        Row(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { translationX = offsetX }
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onDragEnd = {
                            offsetX = if (offsetX < maxOffset / 2f) maxOffset else minOffset
                        }
                    ) { _, amount ->
                        offsetX = (offsetX + amount).coerceIn(maxOffset, minOffset)
                    }
                }
                .background(Color.White)
                .clickable { onClick() }
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
            )

            Spacer(Modifier.width(16.dp))

            Column {
                Text("${contact.firstName} ${contact.lastName}")
                Text(contact.phoneNumber, color = Color.Gray)
            }
        }
    }
}
