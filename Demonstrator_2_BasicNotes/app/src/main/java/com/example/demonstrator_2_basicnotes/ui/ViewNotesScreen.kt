package com.example.demonstrator_2_basicnotes.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.demonstrator_2_basicnotes.R
import com.example.demonstrator_2_basicnotes.data.NoteRepository
import java.text.SimpleDateFormat
import java.util.*
import com.example.demonstrator_2_basicnotes.DummyNotes
import com.example.demonstrator_2_basicnotes.ui.theme.NotesAppTheme

@Composable
fun ViewNotesScreen(navController: NavController, noteRepository: NoteRepository) {
    NotesAppTheme {
        val noteViewModel: NoteViewModel = viewModel(factory = NoteViewModelFactory(noteRepository))
        //val notes = noteViewModel.allNotes.collectAsState(initial = emptyList())
        val notes = noteViewModel.allNotes.collectAsState(initial = DummyNotes.notes)

        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(notes.value) { note ->
                    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(note.content, style = MaterialTheme.typography.bodyLarge)
                                    Spacer(modifier = Modifier.height(8.dp))
                                    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                                    val dateTime = dateFormat.format(Date(note.timestamp))
                                    Text(dateTime, style = MaterialTheme.typography.bodySmall)
                                }
                                IconButton(onClick = { noteViewModel.delete(note.id) }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_delete),
                                        contentDescription = "Delete",
                                        tint = Color.Red
                                    )
                                }
                            }
                        }
                    }
                }
            }
            FloatingActionButton(
                onClick = {
                    navController.navigate("add_note") {
                        popUpTo("view_notes") { inclusive = true }
                        launchSingleTop = true
                        anim {
                            enter = 0
                            exit = 0
                        }
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
                    .size(72.dp), // button size
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add_note),
                    contentDescription = "Add Note",
                    modifier = Modifier.size(36.dp) // icon size (inside button)
                )
            }
        }
    }
}