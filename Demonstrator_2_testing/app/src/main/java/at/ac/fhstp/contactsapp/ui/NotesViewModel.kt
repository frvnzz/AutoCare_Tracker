// NotesViewModel.kt
package com.example.myapp.ui

import androidx.lifecycle.*
import com.example.myapp.data.NoteEntity
import com.example.myapp.data.NotesRepository
import kotlinx.coroutines.launch

class NotesViewModel(private val repository: NotesRepository) : ViewModel() {
    val allNotes: LiveData<List<NoteEntity>> = repository.allNotes.asLiveData()

    fun addNote(note: NoteEntity) = viewModelScope.launch {
        repository.addNote(note)
    }

    fun deleteNote(note: NoteEntity) = viewModelScope.launch {
        repository.deleteNote(note)
    }
}

class NotesViewModelFactory(private val repository: NotesRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NotesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}