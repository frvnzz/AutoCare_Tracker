package at.ac.fhstp.fhstplecturegroup3a.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

data class MainUiState(
    val result: Double
)

class MainViewModel : ViewModel() {

    // private MUTABLE stateflow
    private val _mainUiState = MutableStateFlow(MainUiState(0.0))
    // public IMMUTABLE stateflow
    val mainUiState : StateFlow<MainUiState> = _mainUiState

    fun onCalculate(num1Text: String, num2Text: String) {
        val num1 = num1Text.toDoubleOrNull() ?: 0.0
        val num2 = num2Text.toDoubleOrNull() ?: 0.0
        val sum = num1 + num2

        _mainUiState.update { oldState ->
            oldState.copy(result = sum)
        }
    }

}