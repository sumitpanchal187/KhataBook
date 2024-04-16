package com.example.android.roomwordssample

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow


class WordRepository(private val wordDao: WordDao) {

    val allWords: Flow<List<Word>> = wordDao.getAlphabetizedWords()
    @Suppress("RedundantSuspendModifier")

    @WorkerThread
    suspend fun insert(word: Word) {
        wordDao.insert(word)
    }
    suspend fun deleteWord(word: String) {
        wordDao.deleteWord(word)
    }

    suspend fun deleteAll() {
        wordDao.deleteAll()
    }
    suspend fun updateWord(wordId: Int, newNote: String) {
        wordDao.updateWord(wordId, newNote)
    }

}
