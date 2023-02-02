package com.olesix.mynotes

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.olesix.mynotes.db.AppDatabase
import com.olesix.mynotes.repository.NoteRepository

class MainViewModel(application: Application) : ViewModel() {

    val listOfNotes: MutableLiveData<List<Note>> by lazy {
        MutableLiveData<List<Note>>()
    }

    private var noteRepository: NoteRepository =
        NoteRepository(AppDatabase.getDatabase(application).noteDao())

    fun getAllNotes() {
        noteRepository.getAll().sortByDescending { note -> note.date }
        listOfNotes.postValue(noteRepository.getAll())
    }
}