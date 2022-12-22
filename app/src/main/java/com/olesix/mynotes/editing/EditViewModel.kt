package com.olesix.mynotes.editing

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.olesix.mynotes.Note
import com.olesix.mynotes.db.AppDatabase
import com.olesix.mynotes.repository.NoteRepository

class EditViewModel(application: Application) : ViewModel() {

    val note: MutableLiveData<Note> by lazy {
        MutableLiveData<Note>()
    }

    private var noteRepository: NoteRepository =
        NoteRepository(AppDatabase.getDatabase(application).noteDao())

    fun addNote(note: Note) {
        noteRepository.insert(note)
    }

    fun updateNote(note: Note) {
        noteRepository.update(note)
    }

    fun deleteNote(note: Note) {
        noteRepository.delete(note)
    }

    fun getNoteById(id: String) {
        note.postValue(noteRepository.getById(id))
    }
}