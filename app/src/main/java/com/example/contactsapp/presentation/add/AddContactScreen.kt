package com.example.contactsapp.presentation.add

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.contactsapp.R
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import com.airbnb.lottie.compose.*




@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

@Composable
fun AddContactScreen(
    viewModel: AddContactViewModel = hiltViewModel(),
    onBack: () -> Unit,
    onDone: () -> Unit = {}
) {
    val state = viewModel.state

    // Launcher for selecting image from gallery
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { viewModel.onEvent(AddContactEvent.PhotoSelected(it)) }
    }

    val focusManager = LocalFocusManager.current
    if (state.isSaved) {
        LaunchedEffect(Unit) {
            onDone()
        }
        return
    }

    if (state.isSaving) {

        val composition by rememberLottieComposition(
            LottieCompositionSpec.RawRes(R.raw.done)
        )

        val progress by animateLottieCompositionAsState(
            composition = composition,
            iterations = 1,
            speed = 1f,
            restartOnPlay = false,
            cancellationBehavior = LottieCancellationBehavior.Immediately
        )


        LaunchedEffect(progress) {
            if (progress == 1f) {
                onDone()
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                LottieAnimation(
                    composition = composition,
                    progress = { progress },
                    modifier = Modifier.size(200.dp)
                )

                Text("All Done!", style = MaterialTheme.typography.titleMedium)
                Text("New contact saved 🎉")
            }
        }

        return
    }



    // Root layout
    Column(
        modifier = Modifier
            .fillMaxSize()
            // clear focus when tapping outside text fields
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }
            .padding(horizontal = 24.dp)
    ) {

        // top bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Cancel",
                modifier = Modifier.clickable { onBack() })
            Text("New Contact", style = MaterialTheme.typography.titleMedium)

            Text(
                text = "Done",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable {
                    viewModel.onEvent(AddContactEvent.SaveContact)

                }
            )
        }

        Spacer(Modifier.height(24.dp))

        // picture area
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(110.dp)
                    .background(
                        MaterialTheme.colorScheme.surfaceVariant,
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.size(60.dp)
                )
            }

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Add Photo",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable {
                    viewModel.onEvent(AddContactEvent.OpenPhotoSheet)
                }
            )
        }


        Spacer(Modifier.height(32.dp))

        // text fiels
        OutlinedTextField(
            value = state.firstName,
            onValueChange = { viewModel.onEvent(AddContactEvent.FirstNameChanged(it)) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("First Name") }
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = state.lastName,
            onValueChange = { viewModel.onEvent(AddContactEvent.LastNameChanged(it)) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Last Name") }
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = state.phoneNumber,
            onValueChange = { viewModel.onEvent(AddContactEvent.PhoneChanged(it)) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Phone Number") }
        )
        // bottom sheet
        if (state.isPhotoSheetVisible) {
            androidx.compose.material3.ModalBottomSheet(
                onDismissRequest = {
                    viewModel.onEvent(AddContactEvent.ClosePhotoSheet)
                }
            ) {
                PhotoPickerSheet(
                    onCameraClick = {
                        // TODO: open camera
                        viewModel.onEvent(AddContactEvent.ClosePhotoSheet)
                    },
                    onGalleryClick = {
                        galleryLauncher.launch("image/*")
                        viewModel.onEvent(AddContactEvent.ClosePhotoSheet)
                    }
                    ,
                    onCancel = {
                        viewModel.onEvent(AddContactEvent.ClosePhotoSheet)
                    }
                )
            }
        }



    }

}
// Photo bottom sheet
@Composable
fun PhotoPickerSheet(
    onCameraClick: () -> Unit,
    onGalleryClick: () -> Unit,
    onCancel: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Camera button
        Button(
            onClick = onCameraClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = CircleShape
        ) {
            Text("Camera")
        }

        Spacer(Modifier.height(12.dp))

        // Gallery button
        Button(
            onClick = onGalleryClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = CircleShape
        ) {
            Text("Gallery")
        }

        Spacer(Modifier.height(12.dp))

        // Cancel button
        Text(
            text = "Cancel",
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable { onCancel() }
        )
    }
}


