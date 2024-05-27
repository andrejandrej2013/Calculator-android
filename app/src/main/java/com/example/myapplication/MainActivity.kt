package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
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
                val selectedTab = remember { mutableStateOf<Screen>(Screen.Calculator) }

                Scaffold(
                    topBar = { MyAppTopBar(navController, selectedTab) }
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
