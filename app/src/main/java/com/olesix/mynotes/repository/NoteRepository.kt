package com.olesix.mynotes.repository

import com.olesix.mynotes.model.Note
import com.olesix.mynotes.db.NoteDao

class NoteRepository(private val noteDao: NoteDao) {

    fun getAll(): MutableList<Note> {
        return noteDao.getAll()
    }

    fun getById(id: String): Note {
        return noteDao.getById(id)
    }

    fun insert(note: Note) {
        return noteDao.insert(note)
    }

    fun update(note: Note) {
        return noteDao.update(note)
    }

    fun delete(note: Note) {
        return noteDao.delete(note)
    }
}
