package com.olesix.mynotes.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.olesix.mynotes.model.Note
import com.olesix.mynotes.repository.NoteRepository
import kotlinx.coroutines.launch

class EditViewModel(private val noteRepository: NoteRepository) : ViewModel() {

    val note: MutableLiveData<Note> by lazy {
        MutableLiveData<Note>()
    }

    fun addNote(note: Note) {
        viewModelScope.launch {
            note.color = getRandomColor()
            noteRepository.insert(note)
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            noteRepository.update(note)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            noteRepository.delete(note)
        }
    }

    fun getNoteById(id: String) {
        viewModelScope.launch {
            note.postValue(noteRepository.getById(id))
        }
    }

    companion object {
        val colors = listOf("#E2F3F0", "#C3D9FF", "#FFF5E6", "#F8D9DE", "#FDCCCA")
    }

    private fun getRandomColor(): String {
        return colors.random()
    }
}