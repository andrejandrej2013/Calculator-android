package com.example.myapplication.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CalculationDao {
    @Query("SELECT * FROM calculation ORDER BY id ASC")
    fun getAllCalculations(): Flow<List<Calculation>>

    @Insert
    suspend fun insert(calculation: Calculation)

    @Delete
    suspend fun delete(calculation: Calculation)
}
