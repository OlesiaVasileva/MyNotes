package com.olesix.mynotes.model

object NotesList {

    private var notes = mutableListOf<Note>()

    /*private fun fillList(): MutableList<Note> {
        val note1 = Note(
            "01", "Список дел на сегодня",
            "Решить задачу на связный список", System.currentTimeMillis()
        )
        val note2 = Note(
            "02", "Что посмотреть",
            "Видео с собеседования на Андроид разработчика", System.currentTimeMillis()
        )
        val note3 = Note(
            "03", "Купить в продуктовом",
            "Хлеб, молоко, орехи", System.currentTimeMillis()
        )

        return mutableListOf(note1, note2, note3)
    }*/

    fun getListOfNotes(): MutableList<Note> {
        return notes
    }

    fun addNote(note: Note) {
        notes.add(note)
    }

    fun getNoteById(id: String): Note {
        return notes.first { note -> note.id == id }
    }

    fun deleteNote(note: Note) {
        notes.remove(note)
    }

    fun updateNote(newNote: Note) {
        notes[notes.indexOf(newNote)] = newNote
    }

    fun getNotesBySearch(inputString: String) : MutableList<Note> {
        val foundNotes = mutableListOf<Note>()
        notes.forEach { note ->
            if (note.header.contains(inputString, ignoreCase = true) || note.text.contains(
                    inputString, ignoreCase = true)) {
                foundNotes.add(note)
            }
        }
        return foundNotes
    }
}