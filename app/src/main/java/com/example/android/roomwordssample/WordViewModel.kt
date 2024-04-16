
package com.example.android.roomwordssample

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class WordViewModel(private val repository: WordRepository) : ViewModel() {

    val allWords: LiveData<List<Word>> = repository.allWords.asLiveData()

    fun insert(word: Word) = viewModelScope.launch {
        repository.insert(word)
    }
    fun deleteWord(word: String) = viewModelScope.launch {
        repository.deleteWord(word)
    }
    // Function to update a word by its ID and new note
    fun updateWord(wordId: Int, newNote: String) = viewModelScope.launch {
        repository.updateWord(wordId, newNote)
    }


}

class WordViewModelFactory(private val repository: WordRepository) : ViewModelProvider.Factory {
//    it's responsible for creating ViewModel instances. its used to access repository
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WordViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
