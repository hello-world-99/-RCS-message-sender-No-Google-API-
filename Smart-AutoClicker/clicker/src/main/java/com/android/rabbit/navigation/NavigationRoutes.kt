package com.android.clicker.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.clicker.ui.home.HomeScreen
import com.android.clicker.ui.settings.SettingsScreen

@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home screen") {
        composable("home screen") {
            HomeScreen(navController = navController)
        }
        composable("settings screen") {
            SettingsScreen(navController = navController)
        }

    }
}