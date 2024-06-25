package com.ircl.cambiosmvd.ui

sealed class Screen(val route: String) {
    object Home : Screen("home")
}