package at.ac.fhstp.contactsapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapp.data.NoteEntity
import com.example.myapp.data.NotesDao

@Database(entities = [NoteEntity::class], version = 1)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun notesDao(): NotesDao

    companion object {
        @Volatile
        private var Instance: NotesDatabase? = null

        fun getDatabase(context: Context): NotesDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                val instance = Room.databaseBuilder(context, NotesDatabase::class.java, "notes_database")
                    /**
                     * Setting this option in your app's database builder means that Room
                     * permanently deletes all data from the tables in your database when it
                     * attempts to perform a migration with no defined migration path.
                     * DO NOT DO THIS FOR PRODUCTION APPS
                     */
                    .fallbackToDestructiveMigration()
                    .build()
                Instance = instance
                return instance
            }
        }
    }
}