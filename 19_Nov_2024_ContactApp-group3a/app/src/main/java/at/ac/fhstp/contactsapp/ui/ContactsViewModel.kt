package at.ac.fhstp.contactsapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.ac.fhstp.contactsapp.data.ContactRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ContactsViewModel(val repository: ContactRepository) : ViewModel() {
    private val _contactsUiState = MutableStateFlow(ContactsUiState(emptyList(), null))
    val contactsUiState = _contactsUiState

    init {
        viewModelScope.launch {
            repository.addInitialContacts()
        }
        viewModelScope.launch {
            repository.contacts.collect { data ->
                _contactsUiState.update { oldState ->
                    oldState.copy(
                        contacts = data
                    )
                }

            }

        }
    }

    fun onAddButtonClicked() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addRandomContact()
        }
    }

    fun onCardClick(index: Int) {
        _contactsUiState.update {
            it.copy(selectedCardIndex = index)
        }
    }
}
