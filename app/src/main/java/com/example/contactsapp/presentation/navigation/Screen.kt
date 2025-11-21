package com.example.contactsapp.presentation.navigation

sealed class Screen(val route: String) {
    data object Contacts : Screen("contacts")
    data object AddContact : Screen("add_contact")
    data object Profile : Screen("profile")
    data object Search : Screen("search")
}
