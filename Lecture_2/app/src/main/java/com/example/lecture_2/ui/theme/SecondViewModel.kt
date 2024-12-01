package com.example.lecture_2.ui.theme

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.random.Random

class SecondViewModel : ViewModel() {
    private val _currentString = MutableStateFlow("Hello")
    val currentString: StateFlow<String> = _currentString

    private val stringList = listOf("Hello", "Hi there", "Ahoi", "Greetings")

    fun pickRandomString() {
        _currentString.value = stringList[Random.nextInt(stringList.size)]
    }
}