package at.ac.fhstp.contactsapp.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import at.ac.fhstp.contactsapp.data.Contact
import at.ac.fhstp.contactsapp.data.ContactRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

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
        // contactRepository.findContactById(contactId)
    }

}