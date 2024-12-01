package com.example.demonstrator_2_basicnotes

import com.example.demonstrator_2_basicnotes.data.Note
import java.util.*

object DummyNotes {
    val notes = listOf(
        Note(
            id = 1,
            content = "dummy note 1",
            timestamp = Date().time
        ),
        Note(
            id = 2,
            content = "dummy note 2",
            timestamp = Date().time
        )
    )
}