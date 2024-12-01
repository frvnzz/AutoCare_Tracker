package com.example.demonstrator_2_basicnotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.demonstrator_2_basicnotes.ui.AddNoteScreen
import com.example.demonstrator_2_basicnotes.ui.ViewNotesScreen
import com.example.demonstrator_2_basicnotes.ui.theme.NotesAppTheme
import com.example.demonstrator_2_basicnotes.data.NoteRepository
import com.example.demonstrator_2_basicnotes.data.NoteDatabase

class MainActivity : ComponentActivity() {
    private val noteDao by lazy { NoteDatabase.getDatabase(this).noteDao() }
    private val noteRepository by lazy { NoteRepository(noteDao) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotesAppTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    NavHost(navController, startDestination = "view_notes") {
                        composable("view_notes") { ViewNotesScreen(navController, noteRepository) }
                        composable("add_note") { AddNoteScreen(navController, noteRepository) }
                    }
                }
            }
        }
    }
}