package at.ac.fhstp.contactsapp.data

import at.ac.fhstp.contactsapp.data.db.ContactEntity
import at.ac.fhstp.contactsapp.data.db.ContactsDao
import at.ac.fhstp.contactsapp.data.remote.ContactRemoteService
import kotlinx.coroutines.flow.map

class ContactRepository(private val contactsDao: ContactsDao, private val contactRemoteService: ContactRemoteService) {
    val names = listOf(
        "Max",
        "Tom",
        "Anna",
        "Matt"
    )

    suspend fun addInitialContacts() {
        contactRemoteService.findAllContacts()
            .map { dto -> ContactEntity(0, dto.name, dto.telephoneNumber.toString(), dto.age) }
            .forEach { entity -> contactsDao.addContact(entity) }
    }

    val contacts = contactsDao.findAllContacts().map { list ->
        list.map { entity ->
            Contact(entity._id, entity.name, entity.telephoneNumber, entity.age)
        }
    }

    suspend fun addRandomContact() {
        contactsDao.addContact(ContactEntity(0, names.random(), "+4357894", 45))
    }

    suspend fun findContactById(contactId: Int) : Contact {
        val entity = contactsDao.findContactById(contactId)
        return Contact(entity._id, entity.name, entity.telephoneNumber, entity.age)
    }
}