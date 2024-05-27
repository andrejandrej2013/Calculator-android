package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.database.AppDatabase
import com.example.myapplication.database.CalculationViewModel
import com.example.myapplication.database.CalculationViewModelFactory
import com.example.myapplication.repository.CalculationRepository
import com.example.myapplication.ui.theme.MyApplicationTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = AppDatabase.getDatabase(this)
        val repository = CalculationRepository(database.calculationDao())
        val calculationViewModel: CalculationViewModel = ViewModelProvider(
            this, CalculationViewModelFactory(repository)
        ).get(CalculationViewModel::class.java)

        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()
                Scaffold(
                    topBar = { TopAppBar(title = { Text("My Application") }) }
                ) { innerPadding ->
                    NavGraph(
                        navController = navController,
                        calculationViewModel = calculationViewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
