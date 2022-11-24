package com.olesix.mynotes

object NotesList {

    val notes = fillList()

    private fun fillList(): MutableList<Note> {
        val note1 = Note(
            "01", "Список дел на сегодня",
            "Решить задачу на связный список", "#EEEEF4", System.currentTimeMillis()
        )
        val note2 = Note(
            "02", "Что посмотреть",
            "Видео с собеседования на Андроид разработчика", "#EEEEF4",
            System.currentTimeMillis()
        )
        val note3 = Note(
            "03", "Купить в продуктовом",
            "Хлеб, молоко, орехи", "#EEEEF4", System.currentTimeMillis()
        )

        return mutableListOf(note1, note2, note3)
    }
}