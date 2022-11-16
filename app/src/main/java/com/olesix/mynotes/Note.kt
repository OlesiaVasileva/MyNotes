package com.olesix.mynotes

data class Note(
    val id: String, var header: String, var text: String,
    val data: Long) {

    val color: String = getRandomColor()

    private fun getRandomColor(): String {
        val colors = listOf("#E2F3F0", "#C3D9FF", "#FFF5E6", "#F8D9DE", "#FDCCCA")
        return colors.random()
    }
}
