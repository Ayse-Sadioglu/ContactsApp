package com.example.contactsapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.contactsapp.presentation.contacts.ContactsScreen
import com.example.contactsapp.presentation.add.AddContactScreen
import com.example.contactsapp.presentation.profile.ProfileScreen
import com.example.contactsapp.presentation.search.SearchScreen
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.contactsapp.presentation.contacts.ContactsViewModel

@Composable
fun AppNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    NavHost(
        navController = navController,
        startDestination = Screen.Contacts.route,
        modifier = modifier
    ) {

        composable(Screen.Contacts.route) {
            ContactsScreen(
                onAddClick = { navController.navigate(Screen.AddContact.route) },
                onContactClick = { id -> },
                onSearchClick = { navController.navigate(Screen.Search.route) }
            )
        }


        composable(Screen.AddContact.route) {
            AddContactScreen(
                onBack = { navController.popBackStack() },
                onDone = {
                    navController.navigate(Screen.Contacts.route) {
                        popUpTo(Screen.AddContact.route) { inclusive = true }
                    }
                }

            )
        }



        composable(Screen.Profile.route) {
            ProfileScreen()
        }

        composable(Screen.Search.route) {
            println("SearchScreen OPENED")
            val viewModel: ContactsViewModel = hiltViewModel()

            SearchScreen(
                onBack = { navController.popBackStack() },
                allContacts = viewModel.state.contacts
            )
        }



    }
}
