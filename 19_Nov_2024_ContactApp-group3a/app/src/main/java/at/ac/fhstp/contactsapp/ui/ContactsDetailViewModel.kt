package at.ac.fhstp.contactsapp.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.ac.fhstp.contactsapp.data.Contact
import at.ac.fhstp.contactsapp.data.ContactRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ContactDetailUiState(
    val contact: Contact = Contact(0, "", "", 0)
)

class ContactsDetailViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val contactRepository: ContactRepository
) : ViewModel() {

    private val contactId: Int = checkNotNull(savedStateHandle["contactId"])

    private val _contactDetailUiState = MutableStateFlow(ContactDetailUiState())
    val contactDetailUiState = _contactDetailUiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val contact = contactRepository.findContactById(contactId)
            _contactDetailUiState.update {
                it.copy(contact = contact)
            }
        }
    }

}