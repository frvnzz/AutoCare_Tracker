package com.example.demonstrator_2_basicnotes.data

import kotlinx.coroutines.flow.Flow

class NoteRepository(private val noteDao: NoteDao) {
    val allNotes: Flow<List<Note>> = noteDao.getAllNotes()

    suspend fun insert(note: Note) {
        noteDao.insert(note)
    }

    suspend fun delete(noteId: Int) {
        noteDao.delete(noteId)
    }
}