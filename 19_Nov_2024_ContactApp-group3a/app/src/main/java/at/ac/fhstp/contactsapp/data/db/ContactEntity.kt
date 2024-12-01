package at.ac.fhstp.contactsapp.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "contacts")
data class ContactEntity(
    @PrimaryKey(autoGenerate = true)
    val _id: Int = 0,
    val name: String,
    @ColumnInfo(name = "telephone_number")
    val telephoneNumber: String,
    val age: Int
)
