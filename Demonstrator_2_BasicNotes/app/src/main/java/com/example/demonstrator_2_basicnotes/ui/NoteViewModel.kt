package com.example.demonstrator_2_basicnotes.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.demonstrator_2_basicnotes.data.Note
import com.example.demonstrator_2_basicnotes.data.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class NoteViewModel(private val repository: NoteRepository) : ViewModel() {
    val allNotes: Flow<List<Note>> = repository.allNotes

    fun insert(note: Note) = viewModelScope.launch {
        repository.insert(note)
    }

    fun delete(noteId: Int) = viewModelScope.launch {
        repository.delete(noteId)
    }
}

class NoteViewModelFactory(private val repository: NoteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NoteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}