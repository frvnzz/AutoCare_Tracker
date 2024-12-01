// NotesDao.kt
package com.example.myapp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {
    @Insert
    suspend fun addNote(note: NoteEntity)

    @Delete
    suspend fun deleteNote(note: NoteEntity)

    @Query("SELECT * FROM notes")
    fun getAllNotes(): Flow<List<NoteEntity>>
}