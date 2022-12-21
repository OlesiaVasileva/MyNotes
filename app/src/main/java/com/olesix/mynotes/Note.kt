package com.olesix.mynotes

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Notes")
data class Note(
    @PrimaryKey
    val id: String,
    var header: String,
    var text: String,
    var date: Long
) {

    companion object {
        val colors = listOf("#E2F3F0", "#C3D9FF", "#FFF5E6", "#F8D9DE", "#FDCCCA")
    }

    val color: String = getRandomColor()

    private fun getRandomColor(): String {
        return colors.random()
    }
}
