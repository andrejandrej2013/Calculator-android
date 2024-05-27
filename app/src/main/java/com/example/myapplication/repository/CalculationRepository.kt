package com.example.myapplication.repository

import androidx.annotation.WorkerThread
import com.example.myapplication.database.CalculationDao
import com.example.myapplication.database.Calculation
import kotlinx.coroutines.flow.Flow


class CalculationRepository(private val calculationDau: CalculationDao) {

    val allCalculations:Flow<List<Calculation>> = calculationDau.getAllCalculations()

    @WorkerThread
    suspend fun insertCalculation(calculation: Calculation)
    {
        calculationDau.insert(calculation)
    }

    @WorkerThread
    suspend fun deleteCalculation(calculation: Calculation)
    {
        calculationDau.delete(calculation)
    }
}