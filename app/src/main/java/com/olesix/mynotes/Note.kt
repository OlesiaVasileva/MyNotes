package com.olesix.mynotes

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Notes")
data class Note(
    @PrimaryKey
    val id: String,
    var header: String,
    var text: String,
    var date: Long,
    val color: String
)
