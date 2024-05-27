package com.example.myapplication

import android.app.Application
import com.example.myapplication.database.AppDatabase
import com.example.myapplication.repository.CalculationRepository

class TodoApplication: Application() {
    private val database by lazy { AppDatabase.getDatabase(this) }

    val repository by lazy { CalculationRepository(database.calculationDao()) }

}