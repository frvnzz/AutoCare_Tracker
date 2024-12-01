package at.ac.fhstp.contactsapp.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactsDao {

    @Insert
    suspend fun addContact(contactEntity: ContactEntity)
    // INSERT INTO contacts (name, phone) VALUES ('Max',..)

    @Update
    suspend fun updateContact(contactEntity: ContactEntity)
    // UPDATE contacts set name = 'Anna', ...

    @Delete
    suspend fun deleteContact(contactEntity: ContactEntity)
    // DELETE FROM contacts WHERE _id = ..

    @Query("SELECT * FROM contacts WHERE _id = :contactId")
    suspend fun findContactById(contactId: Int): ContactEntity

    @Query("SELECT * FROM contacts")
    fun findAllContacts(): Flow<List<ContactEntity>>

    @Query("SELECT * FROM contacts WHERE name = :contactName")
    suspend fun findAllContactsByName(contactName: String): List<ContactEntity>

}