package com.example.android.roomwordssample

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Word::class], version = 23)
//specifies the entities it manages and the database version.
abstract class WordRoomDatabase : RoomDatabase() {

    abstract fun wordDao(): WordDao
    companion object {
        @Volatile
        //represent singleton object
        private var INSTANCE: WordRoomDatabase? = null
/*
    getDatabase returns the singleton. It'll create the database the first time it's accessed,using Room's database builder
    to create a RoomDatabase object in the application context from the WordRoomDatabase class and names it "word_database".
*/
        fun getDatabase(context: Context,scope: CoroutineScope): WordRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder( context.applicationContext,WordRoomDatabase::class.java,"word_database")
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

/*
synchronized --> the process of ensuring that data stored in multiple locations is the same and up-to-date.
 */

