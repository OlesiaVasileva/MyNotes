package com.olesix.mynotes.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.olesix.mynotes.model.Note
import com.olesix.mynotes.repository.NoteRepository

class MainViewModel(private val noteRepository: NoteRepository) : ViewModel() {

    val listOfNotes: MutableLiveData<List<Note>> by lazy {
        MutableLiveData<List<Note>>()
    }

    fun getAllNotes() {
        noteRepository.getAll().sortByDescending { note -> note.date }
        listOfNotes.postValue(noteRepository.getAll())
    }
}