package com.olesix.mynotes

object NotesList {

    val notes = fillList()

    private fun fillList(): MutableList<Note> {
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
    }

    fun addNote (note: Note) {
        notes.add(note)
    }
}