package com.example.myapplication.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.repository.CalculationRepository

class CalculationViewModelFactory(private val repository: CalculationRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalculationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CalculationViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
