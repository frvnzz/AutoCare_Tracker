package com.example.demonstrator_2_basicnotes.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes ORDER BY timestamp DESC")
    fun getAllNotes(): Flow<List<Note>>

    @Insert
    suspend fun insert(note: Note)

    @Query("DELETE FROM notes WHERE id = :noteId")
    suspend fun delete(noteId: Int)
}