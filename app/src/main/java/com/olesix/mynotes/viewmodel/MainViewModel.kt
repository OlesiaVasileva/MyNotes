package com.olesix.mynotes.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.olesix.mynotes.mainscreen.LOG_TAG
import com.olesix.mynotes.model.Note
import com.olesix.mynotes.repository.NoteRepository
import com.olesix.mynotes.util.Response
import kotlinx.coroutines.launch

class MainViewModel(private val noteRepository: NoteRepository) : ViewModel() {

    val listOfNotes: MutableLiveData<List<Note>> by lazy {
        MutableLiveData<List<Note>>()
    }

    fun getAllNotes() {
        viewModelScope.launch {
            when (val result = noteRepository.getAll()) {
                is Response.Success -> {
                    result.data.sortByDescending { note -> note.date }
                    listOfNotes.postValue(result.data)
                }
                is Response.Exception -> Log.e(LOG_TAG, result.error.toString())
            }
        }
    }
}