package com.example.demonstrator_2_basicnotes.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val content: String,

    val timestamp: Long
    // timestamp long refers to the time passed in milliseconds since the unix epoch (January 1, 1970, 00:00:00 GMT)
    // https://docs.intersystems.com/irislatest/csp/documatic/%25CSP.Documatic.cls?LIBRARY=%25SYS&CLASSNAME=%25Library.TimeStamp
    // https://developer.android.com/reference/java/sql/Timestamp

    // https://en.wikipedia.org/wiki/Year_2038_problem
)