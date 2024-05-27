package com.example.myapplication

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppTopBar(navController: NavHostController, selectedTab: MutableState<Screen>) {
    val screens = listOf(Screen.Calculator, Screen.History, Screen.Game)
    Column {
        TabRow(
            selectedTabIndex = screens.indexOf(selectedTab.value),
            containerColor = Color.Transparent, // Optional: Adjust the TabRow color to match the AppBar
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ) {
            screens.forEachIndexed { index, screen ->
                Tab(
                    selected = selectedTab.value == screen,
                    onClick = {
                        selectedTab.value = screen
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    text = { Text(screen.route.capitalize()) } // Capitalize the route to use as a title
                )
            }
        }
    }
}
