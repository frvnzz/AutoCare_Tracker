package com.example.autocaretracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cars")
data class Car(
    @PrimaryKey(autoGenerate = true) val car_id: Int = 0,
    val name: String,
    val imagePath: String,
    val latestMileage: Int
)