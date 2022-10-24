package com.olesix.mynotes

data class Note(
    val id: String, var header: String, var text: String,
    var color: String, val data: Long)
