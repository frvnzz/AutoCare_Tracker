package com.example.demonstrator_2_basicnotes.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.demonstrator_2_basicnotes.R
import com.example.demonstrator_2_basicnotes.data.Note
import com.example.demonstrator_2_basicnotes.data.NoteRepository
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import com.example.demonstrator_2_basicnotes.ui.theme.NotesAppTheme
import java.util.*

@Composable
fun AddNoteScreen(navController: NavController, noteRepository: NoteRepository) {
    NotesAppTheme {
        val noteViewModel: NoteViewModel = viewModel(factory = NoteViewModelFactory(noteRepository))
        var noteContent by remember { mutableStateOf("") }
        val focusManager = LocalFocusManager.current
        val focusRequester = remember { FocusRequester() }

        // handling back button press
        BackHandler {
            navController.navigate("view_notes") {
                popUpTo("add_note") { inclusive = true }
                launchSingleTop = true
                anim {
                    enter = 0
                    exit = 0
                }
            }
        }

        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = { focusManager.clearFocus(force = true) }
                )
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
            ) {
                Spacer(modifier = Modifier.weight(1f))
                OutlinedTextField(
                    value = noteContent,
                    onValueChange = { noteContent = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .focusRequester(focusRequester),
                    keyboardOptions = KeyboardOptions.Default,
                    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus(force = true) })
                )
                Spacer(modifier = Modifier.weight(1f))
            }
            FloatingActionButton(
                onClick = {
                    if (noteContent.isNotEmpty()) {
                        noteViewModel.insert(Note(content = noteContent, timestamp = Date().time))
                        navController.navigate("view_notes") {
                            popUpTo("add_note") { inclusive = true }
                            launchSingleTop = true
                            anim {
                                enter = 0
                                exit = 0
                            }
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
                    painter = painterResource(id = R.drawable.ic_save_note),
                    contentDescription = "Save Note",
                    modifier = Modifier.size(36.dp) // icon size (inside button)
                )
            }
        }
    }
}