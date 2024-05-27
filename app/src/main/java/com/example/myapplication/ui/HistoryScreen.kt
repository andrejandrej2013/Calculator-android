package com.example.myapplication.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.Screen
import com.example.myapplication.database.CalculationViewModel
import androidx.compose.runtime.getValue


@Composable
fun HistoryScreen(navController: NavController, viewModel: CalculationViewModel) {
    val calculations by viewModel.calculations.observeAsState(emptyList())

    Column(modifier = Modifier.padding(16.dp)) {
        calculations.forEach { calculation ->
            Text("${calculation.expression} = ${calculation.result} at ${calculation.createdAt}")
        }
        Button(onClick = { navController.navigate(Screen.Calculator.route) }) {
            Text("Calculator")
        }
        Button(onClick = { navController.navigate(Screen.Game.route) }) {
            Text("Game")
        }
    }
}
