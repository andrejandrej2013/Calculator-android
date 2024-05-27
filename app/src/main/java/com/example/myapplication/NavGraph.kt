package com.example.myapplication

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.database.CalculationViewModel
import com.example.myapplication.ui.CalculatorScreen
import com.example.myapplication.ui.HistoryScreen
import com.example.myapplication.ui.GameScreen

sealed class Screen(val route: String) {
    object Calculator : Screen("calculator")
    object History : Screen("history")
    object Game : Screen("game")
}

@Composable
fun NavGraph(navController: NavHostController, calculationViewModel: CalculationViewModel, modifier: Modifier = Modifier) {
    NavHost(navController, startDestination = Screen.Calculator.route, modifier = modifier) {
        composable(Screen.Calculator.route) { CalculatorScreen(navController, calculationViewModel) }
        composable(Screen.History.route) { HistoryScreen(navController, calculationViewModel) }
        composable(Screen.Game.route) { GameScreen(navController) }
    }
}
