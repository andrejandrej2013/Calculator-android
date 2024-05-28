package com.example.myapplication.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.database.CalculationViewModel

@Composable
fun HistoryScreen(navController: NavController, viewModel: CalculationViewModel) {
    val calculations by viewModel.calculations.observeAsState(emptyList())

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(calculations) { calculation ->
            CalculationCard(
                expression = calculation.expression,
                result = calculation.result ?: "Unknown",
                createdAt = calculation.createdAt ?: "Unknown" // Provide a default value
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun CalculationCard(expression: String, result: String, createdAt: String) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Expression: $expression",
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "Result: $result",
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "Date: $createdAt",
                fontSize = 14.sp,
                color = androidx.compose.ui.graphics.Color.Gray
            )
        }
    }
}
