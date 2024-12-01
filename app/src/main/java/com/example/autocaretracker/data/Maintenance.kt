package com.example.autocaretracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "maintenance")
data class Maintenance(
    @PrimaryKey(autoGenerate = true) val maintenance_id: Int = 0,
    val car_id: Int,
    val task: String,
    val date: Long,
    val currentMileage: Int,
    val notes: String
)