// NotesRepository.kt
package com.example.myapp.data

class NotesRepository(private val notesDao: NotesDao) {
    val allNotes = notesDao.getAllNotes()

    suspend fun addNote(note: NoteEntity) {
        notesDao.addNote(note)
    }

    suspend fun deleteNote(note: NoteEntity) {
        notesDao.deleteNote(note)
    }
}