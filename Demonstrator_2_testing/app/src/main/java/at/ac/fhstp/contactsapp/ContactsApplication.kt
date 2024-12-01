package at.ac.fhstp.contactsapp

import android.app.Application
import at.ac.fhstp.contactsapp.data.ContactRepository
import at.ac.fhstp.contactsapp.data.db.ContactsDatabase

class ContactsApplication : Application() {

    val contactRepository by lazy {
        val dao = ContactsDatabase.getDatabase(this).contactsDao()
        ContactRepository(dao)
    }

}