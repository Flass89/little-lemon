package com.example.littlelemon

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun MyNavigation(navController: NavHostController, database: AppDatabase) { // Added database here
    // ... (rest of your sharedPreferences logic)

    NavHost(navController = navController, startDestination = "Onboarding") {
        composable(Onboarding.route) {
            Onboarding(navController)
        }
        composable(Home.route) {
            // Pass the database to the Home composable
            Home(navController, database)
        }
        composable(Profile.route) {
            Profile(navController)
        }
    }
}