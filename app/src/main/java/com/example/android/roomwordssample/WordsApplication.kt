package com.example.android.roomwordssample

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class WordsApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
//    scope will be used for managing coroutines throughout the application's lifecycle.

    val database by lazy { WordRoomDatabase.getDatabase(this, applicationScope) }
//    A lazy-initialized property that represents the Room database instance.

    //    lazy delegate ensures that the database is created only when it's accessed for the first time.
    val repository by lazy { WordRepository(database.wordDao()) }
}

/*
It serves as a single entry point for initializing global components of the application, such as the Room database,
repository, or other application-wide resources.

Lifecycle Management:
Context Access:
 */