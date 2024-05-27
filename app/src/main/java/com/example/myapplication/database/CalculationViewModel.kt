package com.example.myapplication.database

import androidx.lifecycle.*
import com.example.myapplication.repository.CalculationRepository
import kotlinx.coroutines.launch

class CalculationViewModel(private val repository: CalculationRepository): ViewModel()
{
    var calculations: LiveData<List<Calculation>> = repository.allCalculations.asLiveData()

    fun addCalculation(calculation: Calculation) = viewModelScope.launch {
        repository.insertCalculation(calculation)
    }
}

