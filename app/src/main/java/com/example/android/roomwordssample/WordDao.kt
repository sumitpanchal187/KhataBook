
package com.example.android.roomwordssample

import android.location.Address
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {

//    @Query("SELECT * FROM word_table ORDER BY date DESC")
    @Query("SELECT * FROM word_table ORDER BY DATE(SUBSTR(date, 7) || '-' || SUBSTR(date, 4, 2) || '-' || SUBSTR(date, 1, 2)) DESC")
    fun getAlphabetizedWords(): Flow<List<Word>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(word: Word)

    @Query("DELETE FROM word_table")
    suspend fun deleteAll()

    @Query("DELETE FROM word_table WHERE word = :word")
    suspend fun deleteWord(word: String)

    @Query("UPDATE word_table SET note = :newNote WHERE id = :wordId")
    suspend fun updateWord(wordId: Int, newNote: String)


}
