package com.example.myapplication.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "calculation")
data class Calculation(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "expression") val expression: String,
    @ColumnInfo(name = "result") val result: String?,
    @ColumnInfo(name = "created_at")  val createdAt:String?
)
